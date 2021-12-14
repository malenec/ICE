import java.util.ArrayList;
import java.util.Collections;

//PLAYER CLASS: HOW EACH PLAYER IS SET UP
public class Player {
    private String name;
    public int totalGameScore;
    public boolean hasClosed;
    public ArrayList<Card> cardsOnHand = new ArrayList<>();
    public ArrayList<Card> cardsOnTable = new ArrayList<>();


    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    //PRINTS OUT THE cardsOnHand ARRAYLIST TO HAVE EACH CARD SIDE BY SIDE
    public void showHand() {
        sortCardsOnHand();
        int id = 1;
        String cardID = "";
        String cardSuit = "";
        String cardName = "";
        String cardNumber = "";
        String cardValue = "";

        System.out.println(name + "'s Hand:");
        for(Card c: cardsOnHand) {
            cardID += "Card " + id + ":             ";
            cardSuit += "Suit: " + c.getSuit() + "       ";
            cardName += "Kind: " + c.getCardName() + "        ";

            if(c.getNumber() >= 10) {
                cardNumber += "Number: " + c.getNumber() + "          ";
            } else {
                cardNumber += "Number: " + c.getNumber() + "           ";
            }

            if(c.getPointValue() == 5) {
                cardValue += "Point Value: " + c.getPointValue() + "      ";
            } else {
                cardValue += "Point Value: " + c.getPointValue() + "     ";
            }

            id ++;
        }
        System.out.println(cardID + "\n" + cardSuit + "\n" + cardName + "\n" + cardNumber + "\n" + cardValue + "\n");

    }

    //PRINTS OUT THE cardsOnTable ARRAYLIST TO HAVE EACH CARD SIDE BY SIDE
    public void showTable() {
        int id = 1;
        String cardID = "";
        String cardSuit = "";
        String cardName = "";
        String cardNumber = "";
        String cardValue = "";

        System.out.println(name +"'s Table:");
        for(Card c: cardsOnTable) {
            cardID += "Card " + id + ":             ";
            cardSuit += "Suit: " + c.getSuit() + "       ";
            cardName += "Kind: " + c.getCardName() + "        ";

            if(c.getNumber() >= 10) {
                cardNumber += "Number: " + c.getNumber() + "          ";
            } else {
                cardNumber += "Number: " + c.getNumber() + "           ";
            }

            if(c.getPointValue() == 5) {
                cardValue += "Point Value: " + c.getPointValue() + "      ";
            } else {
                cardValue += "Point Value: " + c.getPointValue() + "     ";
            }

            id ++;
        }
        System.out.println(cardID + "\n" + cardSuit + "\n" + cardName + "\n" + cardNumber + "\n" + cardValue + "\n");

    }

    //CALCULATES THIS PLAYER'S TOTAL POINTS FOR ONE ROUND
    public void calculatePoints() {
        int totalOnHand = 0;
        int totalOnTable = 0;

        for(Card c: cardsOnHand) {
            totalOnHand += c.getPointValue();
        }
        for(Card x: cardsOnTable) {
            totalOnTable += x.getPointValue();
        }

        int totalThisRound = totalOnTable - totalOnHand;
        this.totalGameScore += totalThisRound;
        System.out.println("\n" + name + " scored " + totalThisRound + " points this round \n");


    }

    //SORTS THIS PLAYER'S HAND SO THAT IT IS ORGANIZED BY SUITS
    private void sortCardsOnHand () {
        ArrayList<Card> sortedCards = new ArrayList<>();
        String[] suits = {"heart  ", "spade  ", "club   ", "diamond"};
        boolean jokerFound = false;

        //todo: SORT CARDSONHAND AFTER NUMBER BEFORE AFTER SUIT

        for(int i = 0; i < suits.length; i++) {
            for(Card c: cardsOnHand) {
                if(c.getSuit() == null) {
                    jokerFound = true;
                } else if(c.getSuit().equalsIgnoreCase(suits[i])) {
                    sortedCards.add(c);
                }
            }
        }

        if(jokerFound == true) {
            for(Card x: cardsOnHand) {
                if (x.getCardName().equalsIgnoreCase("joker ") && x.getSuit() == null) {
                    sortedCards.add((x));
                }
            }
        }

        cardsOnHand.clear();

        for(Card y: sortedCards) {
            cardsOnHand.add(y);
        }

    }



}
