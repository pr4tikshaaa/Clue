import java.util.ArrayList;

/**
 * Represents a room on the Clue board.
 * A room is a location and it has a name.
 * A room has doors, connects to secret passages, and player spots within the rooms.
 */
public class Room implements Location {
    /**
     * The name of the room.
     */
    private String name;
    /**
     * The doors connected to the room.
     */
    private ArrayList<Tile> doors;
    /**
     * The secret passages that connect to the room.
     */
    private Room secretPassage;
    /**
     * The player spots inside of the room.
     */
    private ArrayList<Tile> playerSpots;

    /**
     * Constructs a room object with a name
     * 
     * @param name of the room
     */
    public Room(String name) {
        this.name = name;
        doors = new ArrayList<>();
        playerSpots = new ArrayList<>();
    }

    /**
     * Gets the name of the room.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets a room as a secret passage
     * 
     * @param secret room to connect to
     */
    public void setSecretPassage(Room secret) {
        secretPassage = secret;
    }

    /**
     * Adds specified tiles as doors to the room
     * 
     * @param door tile
     */
    public void addDoor(Tile door) {
        doors.add(door);
    }

    /**
     * Gets a list that contains all the tiles that are doors to the room
     * 
     * @return ArrayList of doors
     */
    public ArrayList<Tile> getDoors() {
        return doors;
    }

    /**
     * Gets secret passage of the room.
     * 
     * @return the room that's a secret passage
     */
    public Room getSecretPassage() {
        return secretPassage;
    }

    /**
     * Adds a position inside the room where players can be placed at.
     * 
     * @param tile the player spot tile
     */
    public void addPlayerSpot(Tile tile) {
        playerSpots.add(tile);
    }

    /**
     * Returns an open player spot tile.
     * 
     * @return a tile of the first available player spot.
     */
    public Tile getPlayerSpot() {
        for (Tile t : playerSpots) {
            if (!t.isOccupied()) {
                return t;
            }
        }
        return playerSpots.get(0);
    }
}
