import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    public ArrayList<Card> deck = new ArrayList<>();
    private String[] suits = {"heart  ", "spade  ", "club   ", "diamond"};
    private int[] numbers = {1, 11, 12, 13, 10, 2, 3, 4, 5, 6, 7, 8, 9};
    private String[] cardNames = {"ace   ", "knight", "queen ", "king  ", "number"};
    private int[] pointValues = {15, 10, 5};
    int amountOfJokers = Game.amountOfJokers;


    public void createDeck () {
        for(int i = 0; i< suits.length; i++) {                      // iterates through the 4 suits
            for(int j = 0; j < numbers.length; j++) {               // for each suit this iterates through the 13 numbers
                String cardName;                                    // declares local variable for card name
                int pointValue;                                     // declares local variable for point value
                if(j >= 0 && j<=3) {                                // if the number is 1, 11, 12 or 13
                    cardName = cardNames[j];                        // a card gets a 'special' name like ace, knight etc
                    if(numbers[j] == 1) {                           // if the number is 1, meaning an ace
                        pointValue = pointValues[0];                // the point value is set to 15
                    } else {
                        pointValue = pointValues[1];                // for number 11, 12 and 13 point value is set to 10
                    }
                } else {
                    cardName = cardNames[4];                        // the remaining cards 2-10 get the name 'number'
                    if (numbers[j] == 10) {                         // if the number is 10
                        pointValue = pointValues[1];                // the point value is set to 10
                    } else {
                        pointValue = pointValues[2];                // the remaining cards get the point value 5
                    }
                }

                Card card = new Card(suits[i], numbers[j], cardName, pointValue);   // each card is instantiated based on the above
                deck.add(card);                                                     // each card is added to the deck
            }
        }
        if (amountOfJokers > 0) {                                               // if 1-4 jokers are chosen by user
            for (int k = 0; k < amountOfJokers; k++) {                          // this iterates through each joker
                Card joker = new Card("joker ", 25);        // instantiates with name and point value
                deck.add(joker);                                                // adds each joker to the deck
            }
        }

        Collections.shuffle(deck);                                              // and finally the deck gets shuffled
    }



}
