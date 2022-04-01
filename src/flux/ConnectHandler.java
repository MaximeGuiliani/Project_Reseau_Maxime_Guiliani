package src.flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import src.RequestHandler.PublishHandler;
import src.RequestHandler.RTHandler;
import src.RequestHandler.ReplyHandler;

public class ConnectHandler implements Runnable {

    private BufferedReader reader;
    private Socket socket;
    private Scanner sc = new Scanner(System.in);

    public ConnectHandler(BufferedReader reader, Socket socket) {
        this.socket = socket;
    }

    public ConnectHandler(Socket socket, String username) {
        getSqlFollow(username);
        this.socket = socket;

        new Thread(() -> {
            InputStream input;
            try {
                input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line = reader.readLine();
                if (line != null) {
                    if (line.equals("update"))
                        getSqlFollow(username);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("You can sub and unsub here : ");
        while (true) {
            if (sc.hasNextLine()) {
                new FluxREquestHandler(sc.nextLine(), reader, this.socket, username);

            }

        }

    }

    @Override
    public void run() {

    }

    public void getSqlFollow(String username) {
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
