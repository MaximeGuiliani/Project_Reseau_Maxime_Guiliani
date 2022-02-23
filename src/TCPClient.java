package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class TCPClient {

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            Scanner scan = new Scanner(System.in);
            Socket socket = new Socket();
            SocketAddress endpoint = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
            socket.connect(endpoint);

            String string = scan.nextLine() + '\n';
            OutputStream output = socket.getOutputStream();
            byte[] data = string.getBytes();
            output.write(data);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine();
            System.out.println(line);

            socket.close();

            scan.close();

        } else {
            System.out.println("please select a port and a server");
        }

    }
}