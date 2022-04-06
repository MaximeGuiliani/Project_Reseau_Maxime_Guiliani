package src.utils;

/* import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; */

public class SQLConected {

    /* private static void getConnected() {
        try {
            String jdbxUrl = "jdbc:sqlite:table.db";
            Connection connection = DriverManager.getConnection(jdbxUrl);
            String sql = "select * from connected";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next()) {
                messageID = Integer.parseInt(result.getString("id"));
                Socket socket = new Socket(address, port);
                if()
                TCPServer.connectedUsers.put(arg0, arg1)
    
    
            }
    
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                String follows = result.getString("username");
    
                Map<String, Socket> connected = TCPServer.getConected();
                Socket socket = connected.get(follows);
                OutputStream output;
                System.out.println("----------------------->" + follows);
    
                System.out.println("----------------------->" + connected);
                if (!socket.isClosed()) {
                    try {
                        output = socket.getOutputStream();
                        output.write(("update" + "\n").getBytes());
                        output.write("$".getBytes());
                        output.write("\r\n".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
    
            }
        } catch (SQLException e) {
            System.out.println("echec de connexion");
    
            e.printStackTrace();
        }
    } */

    /* public void updateSQL() {
        String jdbxUrl = "jdbc:sqlite:table.db";
    
        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);
    
            String sql = "delete from follows where username = ? and following = ?";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, follow);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    
    
    
    public void updateSQL() {
        String jdbxUrl = "jdbc:sqlite:table.db";
    
        try {
            Connection conn = DriverManager.getConnection(jdbxUrl);
    
            String sql = "insert into messages (author,message,date) VALUES(?,?,?)";
    
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, author);
            pstmt.setString(2, message);
            pstmt.setString(3, date);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    } */

}
