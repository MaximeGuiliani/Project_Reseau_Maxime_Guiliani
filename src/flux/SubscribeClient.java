package src.flux;

import java.io.OutputStream;
import java.net.Socket;

public class SubscribeClient {

    private Socket socket;

    public SubscribeClient(Socket socket) {
        this.socket = socket;
        receive();
    }

    public void receive() {
        Subscribe sub = new Subscribe();
        try {
            OutputStream output = socket.getOutputStream();
            output.write(("subscribe" + "\n").getBytes());
            output.write((sub.author + "\n").getBytes());
            output.write((sub.follow + "\n").getBytes());
            output.write("$".getBytes());
            output.write("\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
