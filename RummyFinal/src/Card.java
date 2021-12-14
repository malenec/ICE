//CARD CLASS: HOW EACH INDIVIDUAL CARD LOOKS

public class Card {
    private String suit;
    private int number;
    private String cardName;
    private int pointValue;

    public Card(String suit, int number, String cardName, int pointValue) {
        this.suit = suit;
        this.number = number;
        this.cardName = cardName;
        this.pointValue = pointValue;
    }

    // ONLY FOR JOKERS
    public Card(String cardName, int pointValue){
        this.cardName = cardName;
        this.pointValue = pointValue;
    }

    @Override
    public String toString() {
        String s = "Suit: " + getSuit() + "\n" +
                "Kind: " + getCardName() + "\n" +
                "Number: " + getNumber() + "\n" +
                "Point Value: " + getPointValue();
        return s;
    }

    public String getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    public String getCardName() {
        return cardName;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}