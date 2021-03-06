package src.RequestHandler;

import java.io.BufferedReader;
import java.net.Socket;

import src.flux.ConnectHandler;

public class RequestHandler {

    public RequestHandler(String line, BufferedReader reader, Socket socket) {
        if (line.equals("publish")) {
            new PublishHandler(reader, socket);
        } else if (line.equals("receiveID")) {
            new ReceiveIDHandler(reader, socket);
        } else if (line.equals("receiveMSG")) {
            new ReceiveMSGHandler(reader, socket);
        } else if (line.equals("reply")) {
            new ReplyHandler(reader, socket);
        } else if (line.equals("rt")) {
            new RTHandler(reader, socket);
        } else if (line.equals("connect")) {
            new ConnectHandler(reader, socket);
        }
    }

}
