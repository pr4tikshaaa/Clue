import java.util.ArrayList;

public class Room implements Location {
    private String name;
    private ArrayList<Tile> doors;
    private Room secretPassage;
    private ArrayList<Tile> playerSpots;

    public Room(String name) {
        this.name = name;
        doors = new ArrayList<>();
        playerSpots = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    
    public void setSecretPassage(Room secret) {
        secretPassage = secret;
    }

    public void addDoor(Tile door) {
        doors.add(door);
    }

    public ArrayList<Tile> getDoors() {
        return doors;
    }

    public Room getSecretPassage() {
        return secretPassage;
    }

    public void addPlayerSpot(Tile tile) {
        playerSpots.add(tile);
    }

    public Tile getPlayerSpot() {
        for (Tile t : playerSpots) {
            if (!t.isOccupied()) {
                return t;
            }
        }
        return playerSpots.get(0);
    }
}
