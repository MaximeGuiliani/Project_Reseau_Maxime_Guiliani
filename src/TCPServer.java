package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import src.RequestHandler.PublishHandler;
import src.RequestHandler.RTHandler;
import src.RequestHandler.ReplyHandler;

public class TCPServer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws IOException {
        System.out.println("\n\n/-------------------------------------/");
        System.out.println("        WELCOME TO MY APP \n\n");
        System.out.println("    LATEST POST \n");
        getSqlData();
        Executor executor_stealing = Executors.newWorkStealingPool();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {

            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                Thread thread = new Thread(handler);
                executor_stealing.execute(thread);
            }
        }

    }

    public static void getSqlData() {

        try {
            String jdbxUrl = "jdbc:sqlite:/home/maxime/Desktop/cours/reseau/Project/table.db";
            Connection connection = DriverManager.getConnection(jdbxUrl);
            String sql = "select * from messages";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

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
            connection.close();
        } catch (SQLException e) {
            System.out.println("echec de connexion");

            e.printStackTrace();
        }
    }

}
