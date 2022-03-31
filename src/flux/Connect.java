package src.flux;

import java.net.Socket;
import java.util.Scanner;

import src.request.Request;

public class Connect extends Request {
    private Scanner sc = new Scanner(System.in);
    private Socket socket;

    public Connect(Socket socket) {
        this.socket = socket;
        this.header = Header.CONNECT;
        getAuthor();

    }

    public void getAuthor() {
        boolean wait = false;
        System.out.println("author : ");
        System.out.print("@");

        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();

                if (!s1.isBlank() && !s1.contains(" ")) {
                    this.author = s1;
                    wait = true;
                } else {
                    System.out.println("please select a valid author name");
                    System.out.print("@");
                }

            }

        }

    }

}