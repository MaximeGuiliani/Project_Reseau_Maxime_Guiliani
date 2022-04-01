package src.flux;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import src.TCPServer;
import src.RequestHandler.PublishHandler;
import src.RequestHandler.RTHandler;
import src.RequestHandler.ReplyHandler;
import src.request.Request;

public class Connect extends Request {
    private Scanner sc = new Scanner(System.in);
    private Socket socket;
    String username;

    public Connect(Socket socket) {
        this.socket = socket;
        this.header = Header.CONNECT;
        getAuthor();
        TCPServer.connectedUsers.put(username, socket);
        getSqlFollow();
        Executor executor_stealing = Executors.newWorkStealingPool();

        ConnectHandler handler = new ConnectHandler(this.socket, username);
        Thread thread = new Thread(handler);
        executor_stealing.execute(thread);

    }

    public void getAuthor() {
        boolean wait = false;
        System.out.println("author : ");
        System.out.print("@");

        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();

                if (!s1.isBlank() && !s1.contains(" ")) {
                    this.username = s1;
                    wait = true;
                } else {
                    System.out.println("please select a valid author name");
                    System.out.print("@");
                }
            }
        }

    }

    public void getSqlFollow() {
        ArrayList<String> list = new ArrayList<String>();

        try {
            String jdbxUrl = "jdbc:sqlite:table.db";
            Connection connection = DriverManager.getConnection(jdbxUrl);
            String sql = "select * from follows where username = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                String follows = result.getString("following");
                list.add(follows);
            }
            getSqlData(list);
        } catch (SQLException e) {
            System.out.println("echec de connexion");

            e.printStackTrace();
        }
    }

    public void getSqlData(ArrayList<String> list) {

        try {
            for (String s : list) {
                String jdbxUrl = "jdbc:sqlite:table.db";
                Connection connection = DriverManager.getConnection(jdbxUrl);
                String sql = "select * from messages where author = ?";

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, s);
                ResultSet result = pstmt.executeQuery();

                while (result.next()) {

                    String author = result.getString("author");
                    String message = result.getString("message");
                    String date = result.getString("date");
                    String replyId = result.getString("reply");
                    String id = result.getString("rt");

                    if (replyId == null && !message.isBlank()) {
                        new PublishHandler(author, message, date);
                    } else if (!message.isEmpty()) {
                        new ReplyHandler(author, message, date, replyId);
                    } else {
                        new RTHandler(author, date, id);
                    }

                }
            }

        } catch (SQLException e) {
            System.out.println("echec de connexion");

            e.printStackTrace();
        }
    }

}
