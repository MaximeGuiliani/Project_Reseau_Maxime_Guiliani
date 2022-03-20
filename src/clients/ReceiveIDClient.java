package src.clients;

import java.io.OutputStream;
import java.net.Socket;

import src.request.ReceiveIDs;

public class ReceiveIDClient {

    private Socket socket;

    public ReceiveIDClient(Socket socket) {
        this.socket = socket;
        receiveID();
    }

    private void receiveID() {
        ReceiveIDs rID = new ReceiveIDs();
        try {
            OutputStream output = socket.getOutputStream();

            output.write(("receiveID" + "\n").getBytes());
            output.write((rID.author + "\n").getBytes());
            output.write((rID.tag + "\n").getBytes());
            output.write((rID.sinceId + "\n").getBytes());
            output.write((rID.limit + "\n").getBytes());
            output.write("$".getBytes());
            output.write("\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
