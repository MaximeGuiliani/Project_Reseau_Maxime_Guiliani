package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

import src.clients.Client;

public class TCPClient {

    public static void main(String[] args) throws IOException {

        if (args.length == 2) {
            boolean waitend = false;
            System.out.print("Choose new request : ");
            Scanner scan = new Scanner(System.in);
            Socket socket = new Socket();
            SocketAddress endpoint = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
            socket.connect(endpoint);

            while (!waitend) {

                String string = scan.nextLine();

                new Client(string, socket);
                waitend = true;

            }
            scan.close();

            InputStream input;
            try {

                input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line = reader.readLine();

                if (line != null) {
                    System.out.println("Status : " + line);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.close();

        } else

        {
            System.out.println("please select a port and a server");
        }

    }
}