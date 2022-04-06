package src.flux;

import java.io.BufferedReader;
import java.net.Socket;
import java.util.Scanner;

public class FluxREquestHandler {

    public FluxREquestHandler(String line, BufferedReader reader, Socket socket, String username) {
        if (line.equals("subscribe") || line.equals("sub")) {

            new Subscribe(socket, username);
        } else if (line.equals("unsubscribe") || line.equals("unsub")) {

            new Unsubscribe(socket, username);
        } else {
            System.out.println(" !! error !! ");
            System.out.println("please select a valid request");
            try (Scanner scan = new Scanner(System.in)) {
                boolean waitend = false;
                while (!waitend) {
                    String string = scan.nextLine();
                    new FluxREquestHandler(string, reader, socket, username);
                    waitend = true;

                }
            }
        }
    }
}
