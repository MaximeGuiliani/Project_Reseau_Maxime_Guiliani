package src.request;

import java.util.Scanner;

public class ReceiveMSGs extends Request {

    private Scanner sc = new Scanner(System.in);
    public String id;

    public ReceiveMSGs() {
        this.header = Header.RCV_MSG;
        getID();
    }

    private void getID() {
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
