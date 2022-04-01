package src.flux;

import java.io.OutputStream;
import java.net.Socket;

public class UnsubscribeClient {

    private Socket socket;

    public UnsubscribeClient(Socket socket) {
        this.socket = socket;
        receive();
    }

    public void receive() {
        Unsubscribe sub = new Unsubscribe();
        try {
            OutputStream output = socket.getOutputStream();
            output.write(("unsubscribe" + "\n").getBytes());
            output.write((sub.author + "\n").getBytes());
            output.write((sub.follow + "\n").getBytes());
            output.write("$".getBytes());
            output.write("\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
