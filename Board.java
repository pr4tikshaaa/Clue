import java.util.ArrayList;

public class Board {
    private Tile[][] board;
    private ArrayList<Tile> validMoves;
    private Room kitchen;
    private Room ballroom;
    private Room conservatory;
    private Room study;
    private Room hall;
    private Room billiardRoom;
    private Room diningRoom;
    private Room lounge;
    private Room library;
    private Room cellar;

    public Board() {
        board = new Tile[24][24];
        kitchen = new Room("Kitchen");
        ballroom = new Room("Ballroom");
        conservatory = new Room("Conservatory");
        study = new Room("Study");
        hall = new Room("Hall");
        billiardRoom = new Room("BilliardRoom");
        diningRoom = new Room("DiningRoom");
        lounge = new Room("Lounge");
        library = new Room("Library");
        cellar = new Room("Cellar");
        initializeBoard();
    }


    public void initializeBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if ((r >= 0 && r < 4) && (c >= 0 && c < 7)) {
                    board[r][c] = new Tile(r, c, "Room", study);
                } else if ((r >= 16 && r < 24) && (c >= 8 && c < 16)) {
                    board[r][c] = new Tile(r, c, "Room", ballroom);
                } else if ((r >= 0 && r < 7) && (c >= 9 && c < 15)) {
                    board[r][c] = new Tile(r, c, "Room", hall);
                } else if ((r >= 0 && r < 6) && (c >= 17 && c < 24)) {
                    board[r][c] = new Tile(r, c, "Room", lounge);
                } else if ((r >= 6 && r < 11) && (c >= 0 && c < 7)) {
                    board[r][c] = new Tile(r, c, "Room", library);
                } else if ((r >= 12 && r < 16) && (c >= 0 && c < 6)) {
                    board[r][c] = new Tile(r, c, "Room", billiardRoom);
                } else if ((r >= 18 && r < 24) && (c >= 0 && c < 6)) {
                    board[r][c] = new Tile(r, c, "Room", conservatory);
                } else if ((r >= 8 && r < 14) && (c >= 9 && c < 14)) {
                    board[r][c] = new Tile(r, c, "Room", cellar);
                } else if ((r >= 9 && r < 15) && (c >= 16&& c < 24)) {
                    board[r][c] = new Tile(r, c, "Room", diningRoom);
                } else if ((r >= 17 && r < 24) && (c >= 18 && c < 24)) {
                    board[r][c] = new Tile(r, c, "Room", kitchen);
                } else {
                    board[r][c] = new Tile(r, c, "Walkway", null);
                }
            }
        }
        
        board[4][6] = new Tile(4,6, "Doorway", study);
        board[18][7] = new Tile(18, 7, "Doorway", ballroom);
        board[15][9] = new Tile(15, 9, "Doorway", ballroom);
        board[15][14] = new Tile(15, 14, "Doorway", ballroom);
        board[18][16] = new Tile(18, 16, "Doorway", ballroom);
        board[18][5] = new Tile(18, 5, "Doorway", conservatory);
        board[14][6] = new Tile(14, 6, "Doorway", billiardRoom);
        board[11][1] = new Tile(11, 1, "Doorway", billiardRoom);
        board[11][3] = new Tile(11, 3, "Doorway", library);
        board[8][7] = new Tile(8, 7, "Doorway", library);
        board[4][8] = new Tile(4, 8, "Doorway", hall);
        board[7][11] = new Tile(7,11, "Doorway", hall);
        board[7][12] = new Tile(7, 12, "Doorway", hall);
        board[6][17] = new Tile(6, 17, "Doorway", lounge);
        board[8][17] = new Tile(8, 17, "Doorway", diningRoom);
        board[12][15] = new Tile(12, 15, "Doorway", diningRoom);
        board[16][19] = new Tile(16, 19, "Doorway", kitchen);

        study.addDoor(board[4][6]);
        ballroom.addDoor(board[18][7]);
        ballroom.addDoor(board[15][9]);
        ballroom.addDoor(board[15][14]);
        ballroom.addDoor(board[18][16]);
        conservatory.addDoor(board[18][5]);
        billiardRoom.addDoor(board[14][6]);
        billiardRoom.addDoor(board[11][1]);
        library.addDoor(board[11][3]);
        library.addDoor(board[8][7]);
        hall.addDoor(board[4][8]);
        hall.addDoor(board[7][11]);
        hall.addDoor(board[7][12]);
        lounge.addDoor(board[6][17]);
        diningRoom.addDoor(board[8][17]);
        diningRoom.addDoor(board[12][15]);
        kitchen.addDoor(board[16][19]);
        
        board[6][6] = new Tile(6, 6, "Walkway", null);
        board[10][6] = new Tile(10, 6, "Walkway", null);
        board[23][9] = new Tile(23, 9, "Walkway", null);
        board[23][8] = new Tile(23, 8, "Walkway", null);
        board[22][9] = new Tile(22, 9, "Walkway", null);
        board[22][8] = new Tile(22, 8, "Walkway", null);
        board[22][14] = new Tile(22, 14, "Walkway", null);
        board[22][15] = new Tile(22, 15, "Walkway", null);
        board[23][14] = new Tile(23, 14, "Walkway", null);
        board[23][15] = new Tile(23, 15, "Walkway", null);
        board[14][16] = new Tile(14, 16, "Walkway", null);
        board[14][17] = new Tile(14, 17, "Walkway", null);
        board[14][18] = new Tile(14, 18, "Walkway", null);

        conservatory.addPlayerSpot(board[20][1]);
        conservatory.addPlayerSpot(board[20][2]);
        conservatory.addPlayerSpot(board[20][3]);
        conservatory.addPlayerSpot(board[21][1]);
        conservatory.addPlayerSpot(board[21][2]);
        conservatory.addPlayerSpot(board[21][3]);

        billiardRoom.addPlayerSpot(board[13][1]);
        billiardRoom.addPlayerSpot(board[13][2]);
        billiardRoom.addPlayerSpot(board[13][3]);
        billiardRoom.addPlayerSpot(board[14][1]);
        billiardRoom.addPlayerSpot(board[14][2]);
        billiardRoom.addPlayerSpot(board[14][3]);

        library.addPlayerSpot(board[7][1]);
        library.addPlayerSpot(board[7][2]);
        library.addPlayerSpot(board[7][3]);
        library.addPlayerSpot(board[8][1]);
        library.addPlayerSpot(board[8][2]);
        library.addPlayerSpot(board[8][3]);

        study.addPlayerSpot(board[1][1]);
        study.addPlayerSpot(board[1][2]);
        study.addPlayerSpot(board[1][3]);
        study.addPlayerSpot(board[2][1]);
        study.addPlayerSpot(board[2][2]);
        study.addPlayerSpot(board[2][3]);

        hall.addPlayerSpot(board[2][10]);
        hall.addPlayerSpot(board[2][11]);
        hall.addPlayerSpot(board[2][12]);
        hall.addPlayerSpot(board[3][10]);
        hall.addPlayerSpot(board[3][11]);
        hall.addPlayerSpot(board[3][12]);

        lounge.addPlayerSpot(board[2][19]);
        lounge.addPlayerSpot(board[2][20]);
        lounge.addPlayerSpot(board[2][21]);
        lounge.addPlayerSpot(board[3][19]);
        lounge.addPlayerSpot(board[3][20]);
        lounge.addPlayerSpot(board[3][21]);

        diningRoom.addPlayerSpot(board[10][19]);
        diningRoom.addPlayerSpot(board[10][20]);
        diningRoom.addPlayerSpot(board[10][21]);
        diningRoom.addPlayerSpot(board[11][19]);
        diningRoom.addPlayerSpot(board[11][20]);
        diningRoom.addPlayerSpot(board[11][21]);

        kitchen.addPlayerSpot(board[19][20]);
        kitchen.addPlayerSpot(board[19][21]);
        kitchen.addPlayerSpot(board[19][22]);
        kitchen.addPlayerSpot(board[20][20]);
        kitchen.addPlayerSpot(board[20][21]);
        kitchen.addPlayerSpot(board[20][22]);

        ballroom.addPlayerSpot(board[18][11]);
        ballroom.addPlayerSpot(board[18][12]);
        ballroom.addPlayerSpot(board[18][13]);
        ballroom.addPlayerSpot(board[19][11]);
        ballroom.addPlayerSpot(board[19][12]);
        ballroom.addPlayerSpot(board[19][13]);

        kitchen.setSecretPassage(study);
        study.setSecretPassage(kitchen);
        lounge.setSecretPassage(conservatory);
        conservatory.setSecretPassage(lounge);
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
        validMoves = new ArrayList<>();
        boolean[][] visited = new boolean[board.length][board[0].length];
        findMoves(player.getRow(), player.getCol(), roll, visited);
        Tile currentTile = board[player.getRow()][player.getCol()];

        if (currentTile.isRoom()) {
            Room currentRoom = currentTile.getConnectedRoom();
            
            if (currentRoom.getSecretPassage() != null) {
                Room destinationRoom = currentRoom.getSecretPassage();

                validMoves.add(destinationRoom.getPlayerSpot());
            }
        }
        return validMoves;
    }
    
    private void findMoves(int row, int col, int stepsLeft, boolean[][] visited) {
        if (!isValidLocation(row, col)) {
            return;
        }

        if (visited[row][col]) {
            return;
        }

        Tile current = board[row][col];

        visited[row][col] = true;

        if (stepsLeft == 0) {
            if (current.isWalkway() || current.isDoorway()) {
                validMoves.add(current);
            } else if (current.isRoom()) {
                boolean alreadyAdded = false;

                for (Tile t : validMoves) {
                    if (t.getConnectedRoom() == current.getConnectedRoom()) {
                        alreadyAdded = true;
                        break;
                    }
                }

                if (!alreadyAdded) {
                    validMoves.add(current);
                }
            }

            visited[row][col] = false;
            return;
        }

        ArrayList<Tile> neighbors = getNeighbors(current);

        for (Tile next : neighbors) {
            if (next.isRoom() && !current.isRoom()) {
                boolean alreadyAdded = false;

                for (Tile t : validMoves) {
                    if (t.getConnectedRoom() == next.getConnectedRoom()) {
                        alreadyAdded = true;
                        break;
                    }
                }

                if (!alreadyAdded) {
                    validMoves.add(next);
                }
            } else {
                findMoves(next.getRow(), next.getCol(), stepsLeft - 1, visited);
            }
        }

        visited[row][col] = false;
    }

    public String getRoom(Player player) {
        return board[player.getRow()][player.getCol()].getName();
    }

    public void setPlayer(Player player, int r, int c) {
        if (!isValidLocation(r, c)) {
            return;
        }

        Tile clickedTile = board[r][c];

        if (clickedTile.getConnectedRoom() != null) {
            Room room = clickedTile.getConnectedRoom();

            Tile spot = room.getPlayerSpot();

            if (spot != null) {
                r = spot.getRow();
                c = spot.getCol();
            }
        }

        if (clickedTile.isOccupied()) {
            return;
        }

        if (isValidLocation(player.getRow(), player.getCol())) {
            board[player.getRow()][player.getCol()].setOccupied(false);
        }

        player.setPosition(r, c);
        board[r][c].setOccupied(true);
    }

    public boolean isValidLocation(int r, int c) {
        if ((r >= 0 && r < board.length) && (c >= 0 && c < board[0].length)) {
            return true;
        } else {
            return false;
        }
    }

    public Tile getTile(int r, int c) {
        return board[r][c];
    }

    public ArrayList<Tile> getNeighbors(Tile current) {
        ArrayList<Tile> neighbors = new ArrayList<>();
        int row = current.getRow();
        int col = current.getCol();

        if (current.isRoom() && current.getConnectedRoom() != null) {
            return current.getConnectedRoom().getDoors();
        }

        checkAndAddNeighbor(current, neighbors, row + 1, col);
        checkAndAddNeighbor(current, neighbors, row - 1, col);
        checkAndAddNeighbor(current, neighbors, row, col + 1);
        checkAndAddNeighbor(current, neighbors, row, col - 1);
        
        return neighbors;
}

    private void checkAndAddNeighbor(Tile current, ArrayList<Tile> neighbors, int r, int c) {
        if (!isValidLocation(r, c)) return;

        Tile target = board[r][c];

        if (target.isWalkway() || target.isDoorway()) {
            neighbors.add(target);
            return;
        }

        if (target.getTileType().equals("Room") && current.isDoorway()) {
            if (current.getConnectedRoom() == target.getConnectedRoom()) {
                neighbors.add(target);
            }
        }
    }
}

