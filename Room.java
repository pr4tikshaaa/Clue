import java.util.ArrayList;

/**
 * 
 */
public class Room implements Location {
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private ArrayList<Tile> doors;
    /**
     * 
     */
    private Room secretPassage;
    /**
     * 
     */
    private ArrayList<Tile> playerSpots;

    /**
     * 
     * @param name
     */
    public Room(String name) {
        this.name = name;
        doors = new ArrayList<>();
        playerSpots = new ArrayList<>();
    }

    /**
     * 
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param secret
     */
    public void setSecretPassage(Room secret) {
        secretPassage = secret;
    }

    /**
     * 
     * @param door
     */
    public void addDoor(Tile door) {
        doors.add(door);
    }

    /**
     * 
     * @return
     */
    public ArrayList<Tile> getDoors() {
        return doors;
    }

    /**
     * 
     * @return
     */
    public Room getSecretPassage() {
        return secretPassage;
    }

    /**
     * 
     * @param tile
     */
    public void addPlayerSpot(Tile tile) {
        playerSpots.add(tile);
    }

    /**
     * 
     * @return
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
