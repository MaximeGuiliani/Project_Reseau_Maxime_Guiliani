package src.clients;

import java.net.Socket;

public class Client {
    public Client(String requestType, Socket socket) {
        if (requestType.equals("publish") || requestType.equals("pub")) {
            new PublishClient(socket);
        } else if (requestType.equals("receiveID") || requestType.equals("rid")) {
            new ReceiveIDClient(socket);
        }

    }

}
