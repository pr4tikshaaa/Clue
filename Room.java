import java.util.ArrayList;

public class Room implements Location {
    private String name;
    private ArrayList<Tile> doors;
    private Room secretPassage;

    public Room(String name) {
        this.name = name;
        doors = new ArrayList<>();
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
}
