package src.flux;

import java.io.BufferedReader;

import java.net.Socket;
import java.util.Scanner;

import src.TCPServer;

public class ConnectHandler implements Runnable {

    private BufferedReader reader;
    private Socket socket;
    private Scanner sc = new Scanner(System.in);
    private String username;

    public ConnectHandler(BufferedReader reader, Socket socket) {
        this.socket = socket;
    }

    public ConnectHandler(Socket socket, String username) {

        this.socket = socket;
        System.out.println("select your request");
        while (true) {
            if (sc.hasNextLine()) {
                System.out.println("You can sub and unsub here : ");
                new FluxREquestHandler(sc.nextLine(), reader, this.socket, this.username);

            }
            if (socket.isClosed()) {
                TCPServer.connectedUsers.remove(this.username, socket);

            }
        }

    }

    @Override
    public void run() {

    }

}
