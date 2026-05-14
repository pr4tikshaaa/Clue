import java.util.ArrayList;

public class Player {    
    private String playerName;
    private String characterName;
    private ArrayList<Card> hand;
    // private int position;
    // private String currentRoom;
    private int row;
    private int col;
    private Location location;
    private Room currentRoom;
    // private int roomCount;
    // private GameManager game;
    
    public Player(String playerName, String characterName) {
        this.playerName = playerName;
        this.characterName = characterName;
        hand = new ArrayList<>();
        // position = 0;
        // roomCount = 0;
        // currentRoom = "Walkway";
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCharacterName() {
        return characterName;
    }
    /**
     * needed for UIClueGame to update what character they are
     */
    public void setCharacterName(String charName)
    {
        characterName = charName;
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

    public boolean hasCard(Card c) {
        for (Card card : hand) {
            if (c.equals(card)) {
                return true;
            }
        }
        return false;
    }

    // public int getPosition() {
    //     return position;
    // }

    public void setPosition(int r, int c) {
        this.row = r;
        this.col = c;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location geLocation() {
        return location;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isInRoom() {
        if (currentRoom != null) {
            return true;
        } else {
            return false;
        }
    }


/*     public String setRoom() {
        roomCount++;
        if (roomCount == 1) {
            this.currentRoom = "Ballroom";
        } else if (roomCount == 2) {
            this.currentRoom = "Billiard Room";
        } else if (roomCount == 3) {
            this.currentRoom = "Conservatory";
        } else if (roomCount == 4) {
            this.currentRoom = "Dining Room";
        } else if (roomCount == 5) {
            this.currentRoom = "Hall";
        } else if (roomCount == 6) {
            this.currentRoom = "Kitchen";
        } else if (roomCount == 7) {
            this.currentRoom = "Library";
        } else if (roomCount == 8) {
            this.currentRoom = "Lounge";
        } else if (roomCount == 9) {
            this.currentRoom = "Study";
        }

        return this.currentRoom;
    }

    public int getRoomCount() {
        return roomCount;
    } */
}