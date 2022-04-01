package src.clients;

import java.net.Socket;
import java.util.Scanner;

import src.flux.ConnectClient;

public class Client {
    public Client(String requestType, Socket socket) {
        if (requestType.equals("publish") || requestType.equals("pub")) {
            new PublishClient(socket);
        } else if (requestType.equals("receiveID") || requestType.equals("rid")) {
            new ReceiveIDClient(socket);
        } else if (requestType.equals("receiveMSG") || requestType.equals("rmsg")) {
            new ReceiveMSGClient(socket);
        } else if (requestType.equals("reply")) {
            new ReplyClient(socket);
        } else if (requestType.equals("rt") || requestType.equals("republish")) {
            new RTClient(socket);
        } else if (requestType.equals("con") || requestType.equals("connect")) {
            new ConnectClient(socket);
        } else {
            System.out.println(" !! error !! ");
            System.out.println("please select a valid request");
            Scanner scan = new Scanner(System.in);
            boolean waitend = false;
            while (!waitend) {
                String string = scan.nextLine();
                new Client(string, socket);
                waitend = true;

            }
            scan.close();

        }

    }

}
