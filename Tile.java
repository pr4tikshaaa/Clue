public class Tile implements Location {
    private int row;
    private int col;
    // private boolean walkway;
    private String tileType;
    private Room connectedRoom;
    //private Room secretPassage;
    private boolean occupied;

    public Tile(int r, int c, String tileType, Room connectedRoom) {
        row = r;
        col = c;
        this.tileType = tileType;
        this.connectedRoom = connectedRoom;
        occupied = false;
    }

    public String getTileType() {
        return tileType;
    }

    public void setTileType(String tileType) {
        this.tileType = tileType;
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


    // public Room getRoom() {
    //     return ;
    // }

    // public void setRoomName(String roomName) {
    //     this.roomName = roomName;
    // }

    // public void setRoom(int r, int c) {
    //     if ((r == 0 || r == 1) && (c == 0 || c == 1)) {
    //         roomName = "Kitchen";
    //     } else if ((r == 0 || r == 1) && (c == 3 || c == 4)) {
    //         roomName = "Ballroom";
    //     } else {
    //         roomName = "Walkway";
    //     }
    // }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString() {
        if (isOccupied()) {
            return "P";
        } else if (isRoom()) {
            return "X".substring(0, 1);
        } else if (isDoorway()) {
            return "D";
        } else {
            return "-".substring(0, 1);
        }
    }

    public void setConnectedRoom(Room room) {
        connectedRoom = room;
    }

    public Room getConnectedRoom() {
        return connectedRoom;
    }

    @Override
    public String getName() {
        if (isDoorway()) {
            return "Doorway";
        } else {
            return "Walkway";
        }
    }


    // private void setSecretPassage(Room secret) {
    //     secretPassage = secret;
    // }

    // private Room getSecretPassage() {
    //     return secretPassage;
    // }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Tile) {
            Tile other = (Tile) obj;
            return (row == other.row && col == other.col);
        }
        return false;
    }

    public int hashCode() {
        return row*100 + col;
    }
}