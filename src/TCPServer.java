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

public class TCPServer {

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

                // String id = result.getString("id");
                String author = result.getString("author");
                String message = result.getString("message");
                String date = result.getString("date");

                new PublishHandler(author, message, date);

            }
        } catch (SQLException e) {
            System.out.println("echec de connexion");

            e.printStackTrace();
        }
    }

}
