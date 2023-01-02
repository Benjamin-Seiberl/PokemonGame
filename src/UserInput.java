import java.util.Scanner;

public class UserInput {
    public static int getInt(int min, String errorText) {
        int number = 0;

        do {
            try {
                Scanner sc = new Scanner(System.in);
                String tmp = sc.nextLine();
                number = Integer.parseInt(tmp);
            } catch (Exception e) {
                System.out.println(errorText);
            }
        } while (number <= min);
        System.out.println();
        return number;
    }

    public static int getInt(int min, int max, String errorText) {
        int number = 0;
        System.out.print("\t\t- ");
        do {
            try {
                Scanner sc = new Scanner(System.in);
                String tmp = sc.nextLine();
                number = Integer.parseInt(tmp);
            } catch (Exception e) {
                System.out.println(errorText);
            }
        } while (number < min || number > max);
        System.out.println();
        return number;
    }

    public static String getString(String errorText) {
        String input = "";
        System.out.print("\t\t- ");
        boolean error = false;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                input = sc.nextLine();
            } catch (Exception e) {
                System.out.println(errorText);
                error = true;
            }
        } while (error);

        return input;
    }

    public static String getString(String yes, String no, String errorText) {
        String input = "";
        do {
            try {
                Scanner sc = new Scanner(System.in);
                input = sc.nextLine();
            } catch (Exception e) {
                System.out.println(errorText);
            }
        } while (!input.equals(yes) && !input.equals(no));

        return input;
    }

    public static double getDouble(double min, String errorText){
        String input = "";
        double number = 0;
        do {
            try {
                Scanner sc = new Scanner(System.in);
                input = sc.nextLine();
                number = Double.parseDouble(input);
                if(number < min){
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println(errorText);

            }
        } while (number <= min);

        return number;
    }
}
