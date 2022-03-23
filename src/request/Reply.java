package src.request;

import java.util.Scanner;

public class Reply extends Request {

    private Scanner sc = new Scanner(System.in);
    public String id;

    public Reply() {
        this.header = Header.REPLY;
        getAuthor();
        getMessage();
        getId();

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

    private void getId() {
        boolean wait = false;
        System.out.println("ID  :  ");

        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();
                if (!s1.isBlank()) {
                    try {
                        Integer.parseInt(s1);
                        this.id = s1;
                        wait = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Please select a valid Integer");
                        System.out.print("ID  :  ");

                    }
                } else {
                    System.out.println("Please select a valid Integer");
                    System.out.print("ID  :  ");

                }

            }
        }

    }

}
