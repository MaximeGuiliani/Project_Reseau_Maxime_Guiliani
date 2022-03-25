package src.clients;

import java.io.OutputStream;
import java.net.Socket;

import src.request.Publish;
import src.request.Request;

public class PublishClient {

    private Socket socket;

    public PublishClient(Socket socket) {
        this.socket = socket;
        publish();
    }

    private void publish() {
        Request pub = new Publish();
        try {
            OutputStream output = socket.getOutputStream();

            output.write(("publish" + "\n").getBytes());
            output.write((pub.author + "\n").getBytes());
            output.write((pub.message + "\n").getBytes());

            output.write("$".getBytes());
            output.write("\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
