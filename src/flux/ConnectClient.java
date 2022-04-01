package src.flux;

import java.io.OutputStream;
import java.net.Socket;

public class ConnectClient {
    private Socket socket;

    public ConnectClient(Socket socket) {
        this.socket = socket;
        connect();
    }

    private void connect() {
        Connect connect = new Connect(socket);
        try {
            OutputStream output = socket.getOutputStream();
            output.write(("connect" + "\n").getBytes());
            output.write((connect.author + "\n").getBytes());
            output.write("$".getBytes());
            output.write("\r\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
