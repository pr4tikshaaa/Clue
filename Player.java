import java.util.ArrayList;

/**
 * Represents a player in the Clue game.
 * A player has a character, a hand of cards, a board position, and game status information.
 */
public class Player {    
    /**
     * The player's name ("Player" followed by number).
     */
    private String playerName;
    /**
     * The player's chosen character name.
     */
    private String characterName;
    /**
     * The player's hand after the cards were shuffled and dealt out.
     */
    private ArrayList<Card> hand;
    /**
     * The player's position's row.
     */
    private int row;
    /**
     * The player's positon's col.
     */
    private int col;
    /**
     * The player's current location.
     */
    private Location location;
    /**
     * The player's current room.
     */
    private Room currentRoom;
    /**
     * The player's dice roll value.
     */
    private int roll;
    /**
     * Whether the player has been eliminated from the game.
     */
    private boolean isOut;
    
    /**
     * Constructs a Player object.
     * 
     * @param playerName the player's name
     * @param characterName the player's character name
     */
    public Player(String playerName, String characterName) {
        this.playerName = playerName;
        this.characterName = characterName;
        hand = new ArrayList<>();
        isOut = false;
    }

    /**
     * Gets player name.
     * 
     * @return player name
     */
    public String getPlayerName() {
        return playerName;
    }
    /**
     * Gets character name.
     * 
     * @return character name
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * Sets character name.
     * 
     * @param name of character
     */
    public void setCharacterName(String name) {
        characterName = name;
    }

    /**
     * Gets player's hand.
     * 
     * @return list of cards representin the player's hand
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Get card given its index within the list.
     * 
     * @param i index of card in the list
     * @return the card
     */
    public Card getCard(int i) {
        return hand.get(i);
    }

    /**
     * Adds a card to a player's hand.
     * 
     * @param card added to hand
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Checks if a player has a card in their hand.
     * 
     * @param c card to check
     * @return true if player has the card, false if not
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
     * Stores a player's board position.
     * 
     * @param r is row of position
     * @param c is col of position
     */
    public void setPosition(int r, int c) {
        this.row = r;
        this.col = c;
    }

    /**
     * Sets the player's location.
     * 
     * @param location is the new location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the player's location.
     * 
     * @return the player's location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gets the player's current room they're in.
     * 
     * @return the room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Gets player's row.
     * 
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets player's col. 
     * 
     * @return col
     */
    public int getCol() {
        return col;
    }

    /**
     * Sets a player's roll value.
     * 
     * @param r is the roll value
     */
    public void setRoll(int r) {
        roll = r;
    }

    /**
     * Gets a player's roll value.
     * 
     * @return the roll value
     */
    public int getRoll() {
        return roll;
    }

    /**
     * Checks if the player is out fo the game or not/
     * 
     * @return true if they are out, false if not
     */
    public boolean isOut() {
        return isOut;
    }
    
    /**
     * Sets whether the player is out of the game or not.
     * 
     * @param out is true if they're out, false if they're still in
     */
    public void setOut(boolean out) { 
        this.isOut = out;
    }
}