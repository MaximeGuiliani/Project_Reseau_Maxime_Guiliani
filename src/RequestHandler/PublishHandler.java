package src.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PublishHandler {

    private BufferedReader reader;
    private Socket socket;
    private String message;
    private String author;
    private String date;

    public PublishHandler(BufferedReader reader, Socket socket) {
        this.socket = socket;
        this.reader = reader;
        System.out.println(ANSI_RED + "Request Type : PUBLISH" + ANSI_RESET);
        handle();

    }

    public PublishHandler(String author, String message, String date) {

        System.out.println(ANSI_RED + "Request Type : PUBLISH" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Author : @" + author + "   at   " + date + ANSI_RESET);
        System.out.println(ANSI_WHITE + "-> " + message + ANSI_RESET);
        System.out.println("\n------------------------------------\n");

    }

    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_BLUE = "\u001B[34m";
    private String ANSI_WHITE = "\u001B[37m";

    private void handle() {

        try {
            OutputStream output = socket.getOutputStream();

            int c = 0;
            while (c < 3) {
                String line;
                try {
                    line = reader.readLine();

                    if (line != null && c == 0) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();

                        this.author = line;
                        this.date = dtf.format(now);
                        System.out.println(ANSI_BLUE + "Author : @" + author + "   at  " + date + ANSI_RESET);
                        c++;
                        continue;

                    }
                    if (line != null && c == 1) {
                        this.message = line;
                        System.out.println("-> " + message);
                        c++;
                        continue;

                    }
                    if (line.equals("$") && c == 2) {
                        output.write(("OK \n").getBytes());
                        c++;

                    } else if (!line.equals("$") && c == 2) {
                        System.out.println("ERROR");
                        output.write(("ERROR \n").getBytes());
                        c++;

                    }

                } catch (IOException e) {
                    output.write(("ERROR\n" + "\n").getBytes());
                }
            }
            System.out.println("\n------------------------------------\n");
            updateSQL();
            reader.close();

        } catch (IOException e) {

        }

    }

    public void updateSQL() {
        String jdbxUrl = "jdbc:sqlite:/home/maxime/Desktop/cours/reseau/Project/table.db";

        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);

            String sql = "insert into messages (author,message,date) VALUES(?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, author);
            pstmt.setString(2, message);
            pstmt.setString(3, date);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
