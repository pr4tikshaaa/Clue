import java.util.ArrayList;

public class Board {
    private Tile[][] board;
    private ArrayList<Tile> validMoves;

    public Board() {
        board = new Tile[3][5];
        initializeBoard();
    }

    public void initializeBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if ((r == 0 || r == 1) && (c == 0 || c == 1)) {
                    board[r][c] = new Tile(r, c, false, "Kitchen");
                } else if ((r == 0 || r == 1) && (c == 3 || c == 4)) {
                    board[r][c] = new Tile(r, c, false, "Ballroom");
                } else {
                    board[r][c] = new Tile(r, c, false, "Walkway");
                }
            }
        }
    }

    public void printBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(board[r][c] + " ");
            }
            System.out.print("\n");
        }
    }

    public ArrayList<Tile> getValidMoves(Player player, int roll) {
        return validMoves;
    }

    public String getRoom(int r, int c) {
        return board[r][c].getRoomName();
    }

    public boolean isValidLocation(int r, int c) {
        if ((r >= 0 && r < board.length) && (c >= 0 && c < board[0].length)) {
            return true;
        } else {
            return false;
        }
    }
}
