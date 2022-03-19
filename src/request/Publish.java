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
        System.out.println("give an author name");

        while (!wait) {
            if (sc.hasNextLine()) {

                String s1 = sc.nextLine();
                this.author = s1;
                wait = true;

            }

        }

    }

    public void getMessage() {
        boolean wait = false;
        System.out.println("Set message");
        Scanner sc = new Scanner(System.in);
        while (!wait) {
            if (sc.hasNextLine()) {

                String s1 = sc.nextLine();
                this.message = s1;
                wait = true;

            }

        }
        sc.close();

    }

}
