package src.RequestHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceiveMSGHandler {

    private BufferedReader reader;
    private Socket socket;
    private String id;
    private String message;

    public ReceiveMSGHandler(BufferedReader reader, Socket socket) {
        this.socket = socket;
        this.reader = reader;

        System.out.println(ANSI_GREEN + "Request Type : RECEIVEMSG" + ANSI_RESET);
        handle();
    }

    private String ANSI_RESET = "\u001B[0m";

    private String ANSI_GREEN = "\u001B[32m";

    private String ANSI_CYAN = "\u001B[36m";

    private void handle() {

        try {
            OutputStream output = socket.getOutputStream();

            int c = 0;
            while (c < 2) {
                String line;
                try {
                    line = reader.readLine();
                    if (line != null && c == 0) {
                        this.id = line;
                        System.out.println(ANSI_CYAN + "id : " + id + ANSI_RESET);
                        getMessagesSQL();
                        c++;
                        continue;

                    }

                    if (line.equals("$") && c == 1) {
                        output.write((message + "\n").getBytes());
                        c++;
                        break;

                    } else if (!line.equals("$") && c == 1) {
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

            reader.close();

        } catch (IOException e) {

        }

    }

    public void getMessagesSQL() {
        String jdbxUrl = "jdbc:sqlite:table.db";

        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);

            String sql = "select message FROM messages where id=?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                message = result.getString("message");

            }
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
