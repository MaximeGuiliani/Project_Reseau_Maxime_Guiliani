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

    public void getAuthor() {
        boolean wait = false;
        System.out.println("author : ");
        System.out.print("@");

        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();
                if (s1.isEmpty()) {
                    this.author = "";
                    wait = true;
                } else if (!s1.isBlank() && !s1.contains(" ")) {
                    this.author = s1;
                    wait = true;
                } else {
                    System.out.println("please select a valid author name");
                    System.out.print("@");
                }

            }

        }

    }

    private void getTag() {
        boolean wait = false;
        System.out.println("Set tag");
        System.out.print("#");
        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();

                if (s1.contains(" ")) {
                    System.out.println("please select a valid tag");
                    System.out.print("#");

                } else {
                    this.tag = s1;
                    wait = true;
                }

            }
        }
    }

    private void getSinceId() {
        boolean wait = false;
        System.out.print("Since Id : ");
        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();
                if (!s1.isBlank()) {
                    try {
                        Integer.parseInt(s1);
                        this.sinceId = s1;
                        wait = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Please select a valid Integer");
                    }
                } else {
                    this.sinceId = "";
                    wait = true;
                }

            }

        }
    }

    private void getLimit() {

        boolean wait = false;
        System.out.print("LIMIT : ");
        while (!wait) {
            if (sc.hasNextLine()) {
                String s1 = sc.nextLine();
                if (!s1.isBlank()) {
                    try {
                        Integer.parseInt(s1);
                        this.limit = s1;
                        wait = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Please select a valid Integer");
                        System.out.print("LIMIT : ");

                    }
                } else {
                    this.limit = "";
                    wait = true;
                }

            }

        }

    }
}
