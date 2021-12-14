import java.util.Scanner;

//UI CLASS: GETS USER INPUTS
public class UI {
    public static String getUserInput(String MSG) {
        System.out.println(MSG);
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        return input;
    }


    public static int getUserInt(String MSG) {
        System.out.println(MSG);
        Scanner scan = new Scanner(System.in);
        int input = scan.nextInt();
        return input;
    }

}
