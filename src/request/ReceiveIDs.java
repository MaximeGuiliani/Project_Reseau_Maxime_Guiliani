package src.request;

import java.util.Scanner;

public class ReceiveIDs extends Request {
    private Scanner sc = new Scanner(System.in);
    public String limit;
    public String sinceId;
    public String tag;

    public ReceiveIDs() {
        this.header = Header.RCV_IDS;
        getAuthor();
        getTag();
        getSinceId();
        getLimit();

    }

    private void getLimit() {
        boolean wait = false;
        System.out.println("OPTIONAL  :  LIMIT");

        while (!wait) {
            if (sc.hasNextLine()) {

                String s1 = sc.nextLine();
                this.limit = s1;
                wait = true;

            }

        }

        sc.close();

    }

    private void getSinceId() {
        boolean wait = false;
        System.out.println("Since Id");
        Scanner sc = new Scanner(System.in);
        while (!wait) {
            if (sc.hasNextLine()) {

                String s1 = sc.nextLine();
                this.sinceId = s1;
                wait = true;

            }

        }
    }

    private void getTag() {
        boolean wait = false;
        System.out.println("Set tag");
        Scanner sc = new Scanner(System.in);
        while (!wait) {
            if (sc.hasNextLine()) {

                String s1 = sc.nextLine();
                this.tag = s1;
                wait = true;

            }

        }
    }

    public void getAuthor() {
        boolean wait = false;
        System.out.println("Set author");
        Scanner sc = new Scanner(System.in);
        while (!wait) {
            if (sc.hasNextLine()) {

                String s1 = sc.nextLine();
                this.author = s1;
                wait = true;

            }

        }

    }

}
