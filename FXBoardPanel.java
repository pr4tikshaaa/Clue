import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.ArrayList;

public class FXBoardPanel extends Pane {
    private static final int TILE_SIZE = 25;
    private Canvas canvas;
    private Board gameBoard;
    private ArrayList<Player> activePlayers; // Reference to track player positions dynamically
    
    private ArrayList<Tile> validMoves = new ArrayList<>();
    private Tile hoveredTile = null; 
    private java.util.function.Consumer<Tile> onTileClickedHandler;

    // Updated Constructor to receive your player setup session cleanly
    public FXBoardPanel(Board board, ArrayList<Player> gamePlayers) {
        this.gameBoard = board;
        this.activePlayers = gamePlayers;
        this.canvas = new Canvas(24 * TILE_SIZE, 24 * TILE_SIZE);
        getChildren().add(canvas);
        
        canvas.setOnMouseMoved(event -> {
            int col = (int) (event.getX() / TILE_SIZE);
            int row = (int) (event.getY() / TILE_SIZE);
            
            if (gameBoard.isValidLocation(row, col)) {
                Tile currentTile = gameBoard.getTile(row, col);
                if (currentTile != hoveredTile) {
                    hoveredTile = currentTile;
                    drawBoard(); 
                }
            } else if (hoveredTile != null) {
                hoveredTile = null;
                drawBoard();
            }
        });

        canvas.setOnMouseExited(event -> {
            hoveredTile = null;
            drawBoard();
        });

        canvas.setOnMouseClicked(event -> {
            int clickedCol = (int) (event.getX() / TILE_SIZE);
            int clickedRow = (int) (event.getY() / TILE_SIZE);
            
            if (gameBoard.isValidLocation(clickedRow, clickedCol)) {
                Tile clickedTile = gameBoard.getTile(clickedRow, clickedCol);
                Tile validMoveTarget = getCorrespondingValidMove(clickedTile);
                
                if (validMoveTarget != null && onTileClickedHandler != null) {
                    onTileClickedHandler.accept(validMoveTarget);
                }
            }
        });

        drawBoard();
    }
    
    /**
     * Based on the tile the player is on, this method will find the tiles that the player can move to (or room)
     * @param tile the tile the player is on
     * @return the tiles they can move
     */
    private Tile getCorrespondingValidMove(Tile tile) {
        for (Tile move : validMoves) {
            if (move == tile) return move;
            if (tile.getConnectedRoom() != null && move.getConnectedRoom() == tile.getConnectedRoom()) {
                return move; 
            }
        }
        return null;
    }

    /**
     * based on the character's name, it will give the correct color
     * 
     * @param characterName the character's name
     * @return the color
     */
    private Color getCharacterColor(String characterName) {
        if (characterName == null) return Color.WHITE;
        switch (characterName.trim()) {
            case "Miss Scarlet":
                return Color.web("#e53935"); 
            case "Colonel Mustard":
                return Color.web("#fdd835"); 
            case "Mrs. Peacock":    
                return Color.web("#1e88e5"); 
            case "Mr. Green":       
                return Color.web("#43a047"); 
            case "Professor Plum":      
                return Color.web("#8e24aa");
            case "Dr. Orchid":      
                return Color.web("#ff4dbb"); 
            default:                
                return Color.web("#deb86b"); 
        }
    }

    public void setOnTileClickedListener(java.util.function.Consumer<Tile> listener) {
        this.onTileClickedHandler = listener;
    }

    /**
     * 
     * @param tiles
     */
    public void highlightPossibleMoves(ArrayList<Tile> tiles) {
        this.validMoves = tiles;
        drawBoard(); 
    }

    /**
     * removes the highlights
     */
    public void clearHighlights() {
        this.validMoves.clear();
        this.hoveredTile = null;
        drawBoard();
    }

    /**
     * updates the physical location of the player (when they move to a new location)
     */
    public void refreshPlayerPositions() {
        drawBoard();
    }

    /**
     * draws the board, and changes how it looks
     */
    private void drawBoard() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); 
        
        ArrayList<String> namedRoomsRenderList = new ArrayList<>();


        //Colors the walkways, and different colors for each room
            //has stylistic elements, AI generated (to make the board look more aesthetic)
        //Loop goes through all the tiles on the board
        for (int r = 0; r < 24; r++) {
            for (int c = 0; c < 24; c++) {
                Tile tile = gameBoard.getTile(r, c);
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                //making all the doorways and walkways (Clean tan background)
                if (tile.isWalkway() || tile.isDoorway()) {
                    gc.setFill(Color.web("#d2b48c"));
                    gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                //changing the colors of each room
                } else if (tile.getConnectedRoom() != null) {
                    String roomName = tile.getConnectedRoom().getName();
                    switch (roomName.replaceAll("\\s+", "")) {
                        case "Kitchen":       
                            gc.setFill(Color.web("#060b1e")); break; 
                        case "Ballroom":      
                            gc.setFill(Color.web("#0c1533")); break; 
                        case "Conservatory":  
                            gc.setFill(Color.web("#09112a")); break;
                        case "Study":         
                            gc.setFill(Color.web("#050a1c")); break; 
                        case "Hall":          
                            gc.setFill(Color.web("#111a3e")); break;
                        case "BilliardRoom":  
                            gc.setFill(Color.web("#0a1330")); break; 
                        case "DiningRoom":    
                            gc.setFill(Color.web("#040819")); break;
                        case "Lounge":        
                            gc.setFill(Color.web("#0e173a")); break; 
                        case "Library":       
                            gc.setFill(Color.web("#081027")); break; 
                        case "Cellar":        
                            gc.setFill(Color.web("#161a24")); break; 
                        default:              
                            gc.setFill(Color.web("#0b1324")); break; 
                    }
                    gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                    // Room Walls
                    gc.setStroke(Color.web("#FFFFFF"));
                    gc.setLineWidth(2);
                    if (r > 0 && gameBoard.getTile(r - 1, c).isWalkway()) gc.strokeLine(x, y, x + TILE_SIZE, y);
                    if (r < 23 && gameBoard.getTile(r + 1, c).isWalkway()) gc.strokeLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE);
                    if (c > 0 && gameBoard.getTile(r, c - 1).isWalkway()) gc.strokeLine(x, y, x, y + TILE_SIZE);
                    if (c < 23 && gameBoard.getTile(r, c + 1).isWalkway()) gc.strokeLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE);
                }

                // Grid lines
                gc.setStroke(Color.rgb(255, 255, 255, 0.04));
                gc.setLineWidth(1);
                gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        // PART 2: the labels: writing the name for each room
            //NOTE: some stylistic elements are AI generated
        gc.setFont(Font.font("System", FontWeight.BOLD, 10));
        for (int r = 0; r < 24; r++) {
            for (int c = 0; c < 24; c++) {
                Tile tile = gameBoard.getTile(r, c);
                if (tile.getConnectedRoom() != null) {
                    String rawRoomName = tile.getConnectedRoom().getName();
                    if (!namedRoomsRenderList.contains(rawRoomName) && !rawRoomName.equalsIgnoreCase("Cellar")) {
                        namedRoomsRenderList.add(rawRoomName);
                        
                        int textX = (c * TILE_SIZE) + 10;
                        int textY = (r * TILE_SIZE) + 20;
                        
                        gc.setFill(Color.BLACK);
                        gc.fillText(rawRoomName.toUpperCase(), textX + 1, textY + 1);
                        gc.setFill(Color.web("#deb86b")); // Mansion Gold
                        gc.fillText(rawRoomName.toUpperCase(), textX, textY);
                    }
                }
            }
        }

        //  the hovering part
        if (hoveredTile != null) {
            Tile validTarget = getCorrespondingValidMove(hoveredTile);

            if (validTarget != null) {
                if (hoveredTile.isWalkway() || hoveredTile.isDoorway()) {
                    int x = hoveredTile.getCol() * TILE_SIZE;
                    int y = hoveredTile.getRow() * TILE_SIZE;
                    
                    gc.setFill(Color.rgb(222, 184, 107, 0.45));
                    gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    gc.setStroke(Color.web("#4b56bb"));
                    gc.setLineWidth(1.5);
                    gc.strokeRect(x + 0.5, y + 0.5, TILE_SIZE - 1, TILE_SIZE - 1);
                } 
                else if (hoveredTile.getConnectedRoom() != null) {
                    drawHighlightsRoom(gc, validTarget.getConnectedRoom());
                }
            }
        }

        //Player tokens
        // Reads coordinates directly from game state player collection data
        if (activePlayers != null) {
            for (Player p : activePlayers) {
                if (gameBoard.isValidLocation(p.getRow(), p.getCol())) {
                    int x = p.getCol() * TILE_SIZE;
                    int y = p.getRow() * TILE_SIZE;
                    
                    // Fills piece tracking color based on character names
                    gc.setFill(getCharacterColor(p.getCharacterName())); 
                    gc.fillOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
                    
                    gc.setStroke(Color.web("#0b1324")); 
                    gc.setLineWidth(1.5);
                    gc.strokeOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
                }
            }
        }
    }

    /**
     * helper method for drawBoard()
     *      mainly cause drawBoard() was to crowded
     * highlights rooms
     * 
     * @param gc
     * @param targetRoom room to be highlighted
     */
    private void drawHighlightsRoom(GraphicsContext gc, Room targetRoom)
    {
        gc.setStroke(Color.web("#4b56bb")); 
        gc.setLineWidth(3.5);              

        for (int r = 0; r < 24; r++) 
        {
            for (int c = 0; c < 24; c++) 
            {
                Tile tile = gameBoard.getTile(r, c);
                if (tile.getConnectedRoom() == targetRoom) 
                {
                    int x = c * TILE_SIZE;
                    int y = r * TILE_SIZE;

                    if (r > 0 && gameBoard.getTile(r - 1, c).isWalkway()) gc.strokeLine(x, y, x + TILE_SIZE, y);
                    if (r < 23 && gameBoard.getTile(r + 1, c).isWalkway()) gc.strokeLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE);
                    if (c > 0 && gameBoard.getTile(r, c - 1).isWalkway()) gc.strokeLine(x, y, x, y + TILE_SIZE);
                    if (c < 23 && gameBoard.getTile(r, c + 1).isWalkway()) gc.strokeLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE);
                }
            }
        }
    }
}