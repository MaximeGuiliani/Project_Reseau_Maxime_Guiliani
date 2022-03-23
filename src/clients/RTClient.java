
package src.clients;

import java.io.OutputStream;
import java.net.Socket;

import src.request.RT;

public class RTClient {

    private Socket socket;

    public RTClient(Socket socket) {
        this.socket = socket;
        rt();
    }

    private void rt() {
        RT rt = new RT();
        try {
            OutputStream output = socket.getOutputStream();
            output.write(("rt" + "\n").getBytes());
            output.write((rt.author + "\n").getBytes());
            output.write((rt.id + "\n").getBytes());
            output.write("$".getBytes());
            output.write("\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
