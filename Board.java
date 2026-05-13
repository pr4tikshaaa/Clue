import java.util.ArrayList;

public class Board {
    private Tile[][] board;
    private ArrayList<Location> validMoves;
    private Room kitchen;
    // private Room ballroom;
    // private Room Conservatory;
    // private Room Study;
    // private Room Hall;
    // private Room BilliardRoom;
    // private Room DiningRoom;
    // private Room Lounge;
    // private Room Library;

    public Board() {
        board = new Tile[3][5];
        kitchen = new Room("Kitchen");
        // ballroom = new Room("Ballroom");
        // conservatory = new Room("Conservatory");
        // study = new Room("Study");
        // hall = new Room("Hall");
        // billiardRoom = new Room("BilliardRoom");
        // diningRoom = new Room("DiningRoom");
        // kounge = new Room("Lounge");
        // library = new Room("Library");
        initializeBoard();
    }


    public void initializeBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if ((r == 0 || r == 1) && (c == 0 || c == 1)) {
                    board[r][c] = new Tile(r, c, "Room", null);
                } else if ((r == 0 || r == 1) && (c == 3 || c == 4)) {
                    board[r][c] = new Tile(r, c, "Room", null);
                } else {
                    board[r][c] = new Tile(r, c, "Walkway", null);
                }
            }
        }

        board[0][2] = new Tile(0, 2, "Doorway", kitchen);
        kitchen.addDoor(board[0][2]);
    }

    public void printBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.print("\n");
        }
    }

    public ArrayList<Location> getValidMoves(Player player, int roll) {
        validMoves = new ArrayList<Location>();

        boolean visited[][] = new boolean[board.length][board[0].length];

        findMoves(player.getRow(), player.getCol(), roll, visited);
        return validMoves;
    }

    public void findMoves(int row, int col, int stepsLeft, boolean[][] visited) {
        if (!isValidLocation(row, col)) {
            return;
        }

        if (visited[row][col]) {
            return;
        }

        Tile current = board[row][col];

        if (current.isRoom()) {
            return;
        }

        if (current.isDoorway()) {
            if (!validMoves.contains(current.getConnectedRoom()))
                validMoves.add(current.getConnectedRoom());
            return;
        }

        //if (current.isop)

        if (stepsLeft == 0) {
            if (!validMoves.contains(current)) {
                validMoves.add(current);
            }
;           return;
        }

        visited[row][col] = true;

        findMoves(row + 1, col, stepsLeft - 1, visited);
        findMoves(row - 1, col, stepsLeft - 1, visited);
        findMoves(row, col + 1, stepsLeft - 1, visited);
        findMoves(row, col - 1, stepsLeft - 1, visited);

        visited[row][col] = false;
    }

    public String getRoom(Player player) {
        return board[player.getRow()][player.getCol()].getName();
    }

    public void setPlayer(Player player, int r, int c) {
        if (isValidLocation(r, c)) {
            player.setPosition(r, c);
        } else {
            player.setPosition(player.getRow(), player.getCol());
        }
    }

    public boolean isValidLocation(int r, int c) {
        if ((r >= 0 && r < board.length) && (c >= 0 && c < board[0].length)) {
            return true;
        } else {
            return false;
        }
    }

}
