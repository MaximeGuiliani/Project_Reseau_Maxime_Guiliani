package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.Socket;

import src.RequestHandler.RequestHandler;

public class Handler implements Runnable {
    Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream input;
        try {

            input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();

            if (line != null) {
                new RequestHandler(line, reader, socket);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
