package src.request;

import java.util.Scanner;

public class Publish extends Request {
    private Scanner sc = new Scanner(System.in);

    public Publish() {
        this.header = Header.PUBLISH;
        getAuthor();
        getMessage();

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

    public void getMessage() {
        boolean wait = false;
        System.out.println("Set message :");
        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();

                if (!s1.isBlank()) {
                    this.message = s1;
                    wait = true;

                } else {
                    System.out.println("please select a valid message");

                }

            }

        }

    }

}
