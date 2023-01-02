import java.sql.SQLException;
import java.util.ArrayList;

public class Engine {
    public static void main(String[] args) {
        //fillPokedex

        DataAcess data = new SQLDatabase();

        Pokedex pokedex = null;
        try {
            pokedex = data.fillPokedex();
        } catch (SQLException e) {
            data = new CSV();
            try {
                pokedex = data.fillPokedex();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        //lookupGameState
        ArrayList<Integer> enemies = data.lookUpGameState();
        if (enemies.size() == 4) {
            printIntro();
        }
        Trainer player = data.getTrainer(1);
        printSaveData(enemies, player);

        //main game loop
        for (int i = 0; i < enemies.size(); i++) {
            Trainer enemy = data.getTrainer(enemies.get(i));
            printNextEnemy(enemy);
            Arena arena = new Arena(player, enemy, pokedex);
            boolean won = arena.battle();
            if (won) {
                if (i == enemies.size() - 1) {
                    printWonTop4(player);
                    data.deleteData(player);
                } else {
                    boolean continueB = continueToBattle(enemy);
                    if (!continueB) {
                        data.saveData(enemies.get(i), enemies);
                        data.updateTrainer(player);
                        break;
                    }
                }
            } else {
                printLost(enemy);
                //database.deleteData(player);
                break;
            }
        }

    }

    private static boolean continueToBattle(Trainer enemy) {
        print("System", "Congratulations you won against " + enemy.getName() + "!", "Do you want to continue and battle the next member of the top4?", "(yes / no)", "");
        String answer = UserInput.getString("yes", "no", "Type in yes or no!");
        if (answer.equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    private static void printSaveData(ArrayList<Integer> enemies, Trainer player) {
        if (enemies.size() < 4) {
            print("System", "Loading savadata ...", "Welcome back " + player.getName() + "!", "", "");
            System.out.println();
            if (4 - enemies.size() == 1) {
                print("System", "You already defeated " + (4 - enemies.size()) + " enemy from the top 4", "", "", "");
            } else {
                print("System", "You already defeated " + (4 - enemies.size()) + " enemies from the top 4", "", "", "");
            }
        }
    }

    private static void printIntro() {
        System.out.println("\t\t" + "System");
        printPoints();
        printLine("Welcome to the top4!");
        printLine("");
        printLine("It's the hardest challenge for every trainer to overcome.");
        printLine("I wish you the best luck on your way to the top!");
        printLine("");
        printLine("");

        printPoints();
        System.out.println();
    }

    public static void print(String who, String text1, String text2, String text3, String text4) {
        System.out.println();
        System.out.println("\t\t" + who);
        printPoints();
        printLine(text1);
        printLine(text2);
        printLine(text3);
        printLine(text4);
        printLine("");
        printLine("");
        printPoints();
        System.out.println();
    }

    public static void printLine(String text) {
        System.out.print("\t\t");
        System.out.printf("%-2s", "|");
        System.out.printf("%-68s", text);
        System.out.printf("%10s", "|\n");
    }

    public static void printPoints() {
        System.out.print("\t\t");
        for (int i = 0; i < 79; i++) {
            System.out.print(".");
        }
        System.out.println();
    }


    public static void printLost(Trainer enemy) {
        print("System", "Sorry you lost to " + enemy.getName() + "!", "You are not ready yet to defeat the top4.", "Good luck next time", "");
    }

    public static void printNextEnemy(Trainer enemy) {
        print("System", "Your next fight will be against " + enemy.getName() + "!", "", "", "");
    }

    public static void printWonTop4(Trainer player) {
        print("System", "Congratulations " + player.getName() + "!", "You won against the top4!", "You are now one of the best Pokemon Trainers!", "To Be Continued ...");
    }


}