package src.flux;

import java.util.Scanner;

import src.request.Request;

public class Unsubscribe extends Request {

    private Scanner sc = new Scanner(System.in);
    public String follow;

    public Unsubscribe() {
        this.header = Header.UNSUBSCRIBE;
        getAuthor();
        getFollow();

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

    public void getFollow() {
        boolean wait = false;
        System.out.println("unsub to : ");
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
