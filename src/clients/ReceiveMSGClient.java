package src.clients;

import java.io.OutputStream;
import java.net.Socket;

import src.request.ReceiveMSGs;

public class ReceiveMSGClient {

    private Socket socket;

    public ReceiveMSGClient(Socket socket) {
        this.socket = socket;
        receiveMSG();
    }

    public void receiveMSG() {

        ReceiveMSGs rMSG = new ReceiveMSGs();
        try {
            OutputStream output = socket.getOutputStream();

            output.write(("receiveMSG" + "\n").getBytes());
            output.write((rMSG.id + "\n").getBytes());
            output.write("$".getBytes());
            output.write("\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
