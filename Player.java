import java.util.ArrayList;

public class Player {    
    private String playerName;
    private String characterName;
    private ArrayList<Card> hand;
    
    public Player(String playerName, String characterName) {
        this.playerName = playerName;
        this.characterName = characterName;
        hand = new ArrayList<>();
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card getCard(int i) {
        return hand.get(i);
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void rollDice() {
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
        int dice1Dots = dice1.getNumDots();
        int dice2Dots = dice2.getNumDots();
        int roll = dice1Dots + dice2Dots;
        System.out.println("Dice 1: " + dice1Dots + "\nDice 2: " + dice2Dots + "\nRoll: " + roll);
        
    }
}