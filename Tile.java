public class Tile {
    private int x;
    private int y;
    // private boolean walkway;
    private String tileType;
    private String roomName;

    public Tile(int x, int y, String tileType, String roomName) {
        this.x = x;
        this.y = y;
        this.tileType = tileType;
        this.roomName = roomName;
    }

    public String getTileType() {
        return tileType;
    }

    public boolean isWalkway() {
        if (tileType.equals("Walkway")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDoorway() {
        if (tileType.equals("Doorway")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRoom() {
        if (tileType.equals("Room")) {
            return true;
        }  else {
            return false;
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    // public void setRoom(int r, int c) {
    //     if ((r == 0 || r == 1) && (c == 0 || c == 1)) {
    //         roomName = "Kitchen";
    //     } else if ((r == 0 || r == 1) && (c == 3 || c == 4)) {
    //         roomName = "Ballroom";
    //     } else {
    //         roomName = "Walkway";
    //     }
    // }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        if (isRoom()) {
            return roomName.substring(0, 1);
        } else if (isDoorway()) {
            return tileType.substring(0, 1);
        } else {
            return roomName.substring(0, 1);
        }
    }
}