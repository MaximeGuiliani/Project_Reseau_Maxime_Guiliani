package src.flux;

import java.io.BufferedReader;
import java.net.Socket;

public class FluxREquestHandler {

    public FluxREquestHandler(String line, BufferedReader reader, Socket socket, String username) {
        if (line.equals("subscribe") || line.equals("sub")) {

            new Subscribe(socket, username);
        } else if (line.equals("unsubscribe") || line.equals("unsub")) {

            new Unsubscribe(socket, username);
        }
    }
}
