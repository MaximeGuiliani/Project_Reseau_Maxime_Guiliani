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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ReceiveIDHandler {

    private BufferedReader reader;
    private Socket socket;
    private String author;
    private String date;
    private String tag;
    private String sinceID;
    private Integer limit;
    public String toSend;

    public ReceiveIDHandler(BufferedReader reader, Socket socket) {
        this.socket = socket;
        this.reader = reader;
        System.out.println("Request Type : RECEIVEID");
        handle();

    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public ReceiveIDHandler(String author, String tag, String since, String limit) {

        System.out.println(ANSI_RED + "Request Type : RECEIVEID" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Author : @" + author + "   at   " + date + ANSI_RESET);
        System.out.println(ANSI_WHITE + "tag -> #" + tag + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "limit -> " + limit + ANSI_RESET);

        System.out.println("\n------------------------------------\n");

    }

    private void handle() {

        try {
            OutputStream output = socket.getOutputStream();

            int c = 0;
            while (c < 5) {
                String line;
                try {
                    line = reader.readLine();

                    if (line != null && c == 0) {
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();
                        this.author = line;
                        this.date = dtf.format(now);
                        System.out.println("Author : @" + author + "   at  " + date);
                        c++;
                        continue;

                    }
                    if (line != null && c == 1) {
                        this.tag = line;
                        System.out.println("tag -> " + tag);
                        c++;
                        continue;

                    }
                    if (line != null && c == 2) {
                        this.sinceID = line;
                        System.out.println("since -> " + sinceID);
                        c++;
                        continue;

                    }
                    if (line != null && c == 3) {
                        if (!line.equals("")) {
                            this.limit = Integer.parseInt(line);

                        } else {
                            limit = 5;
                        }
                        System.out.println("limit -> " + limit);
                        c++;
                        continue;

                    }
                    getSQL();
                    if (line.equals("$") && c == 4) {
                        output.write((toSend).getBytes());
                        c++;

                    } else if (!line.equals("$") && c == 4) {
                        System.out.println("ERROR");
                        output.write(("ERROR \n").getBytes());
                        c++;

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

    public void getSQL() {
        String jdbxUrl = "jdbc:sqlite:/home/maxime/Desktop/cours/reseau/Project/table.db";

        try {

            ArrayList<Integer> listId = new ArrayList<>();
            Connection connection = DriverManager.getConnection(jdbxUrl);

            // gestion de l'auteur
            if (!author.equals("")) {

                String sql = "select * FROM messages where author = ?";

                Connection conn = connection;
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, author);
                ResultSet result = pstmt.executeQuery();
                while (result.next()) {
                    String id = result.getString("id");
                    listId.add(Integer.parseInt(id));
                }
            }
            // Gestion du tag
            if (!tag.equals("")) {
                if (listId.size() <= 0) {
                    String sql = "select * FROM messages";
                    Connection conn = connection;
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    ResultSet result = pstmt.executeQuery();
                    while (result.next()) {
                        String message = result.getString("message");
                        if (message.contains("#" + tag)) {
                            String id = result.getString("id");
                            listId.add(Integer.parseInt(id));

                        }
                    }
                } else {
                    for (int e : listId) {
                        String sql = "select * from messages where id = ?";
                        Connection conn = connection;

                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, "" + e);

                        ResultSet result = pstmt.executeQuery();
                        if (result.next()) {
                            String message = result.getString("message");
                            if (message.contains("#" + tag)) {
                                listId.add(e);
                            }

                        }
                    }

                }

            }
            // Gestion du since
            if (sinceID.equals("")) {
                if (listId.size() <= 0) {
                    String sql = "select message from messages where id >= ?";
                    Connection conn = connection;
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, "" + sinceID);
                    ResultSet result = pstmt.executeQuery();
                    if (result.next()) {
                        String id = result.getString("id");
                        listId.add(Integer.parseInt(id));
                    }
                } else {
                    ArrayList<Integer> newList = new ArrayList<>();
                    for (int e : listId) {
                        if (e < Integer.parseInt(sinceID)) {
                            newList.add(e);
                        }
                    }
                    listId = newList;

                }

            }
            // Gestion du limit
            if (listId.size() <= 0) {
                String sql = "select * FROM messages";
                Connection conn = connection;
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet result = pstmt.executeQuery();
                while (result.next()) {
                    String id = result.getString("id");
                    listId.add(Integer.parseInt(id));
                }

                if (limit < listId.size()) {
                    for (int i = 0; i < listId.size() - limit; i++) {
                        listId.remove(0);

                    }
                }

            } else {
                if (limit < listId.size()) {
                    for (int i = 0; i < listId.size() - limit; i++) {
                        listId.remove(0);

                    }
                }

            }
            toSend = listId.toString();
            System.out.println(listId.toString());

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
