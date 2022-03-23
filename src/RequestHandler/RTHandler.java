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

public class RTHandler {

    private BufferedReader reader;
    private Socket socket;
    private String author;
    private String date;
    private String RTId;

    public RTHandler(BufferedReader reader, Socket socket) {
        this.socket = socket;
        this.reader = reader;
        System.out.println(ANSI_GREEN + "Request Type : RT" + ANSI_RESET);
        handle();

    }

    public RTHandler(String author, String date, String id) {

        System.out.println(ANSI_GREEN + "Request Type : RT" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Author : @" + author + "   at   " + date + ANSI_RESET);
        System.out.println(ANSI_CYAN + "RT messages -> " + id + ANSI_RESET);

        System.out.println("\n------------------------------------\n");

    }

    private String ANSI_RESET = "\u001B[0m";

    private String ANSI_BLUE = "\u001B[34m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_CYAN = "\u001B[36m";

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
                        this.RTId = line;
                        System.out.println(ANSI_CYAN + "RT id -> " + RTId + ANSI_RESET);
                        c++;
                        continue;

                    }

                    if (line.equals("$") && c == 2) {
                        output.write(("OK \n").getBytes());
                        c++;
                        break;

                    } else if (!line.equals("$") && c == 2) {
                        System.out.println("ERROR");
                        output.write(("ERROR \n").getBytes());
                        c++;
                        break;

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

            String sql = "insert into messages (author,date,rt,message) VALUES(?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, author);
            pstmt.setString(2, date);
            pstmt.setString(3, RTId);
            pstmt.setString(4, "");

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
