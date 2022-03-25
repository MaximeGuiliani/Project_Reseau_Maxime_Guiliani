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
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplyHandler {

    private BufferedReader reader;
    private Socket socket;
    private String message;
    private String author;
    private String date;
    private String replyId;
    private int messageID;

    public ReplyHandler(BufferedReader reader, Socket socket) {
        this.socket = socket;
        this.reader = reader;
        System.out.println(ANSI_PURPLE + "Request Type : REPLY" + ANSI_RESET);
        handle();

    }

    public ReplyHandler(String author, String message, String date, String id) {

        System.out.println(ANSI_PURPLE + "Request Type : REPLY" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Author : @" + author + "   at   " + date + ANSI_RESET);
        System.out.println("-> " + message + ANSI_RESET);
        System.out.println(ANSI_CYAN + "reply to -> " + id + ANSI_RESET);
        System.out.println("\n------------------------------------\n");

    }

    private String ANSI_RESET = "\u001B[0m";

    private String ANSI_BLUE = "\u001B[34m";
    private String ANSI_PURPLE = "\u001B[35m";
    private String ANSI_CYAN = "\u001B[36m";

    private void handle() {

        try {
            OutputStream output = socket.getOutputStream();

            int c = 0;
            while (c < 4) {
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
                    if (line != null && c == 2) {
                        this.replyId = line;
                        System.out.println(ANSI_CYAN + "reply to -> " + replyId + ANSI_RESET);
                        c++;
                        continue;

                    }
                    if (line.equals("$") && c == 3) {
                        output.write(("OK \n").getBytes());
                        c++;

                    } else if (!line.equals("$") && c == 3) {
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
            getActualID();
            Matcher matcher = getTags(message);
            while (matcher.find()) {
                addTagSQL(matcher.group(), messageID);
            }
            reader.close();

        } catch (IOException e) {

        }

    }

    private void addTagSQL(String tag, int messageID) {
        String jdbxUrl = "jdbc:sqlite:/home/maxime/Desktop/cours/reseau/Project/table.db";

        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);

            String sql = "insert into tags (tag,id) VALUES(?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tag);
            pstmt.setInt(2, messageID);

            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public Matcher getTags(String string) {
        Pattern pattern = Pattern.compile("#\\w+");
        return pattern.matcher(string);
    }

    public void updateSQL() {
        String jdbxUrl = "jdbc:sqlite:/home/maxime/Desktop/cours/reseau/Project/table.db";

        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);

            String sql = "insert into messages (author,message,date,reply) VALUES(?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, author);
            pstmt.setString(2, message);
            pstmt.setString(3, date);
            pstmt.setString(4, replyId);

            pstmt.executeUpdate();

            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void getActualID() {
        String jdbxUrl = "jdbc:sqlite:/home/maxime/Desktop/cours/reseau/Project/table.db";

        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);
            String sql = "SELECT * FROM messages ORDER BY id DESC limit 1 ";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                messageID = Integer.parseInt(result.getString("id"));

            }
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
