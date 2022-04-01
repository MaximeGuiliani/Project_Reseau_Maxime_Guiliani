package src.flux;

import java.io.BufferedReader;
import java.net.Socket;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnsubscribeHandler {
    private BufferedReader reader;
    private Socket socket;
    private String author;
    private String follow;

    public UnsubscribeHandler(BufferedReader reader, Socket socket) {
        this.socket = socket;
        this.reader = reader;
        System.out.println(ANSI_RED + "Request Type : UNSUBSCRIBE" + ANSI_RESET);
        handle();

    }

    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_RED = "\u001B[31m";
    private String ANSI_BLUE = "\u001B[34m";

    private void handle() {

        try {
            OutputStream output = socket.getOutputStream();

            int c = 0;
            while (c < 3) {
                String line;
                try {
                    line = reader.readLine();

                    if (line != null && c == 0) {

                        this.author = line;
                        System.out.println(ANSI_BLUE + "The Author : @" + author + ANSI_RESET);
                        c++;
                        continue;

                    }
                    if (line != null && c == 1) {
                        this.follow = line;
                        System.out.println("You unfollowed  ->  @" + follow);
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
        String jdbxUrl = "jdbc:sqlite:table.db";

        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);

            String sql = "delete from follows where username = ? and following = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, author);
            pstmt.setString(2, follow);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
