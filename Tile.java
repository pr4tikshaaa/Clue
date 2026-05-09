public class Tile {
    private int x;
    private int y;
    private boolean walkway;
    private String roomName;

    public Tile(int x, int y, boolean walkway, String roomName) {
        this.x = x;
        this.y = y;
        this.walkway = walkway;
        this.roomName = roomName;
    }

    public boolean isWalkway() {
        return walkway;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        if (walkway) {
            return "Walkway";
        } else {
            return roomName.substring(0, 1);
        }
    }
}