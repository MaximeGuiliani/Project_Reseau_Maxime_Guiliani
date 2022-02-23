package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TCPServer {

    public static void main(String[] args) throws IOException {
        Executor executor_stealing = Executors.newWorkStealingPool();

        try (ServerSocket serverSocket = new ServerSocket(1234)) {

            while (true) {
                executor_stealing.execute(new Handler(serverSocket.accept()));
            }
        }

    }

}
