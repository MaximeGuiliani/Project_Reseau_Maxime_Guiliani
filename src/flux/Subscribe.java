package src.flux;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import src.request.Request;

public class Subscribe extends Request {
    private Scanner sc = new Scanner(System.in);
    public String follow;
    private Socket socket;
    private String username;

    public Subscribe(Socket socket, String username) {
        this.username = username;
        this.socket = socket;
        this.header = Header.SUBSCRIBE;
        getFollow();
        System.out.println("\n------------------------------------\n");
        updateSQL();
        new ConnectHandler(socket, username);

    }

    public void getFollow() {
        boolean wait = false;
        System.out.println("Sub to : ");
        System.out.print("@");

        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();

                if (!s1.isBlank() && !s1.contains(" ")) {
                    this.follow = s1;
                    wait = true;
                } else {
                    System.out.println("Please select a valid author name");
                    System.out.print("@");
                }
            }
        }

    }

    public void updateSQL() {
        String jdbxUrl = "jdbc:sqlite:table.db";

        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);

            String sql = "insert into follows (username,following) VALUES(?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, follow);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
