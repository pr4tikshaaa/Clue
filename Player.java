import java.util.ArrayList;

/**
 * 
 */
public class Player {    
    /**
     * 
     */
    private String playerName;
    /**
     * 
     */
    private String characterName;
    /**
     * 
     */
    private ArrayList<Card> hand;
    /**
     * 
     */
    private int row;
    /**
     * 
     */
    private int col;
    /**
     * 
     */
    private Location location;
    /**
     * 
     */
    private Room currentRoom;
    /**
     * 
     */
    private int roll;
    /**
     * 
     */
    private boolean isOut;
    
    /**
     * 
     * @param playerName
     * @param characterName
     */
    public Player(String playerName, String characterName) {
        this.playerName = playerName;
        this.characterName = characterName;
        hand = new ArrayList<>();
        // position = 0;
        // roomCount = 0;
        // currentRoom = "Walkway";
        isOut = false;
    }

    /**
     * 
     * @return
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * 
     * @return
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * 
     * @param name
     */
    public void setCharacterName(String name) {
        characterName = name;
    }

    /**
     * 
     * @return
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * 
     * @param i
     * @return
     */
    public Card getCard(int i) {
        return hand.get(i);
    }

    /**
     * 
     * @param card
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * 
     * @param c
     * @return
     */
    public boolean hasCard(Card c) {
        for (Card card : hand) {
            if (c.equals(card)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param r
     * @param c
     */
    public void setPosition(int r, int c) {
        this.row = r;
        this.col = c;
    }

    /**
     * 
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @return
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * 
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     * 
     * @return
     */
    public int getCol() {
        return col;
    }

    /**
     * 
     * @param r
     */
    public void setRoll(int r) {
        roll = r;
    }

    /**
     * 
     * @return
     */
    public int getRoll() {
        return roll;
    }

    /**
     * 
     * @return
     */
    public boolean isOut() {
        return isOut;
    }
    
    /**
     * 
     * @param out
     */
    public void setOut(boolean out) { 
        this.isOut = out;
    }
}