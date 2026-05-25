/**
 * Represents a single tile on the Clue game board.
 * A tile is either a walkway, doorway, or room.
 */
public class Tile implements Location {
    /**
     * The tile's row position on the board.
     */
    private int row;
    /**
     * The tile's col position on the board.
     */
    private int col;
    /**
     * The type of tyle, either walkway, doorway, or room.
     */
    private String tileType;
    /**
     * The room connected to this tile, if it is connected to one.
     */
    private Room connectedRoom;
    /**
     * Whether a player is on the tile.
     */
    private boolean occupied;

    /**
     * Constructs a tile of the game board.
     * 
     * @param r is the row of the tile
     * @param c is the col of the tile
     * @param tileType is the type of tile
     * @param connectedRoom is the room is the connected to if it is
     */
    public Tile(int r, int c, String tileType, Room connectedRoom) {
        row = r;
        col = c;
        this.tileType = tileType;
        this.connectedRoom = connectedRoom;
        occupied = false;
    }

    /**
     * Gets the tile type.
     * 
     * @return tile type
     */
    public String getTileType() {
        return tileType;
    }

    /**
     * Sets the tile type.
     * 
     * @param tileType tile type
     */
    public void setTileType(String tileType) {
        this.tileType = tileType;
    }

    /**
     * Checks if a tile is a walkway.
     * 
     * @return true if it is a walkway, false if not
     */
    public boolean isWalkway() {
        if (tileType.equals("Walkway")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a tile is a doorway.
     * 
     * @return true if it is a doorway, false if not
     */
    public boolean isDoorway() {
        if (tileType.equals("Doorway")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a tile is a room.
     * 
     * @return true if it is a room, false if not
     */
    public boolean isRoom() {
        if (tileType.equals("Room")) {
            return true;
        }  else {
            return false;
        }
    }

    /**
     * Gets row of tile.
     * 
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets col of tile.
     * 
     * @return col
     */
    public int getCol() {
        return col;
    }

    /**
     * (Used for debugging, in text-based game)
     * 
     * "P" represents a player being on a tile
     * "X" is a tile that makes up a room
     * "D" is a tile that is right infront of the doorway of a room
     * "-" is a tile that is a walkway
     */
    public String toString() {
        if (isOccupied()) {
            return "P";
        } else if (isRoom()) {
            return "X";
        } else if (isDoorway()) {
            return "D";
        } else {
            return "-";
        }
    }

    /**
     * Sets the room a tile is connected to.
     * 
     * @param room the tile is connected to
     */
    public void setConnectedRoom(Room room) {
        connectedRoom = room;
    }

    /**
     * Gets connected room.
     * 
     * @return room the tile is connected
     */
    public Room getConnectedRoom() {
        return connectedRoom;
    }

    /**
     * (Used for debugging in text-based game)
     * 
     * Gets name of the tile (either doorway, walkway, or room).
     */
    @Override
    public String getName() {
        if (isDoorway()) {
            return "Doorway";
        } else if (isWalkway()) {
            return "Walkway";
        } else {
            return "Room";
        }
    }

    /**
     * Checks if a tile is occupied by a player.
     * 
     * @return true if occupied, false if not
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * Sets occupancy check to true or false.
     * 
     * @param occupied is true or false
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    /**
     * Checks if an object is equal to another tile based on its coordinates.
     * 
     * @param obj is object to compare to
     */
    public boolean equals(Object obj) {
        if (obj instanceof Tile) {
            Tile other = (Tile) obj;
            return (row == other.row && col == other.col);
        }
        return false;
    }

    /**
     * Generates a hash code for the tile
     * 
     * @return the tile hash code
     */
    public int hashCode() {
        return row*100 + col;
    }
}