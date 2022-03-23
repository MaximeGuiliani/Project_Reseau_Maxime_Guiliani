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
    // TODO : table tag (tag,id) double key
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
        System.out.println("Request Type : RECEIVEID" + ANSI_RESET);
        handle();

    }

    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_RED = "\u001B[31m";

    private String ANSI_BLUE = "\u001B[34m";
    private String ANSI_CYAN = "\u001B[36m";
    private String ANSI_WHITE = "\u001B[37m";

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
                        System.out.println(ANSI_BLUE + "Author : @" + author + "   at  " + date + ANSI_RESET);
                        c++;
                        continue;

                    }
                    if (line != null && c == 1) {
                        this.tag = line;
                        System.out.println(ANSI_CYAN + "tag -> " + tag + ANSI_RESET);
                        c++;
                        continue;

                    }
                    if (line != null && c == 2) {
                        this.sinceID = line;
                        System.out.println(ANSI_WHITE + "since -> " + sinceID + ANSI_RESET);
                        c++;
                        continue;

                    }
                    if (line != null && c == 3) {
                        if (!line.equals("")) {
                            this.limit = Integer.parseInt(line);

                        } else {
                            limit = 5;
                        }
                        System.out.println(ANSI_RED + "limit -> " + limit + ANSI_RESET);
                        c++;
                        continue;

                    }
                    getIDs();
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

    public void getIDs() {
        String jdbxUrl = "jdbc:sqlite:/home/maxime/Desktop/cours/reseau/Project/table.db";

        try {

            ArrayList<Integer> listId = new ArrayList<>();
            Connection conn = DriverManager.getConnection(jdbxUrl);

            if (!author.isBlank()) {

                String sql = "select * FROM messages where author = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, author);
                ResultSet result = pstmt.executeQuery();
                while (result.next()) {
                    String id = result.getString("id");
                    listId.add(Integer.parseInt(id));
                }
            }
            // Gestion du tag
            if (!tag.isBlank()) {
                if (listId.size() <= 0) {
                    String sql = "select * FROM messages";
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
                    ArrayList<Integer> newList = new ArrayList<>();
                    for (int e : listId) {
                        String sql = "select * from messages where id = ?";

                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, "" + e);

                        ResultSet result = pstmt.executeQuery();
                        if (result.next()) {
                            String message = result.getString("message");
                            if (message.contains("#" + tag)) {
                                newList.add(e);
                            }

                        }
                    }
                    listId = newList;

                }

            }
            // Gestion du since
            if (!sinceID.isBlank()) {
                if (listId.size() <= 0) {
                    String sql = "select * from messages where id >= ?";
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
                        if (e >= Integer.parseInt(sinceID)) {
                            newList.add(e);
                        }
                    }

                    listId = newList;

                }

            }
            // Gestion du limit
            if (listId.size() <= 0 && author.equals("") && tag.equals("") && sinceID.equals("")) {
                String sql = "select * FROM messages";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet result = pstmt.executeQuery();
                while (result.next()) {
                    String id = result.getString("id");
                    listId.add(Integer.parseInt(id));
                }

                if (limit < listId.size()) {
                    while (listId.size() > limit) {
                        listId.remove(0);

                    }
                }

            } else {
                if (limit < listId.size()) {
                    while (limit < listId.size()) {
                        listId.remove(0);

                    }
                }

            }
            toSend = listId.toString();
            conn.close();

            System.out.println(listId.toString());

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
