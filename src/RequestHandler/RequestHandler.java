package src.RequestHandler;

import java.io.BufferedReader;
import java.net.Socket;

public class RequestHandler {

    public RequestHandler(String line, BufferedReader reader, Socket socket) {
        if (line.equals("publish")) {
            new PublishHandler(reader, socket);
        } else if (line.equals("receiveID")) {
            new ReceiveIDHandler(reader, socket);
        }
    }

}
