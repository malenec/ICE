import java.util.ArrayList;
import java.util.InputMismatchException;

//THE BIG GAME
public class Game {
    public static ArrayList<Player> playerList = new ArrayList<>();
    public int scoreLimit = 500;
    public static int amountOfJokers = 0;
    private Round round;
    public static Scoreboard scoreboard = new Scoreboard();

    //RUNS THE ENTIRE GAME
    public void runGame() {
        System.out.println("WELCOME TO 500!\n");
        gameSettings();
        runRounds();
        endOfGame();

    }

    // SETS THE GAME'S OVERALL SETTINGS LIKE AMOUNT OF PLAYERS & SCORE LIMIT
    private void gameSettings () {
        playerSettings();
        scoreLimitSettings();
        jokerSettings();

    }

    private void playerSettings () {
        System.out.println("You now have to add players! Minimum 2 & Maximum 6\nTo stop adding players write STOP");

        boolean rerunBoolean;
        do{
            rerunBoolean = true;

            String input = UI.getUserInput("Write player name here: ");


            if(input.equals("STOP")) {
                if(playerList.size() < 2) {
                    System.out.println("You need to have at least two players");
                } else {

                    rerunBoolean = false;
                }
            } else {
                Player newPlayer = new Player(input);
                playerList.add(newPlayer);
                scoreboard.addPlayer(newPlayer);
            }

            if(playerList.size() >= 6) {
                System.out.println("You cannot add more players");
                rerunBoolean = false;
            }


        } while (rerunBoolean == true);

        System.out.println("These players have been added: ");
        for(int i = 0; i < playerList.size(); i++) {
            System.out.print(playerList.get(i).getName() + ", ");
        }

    }

    private void scoreLimitSettings () {
        System.out.println("\nDo you want to change the score limit? (Default is 500)");
        boolean rerunBoolean;
        do {
            rerunBoolean = false;
            String input = UI.getUserInput("Write yes or no: ");

            if (input.equalsIgnoreCase("no")) {
                System.out.println("You have choosen to play to 500.");

            } else if (input.equalsIgnoreCase("yes")) {
                boolean tryAgainBoolean;
                do {
                    tryAgainBoolean = false;

                    try {
                        int limitInput = UI.getUserInt("Write your desired limit here: ");

                        if (limitInput <= 0) {
                            System.out.println("Invalid Input. Please try again");
                            tryAgainBoolean = true;
                        } else if (limitInput > 10000) {
                            System.out.println("You will never finish this game! Please choose something smaller :)");
                            tryAgainBoolean = true;
                        } else {
                            scoreLimit = limitInput;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("You need to write a number!\n");
                        tryAgainBoolean = true;
                    }

                } while (tryAgainBoolean == true);
            } else {
                System.out.println("Invalid input. Please try again");
                rerunBoolean = true;
            }

        } while (rerunBoolean == true);

    }

    private void jokerSettings () {
        System.out.println("Choose your desired amount of jokers. You can have from 0 to 4");
        boolean rerunBoolean;
        do {
            rerunBoolean = false;

            try {
                int input = UI.getUserInt("Write your desired amount here: ");
                if (input >= 0 && input <= 4) {
                    amountOfJokers = input;
                } else {
                    System.out.println("Invalid input. Please try again");
                    rerunBoolean = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("You have to write a number!");
                rerunBoolean = true;
            }
        } while (rerunBoolean);


    }

    //RUNS ALL ROUNDS
    private void runRounds() {

        boolean gameIsOver = false;
        do {
            round = new Round();
            round.runRound();

            if(scoreboard.getHighestScore() >= scoreLimit) {
                gameIsOver = true;
            }

        } while(!gameIsOver);

    }

    //RUNS WHEN SOMEONE HAS WON THE ENTIRE GAME
    private void endOfGame() {
        System.out.println("\nThe game is over! Here is the scoreboard: ");
        System.out.println(scoreboard.toString());
        System.out.println("\n Congratulations to " + scoreboard.list.get(0).getName() + " for winning");
    }



}
