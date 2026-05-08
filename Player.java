import java.util.ArrayList;

public class Player {    
    private String playerName;
    private String characterName;
    private ArrayList<Card> hand;
    private int position;
    private String currentRoom;
    private int roomCount;
//    private GameManager game;
    
    public Player(String playerName, String characterName) {
        this.playerName = playerName;
        this.characterName = characterName;
        hand = new ArrayList<>();
        position = 0;
        roomCount = 0;
        currentRoom = "open";
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int pos) {
        position = pos;
    }

    public String getCurrentRoom() {
        return currentRoom;
    }

    public String setRoom() {
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
    }
}