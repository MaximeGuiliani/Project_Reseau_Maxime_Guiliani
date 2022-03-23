package src.clients;

import java.net.Socket;

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
        } else {
            System.out.println("error");

        }

    }

}
