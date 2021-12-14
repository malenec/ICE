//DEN ENKELTE RUNDE

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Stack;

public class Round {
    public static Deck deck;
    public static Stack<Card> stack;
    public static boolean isFirstRound;
    private Player currentPlayer;
    private boolean hasToPutThree;

    public void runRound () {
        setNewRound();
        playRound();
        calculateRound();

    }


    //EACH INDIVIDUAL ROUND
    private void setNewRound(){
        //RESET PLAYER HANDS AND TABLES
        for(Player p: Game.playerList) {
            p.cardsOnHand.clear();
            p.cardsOnTable.clear();
        }

        // MAKE DECK
        deck = new Deck();
        deck.createDeck();

        // MAKE STACK
        stack = new Stack();

        // GIVE PLAYERS 7 CARDS ON HAND
        for(Player p: Game.playerList) {
            for(int i = 0; i<7; i++) {
                p.cardsOnHand.add(deck.deck.get(deck.deck.size()-1));
                deck.deck.remove(deck.deck.size()-1);
                p.hasClosed = false;
            }
        }

        // ADD ONE CARD TO STACK
        stack.push(deck.deck.get(deck.deck.size()-1));
        deck.deck.remove(deck.deck.size()-1);


    }

    private void playRound() {
        isFirstRound = true;

        boolean playBoolean = true;                     // boolean that tracks whether someone has closed the round
        int i = 0;                                      // variable that tracks which players turn it is

        while(playBoolean == true) {
            if(i >= Game.playerList.size()) {           // if all players has had a turn
                i = 0;                                  // return to the first player
                isFirstRound = false;                   // first round is over, more options are now available
            }
            currentPlayer = Game.playerList.get(i);
            System.out.println("PLAYER : " + currentPlayer.getName());
            takeTurn();
            if (currentPlayer.hasClosed == true) {
                playBoolean = false;
            }
            i++;
        }


    }

    private void calculateRound() {
        for(Player p: Game.playerList) {
            p.calculatePoints();
        }

        System.out.println(Game.scoreboard.toString());

    }



    //EACH INDIVIDUAL TURN
    public void takeTurn () {
        System.out.println(" ");
        UI.getUserInput("Are you ready to start your turn? Press any key when ready: ");
        currentPlayer.showHand();
        pickUpPhase();
        playPhase();
        endPhase();
    }


    //PICK UP PHASE: PLAYER HAS TO PICK UP A CARD/STACK
    //
    private void pickUpPhase() {
        System.out.println("\nYou can now choose to pick up: \n- A card from the deck (write deck)\n- The top card in the stack (write top)");
        if(Round.isFirstRound == false) {
            System.out.println("- The entire stack (write stack). REMEMBER you get -50 points if you cannot put 3 cards in a row down!");
        }
        System.out.println("\nTop card in stack is: \n" + Round.stack.peek() + "\n");

        boolean rerunBoolean;
        do {
            rerunBoolean = false;
            String input = UI.getUserInput("Write choice here: ");
            if (input.equalsIgnoreCase("deck")) {
                pickUpCardFromDeck();
            } else if (input.equalsIgnoreCase("top")) {
                pickUpCardFromStack();
            } else if (input.equalsIgnoreCase("stack") && Round.isFirstRound == false) {
                takeStack();
            } else {
                System.out.println("Invalid input. Please try again");
                rerunBoolean = true;
            }
        } while (rerunBoolean == true);



    }
    //

    private void pickUpCardFromDeck () {
        Card c = Round.deck.deck.get(Round.deck.deck.size()-1);
        currentPlayer.cardsOnHand.add(c);
        Round.deck.deck.remove(c);
        System.out.println("\nYou picked up: \n" + c.toString() + "\n");
    }

    private void pickUpCardFromStack () {
        Card c = Round.stack.pop();
        currentPlayer.cardsOnHand.add(c);
    }

    private void takeStack () {
        for(Card c: Round.stack) {
            currentPlayer.cardsOnHand.add(c);
        }
        Round.stack.clear();

        hasToPutThree = true;

        int tableSize = currentPlayer.cardsOnTable.size();
        boolean exceptionIsCaught;
        do {
            exceptionIsCaught = false;
            try {
                putCardsDownOnTable();
            } catch (InputMismatchException e) {
                System.out.println("Do not write letters when you need to write a number!");
                exceptionIsCaught = true;
            }
        } while(exceptionIsCaught);

        if(currentPlayer.cardsOnTable.size() < tableSize + 3) {
            currentPlayer.totalGameScore -= 50;
            System.out.println("\nYou have lost 50 points\n");

        }

    }

    // PLAY PHASE: PLAYER HAS TO PLAY
    //
    private void playPhase () {

        if (!isFirstRound) {
            boolean turnIsOver = false;
            while (!turnIsOver) {
                currentPlayer.showHand();
                showPlayMenu();
                boolean rerunBoolean;
                do {
                    rerunBoolean = false;
                    String input = UI.getUserInput("Write choice here: ");
                    if (input.equals("1")) {
                        viewPlayerTable();
                    } else if (input.equals("2")) {
                        switchCardWithJoker();
                    } else if (input.equals("3")) {
                        hasToPutThree = false;
                        try {
                            putCardsDownOnTable();
                        } catch(InputMismatchException e) {
                            System.out.println("Do not write letters when you need to write a number!");
                        }
                    } else if (input.equals("4")) {
                        turnIsOver = true;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                        rerunBoolean = true;
                    }
                } while (rerunBoolean == true);
            }

        }


    }
    //

    private void showPlayMenu() {
        System.out.println("\n CHOICE MENU\nPress the number to do the action. Press 0 to go back\n");
        System.out.println("1. View another player's table");
        System.out.println("2. Switch another player's joker with your card");
        System.out.println("3. Put cards from your hand on your table");
        System.out.println("4. Put a card at the top of the stack and end your turn");

    }

    private void viewPlayerTable() {
        System.out.println("Which player's table would you like to view?: ");
        playerNamePrint();

        Player chosenPlayer = new Player(" ");
        boolean rerunBoolean;
        do {
            rerunBoolean = false;
            String input = UI.getUserInput("Write player name here: ");

            if(input.equalsIgnoreCase("0")) {
                playPhase();
            }
            boolean playerIsFound = false;
            for(Player p: Game.playerList) {
                if(p.getName().equalsIgnoreCase(input)) {
                    playerIsFound = true;
                    chosenPlayer = p;
                }
            }
            if(playerIsFound == false) {
                System.out.println("This player does not exist. Please try again");
                rerunBoolean = true;
            }

        } while (rerunBoolean == true);

        chosenPlayer.showTable();
    }


    private void switchCardWithJoker() {
        System.out.println("Which player's table would you like to switch with?: ");
        playerNamePrint();
        Player chosenPlayer = new Player(" ");
        boolean rerunBoolean;
        do {
            rerunBoolean = false;
            String input = UI.getUserInput("Write player name here: ");

            if(input.equalsIgnoreCase("0")) {
                return;
            }
            boolean playerIsFound = false;
            for(Player p: Game.playerList) {
                if(p.getName().equalsIgnoreCase(input)) {
                    playerIsFound = true;
                    chosenPlayer = p;
                }
            }
            if(playerIsFound == false) {
                System.out.println("This player does not exist. Please try again");
                rerunBoolean = true;
            }

        } while (rerunBoolean == true);


        boolean chosenPlayerHasJoker = false;

        for(Card c: chosenPlayer.cardsOnTable) {
            if(c.getPointValue() == 25) {
                chosenPlayerHasJoker = true;
            }
        }

        if(chosenPlayerHasJoker) {
            boolean rerun;

            do {
                rerun = false;
                chosenPlayer.showTable();
                currentPlayer.showHand();
                int intInput = 0;

                System.out.println("Choose which card from your hand you would like to switch for the joker");

                boolean exceptionIsCaught;
                do {
                    exceptionIsCaught = false;
                    try {
                        intInput = UI.getUserInt("Write the card ID here: ");
                    } catch (InputMismatchException e) {
                        System.out.println("Please write a number!");
                        exceptionIsCaught = true;
                    }
                } while (exceptionIsCaught);


                if(intInput >= 1 && intInput <= currentPlayer.cardsOnHand.size()) {
                    Card chosenCard = currentPlayer.cardsOnHand.get(intInput-1);
                    boolean hasSwitched = false;
                    for(int i = 0; i<chosenPlayer.cardsOnTable.size(); i++) {
                        Card y = chosenPlayer.cardsOnTable.get(i);
                        if(chosenCard.getSuit().equals(y.getSuit()) && chosenCard.getNumber() == y.getNumber()) {
                            hasSwitched = true;
                            y.setNumber(0);
                            y.setSuit(null);
                            chosenPlayer.cardsOnTable.add(i,chosenCard);
                            currentPlayer.cardsOnHand.add(y);
                            chosenPlayer.cardsOnTable.remove(y);
                            currentPlayer.cardsOnHand.remove(chosenCard);

                        }

                    }
                    if(!hasSwitched) {
                        rerun = true;
                        System.out.println("These cards are not the same. Please try again");
                    }

                } else if (intInput == 0) {
                    return;
                } else {
                    rerun = true;
                    System.out.println("Invalid input. Please try again");
                }


            } while(rerun);



        } else {
            System.out.println("This player does not have a joker. Please choose someone else.");
            switchCardWithJoker();
        }


    }

    private void putCardsDownOnTable() throws InputMismatchException {
        System.out.println("\nYou have chosen to put down cards.");
        boolean hasJoker = false;
        for(Card card: currentPlayer.cardsOnHand){
            if(card.getCardName() == "joker ") {
                hasJoker = true;
            }
        }

        if(hasJoker) {
            System.out.println("\nYou have a joker on hand");

            boolean rerun;
            do {
                rerun = false;
                String jokerInput = UI.getUserInput("Would you like to assign/reassign a joker? yes or no: ");
                if (jokerInput.equalsIgnoreCase("yes")) {
                    assignJoker();
                } else if (jokerInput.equalsIgnoreCase("no")) {
                    break;
                } else {
                    System.out.println("Please write yes or no.");
                    rerun = true;
                }
            } while(rerun);

        }

        System.out.println("\nPlease choose each card individually in the order you would like them to be put down.");

        ArrayList<Card> chosenCards = new ArrayList<>();

        currentPlayer.showHand();

        boolean allCardsAdded;
        do {
            allCardsAdded = false;
            int intInput = UI.getUserInt("Write cardID here (Write 0 when done): ");
            if(intInput == 0) {
                allCardsAdded = true;
            } else if(intInput > 0 && intInput <= currentPlayer.cardsOnHand.size()) {
                chosenCards.add(currentPlayer.cardsOnHand.get(intInput-1));

            } else {
                System.out.println("Invalid input. Please try again");
            }

        } while (!allCardsAdded);


        if(chosenCards.size() == 0) {
            return;
        } else if (chosenCards.size() == 1) {
            if(hasToPutThree) {
                System.out.println("\nYou have to put at least 3 cards in a row down! If you cannot do this, type zero to quit.\n");
                putCardsDownOnTable();
            } else {
                boolean canPutDown = false;
                Card chosenCard = chosenCards.get(0);

                for (Player p : Game.playerList) {
                    for (Card c : p.cardsOnTable) {
                        if (chosenCard.getSuit().equals(c.getSuit())) {
                            if (chosenCard.getNumber() == c.getNumber() + 1 || chosenCard.getNumber() == c.getNumber() - 1 || chosenCard.getNumber() == 1 && c.getNumber() == 13 || chosenCard.getNumber() == 13 && c.getNumber() == 1) {
                                canPutDown = true;
                            }

                        }

                    }

                }

                if (canPutDown) {
                    currentPlayer.cardsOnTable.add(chosenCard);
                    currentPlayer.cardsOnHand.remove(chosenCard);
                } else {
                    System.out.println("You cannot put this card down. Please try again");
                    putCardsDownOnTable();
                }
            }

        } else if (chosenCards.size() == 2) {
            System.out.println("You cannot do this action. Either choose 1 card or 3+");
            putCardsDownOnTable();

        } else if (chosenCards.size() >= 3 && chosenCards.size() <= 13){

            String suit = chosenCards.get(0).getSuit();
            boolean allSuitsMatch = true;

            for(Card c: chosenCards) {
                if(!c.getSuit().equalsIgnoreCase(suit)){
                    allSuitsMatch = false;
                }
            }

            if(!allSuitsMatch){
                System.out.println("The suits are not the same! You cannot put this down.. Please try again.");
                putCardsDownOnTable();
            } else {

                boolean isInOrder = true;

                int currentNumber;
                int nextNumber = chosenCards.get(0).getNumber();
                for(Card x: chosenCards) {
                    currentNumber = x.getNumber();

                    if(currentNumber != nextNumber) {
                        isInOrder = false;
                    }

                    if (currentNumber == 13) {
                        nextNumber = 1;

                    } else {
                        nextNumber = currentNumber + 1;
                    }
                }

                if(!isInOrder) {
                    System.out.println("Your cards are not in order. Please try again");
                    putCardsDownOnTable();
                } else {
                    for(Card y: chosenCards) {
                        currentPlayer.cardsOnTable.add(y);
                        currentPlayer.cardsOnHand.remove(y);
                    }
                }
                // todo: Add noget med at hver suit i cardsOnTable er en array af 13,
                //  og kortene bliver added til den rigtige på den rigtige plads


            }



        } else {
            System.out.println("You cannot put this down. Please try again");
            putCardsDownOnTable();
        }



    }

    private void assignJoker() {
        currentPlayer.showHand();
        System.out.println("Please choose which joker you would like to assign. Press 0 to go back");
        int intInput = 0;

        boolean exceptionIsCaught;
        do {
            exceptionIsCaught = false;
            try {
                intInput = UI.getUserInt("Write cardID here: ");
            } catch (InputMismatchException e) {
                System.out.println("Please write a number!\n");
                exceptionIsCaught = true;
            }
        } while (exceptionIsCaught);


        if(intInput > 0 && intInput <= currentPlayer.cardsOnHand.size()) {
            Card chosenCard = currentPlayer.cardsOnHand.get(intInput-1);
            if(chosenCard.getCardName().equals("joker ")) {
                boolean rerunBoolean;

                do {
                    rerunBoolean = false;

                    String suitChoice = UI.getUserInput("What suit would you like the joker to be? Heart, Spade, Diamond or Club?: ");
                    if (suitChoice.equalsIgnoreCase("heart")) {
                        chosenCard.setSuit("heart  ");
                    } else if (suitChoice.equalsIgnoreCase("spade")) {
                        chosenCard.setSuit("spade  ");
                    } else if (suitChoice.equalsIgnoreCase("diamond")) {
                        chosenCard.setSuit("diamond");
                    } else if (suitChoice.equalsIgnoreCase("club")) {
                        chosenCard.setSuit("club   ");
                    } else {
                        System.out.println("Invalid input. Please try again");
                        rerunBoolean = true;
                    }
                } while(rerunBoolean == true);


                do{
                    rerunBoolean = false;
                    try {
                        int numberChoice = UI.getUserInt("Choose which number it should have. From 1 to 13: ");
                        if (numberChoice >= 1 && numberChoice <= 13) {
                            chosenCard.setNumber(numberChoice);

                        } else {
                            System.out.println("The number has to be between 1 and 13! Please try again");
                            rerunBoolean = true;
                        }
                    } catch(InputMismatchException e) {
                        System.out.println("Please write a number!\n");
                        rerunBoolean = true;
                    }

                } while (rerunBoolean == true);



            } else {
                System.out.println("This card is not a joker. Please choose a joker");
                assignJoker();

            }



        } else if (intInput == 0){
            return;
        } else {
            System.out.println("Invalid Input. Please try again");
            assignJoker();
        }


    }


    // END PHASE: PLAYER HAS TO PUT A CARD DOWN
    //
    private void endPhase() {
        if(currentPlayer.cardsOnHand.size() == 1) {
            System.out.println("\nYou only have 1 card left on your hand");

            boolean rerunBoolean;
            do {
                rerunBoolean = false;
                String input = UI.getUserInput("Would you like to try to close? Yes or No: ");
                if (input.equalsIgnoreCase("yes")) {
                    tryToClose();
                } else if (input.equalsIgnoreCase("no")) {
                    putCardInStack();
                } else {
                    System.out.println("Invalid input. Please try again");
                    rerunBoolean = true;
                }

            } while(rerunBoolean == true);
        } else if (currentPlayer.cardsOnHand.size() == 0) {
            return;
        } else {
            putCardInStack();
        }

    }
    //

    private void putCardInStack() {
        boolean rerunBoolean;
        do {
            rerunBoolean = false;
            currentPlayer.showHand();
            int input = 0;

            System.out.println("\nWhich card would you like to put in the stack?: ");

            boolean exceptionIsCaught;
            do {
                exceptionIsCaught = false;
                try {
                    input = UI.getUserInt("Write choice here: ");
                } catch (InputMismatchException e) {
                    exceptionIsCaught = true;
                    System.out.println("Please write a number!");
                }
            } while (exceptionIsCaught);

            if (input - 1 >= currentPlayer.cardsOnHand.size() || input -1 < 0) {
                System.out.println("Invalid input. Please try again");
                rerunBoolean = true;
            } else {
                Card c = currentPlayer.cardsOnHand.get(input-1);
                stack.push(c);
                currentPlayer.cardsOnHand.remove(c);

            }
        } while(rerunBoolean == true);



    }

    private void tryToClose() {
        boolean canClose = true;

        Card chosenCard = currentPlayer.cardsOnHand.get(0);

        for (Player p : Game.playerList) {
            for (Card c : p.cardsOnTable) {
                if (chosenCard.getSuit().equals(c.getSuit())) {
                    if (chosenCard.getNumber() == c.getNumber() + 1 || chosenCard.getNumber() == c.getNumber() - 1 || chosenCard.getNumber() == 1 && c.getNumber() == 13) {
                        canClose = false;
                    }

                }

            }

        }

        if(chosenCard.getPointValue() == 25) {
            canClose = false;
        }

        if(canClose) {
            System.out.println("\nYou have closed the game!");
            currentPlayer.hasClosed = true;
        } else {
            System.out.println("\nYou cannot close with this card.");
            if(chosenCard.getPointValue() == 25) {
                System.out.println("You cannot close with a joker. You need to assign it, and place it on the table");

                boolean exceptionIsCaught;
                do {
                    exceptionIsCaught = false;
                    try {
                        putCardsDownOnTable();
                    } catch (InputMismatchException e) {
                        System.out.println("Do not write letters when you need to write a number!");
                        exceptionIsCaught = true;
                    }
                } while(exceptionIsCaught);

            } else {
                currentPlayer.cardsOnTable.add(chosenCard);
                currentPlayer.cardsOnHand.remove(chosenCard);
            }

        }


    }


    // OTHER
    private void playerNamePrint() {
        for(Player p: Game.playerList) {
            System.out.print(p.getName() + ", ");
        }
    }
}
