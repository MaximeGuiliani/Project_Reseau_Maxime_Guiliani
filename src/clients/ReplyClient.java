package src.clients;

import java.io.OutputStream;
import java.net.Socket;

import src.request.Reply;

public class ReplyClient {

    private Socket socket;

    public ReplyClient(Socket socket) {
        this.socket = socket;
        reply();
    }

    private void reply() {
        Reply reply = new Reply();
        try {
            OutputStream output = socket.getOutputStream();

            output.write(("reply" + "\n").getBytes());
            output.write((reply.author + "\n").getBytes());
            output.write((reply.message + "\n").getBytes());
            output.write((reply.id + "\n").getBytes());
            output.write("$".getBytes());
            output.write("\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
