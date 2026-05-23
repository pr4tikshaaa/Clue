import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class FXBoardPanel extends Pane {
    private static final int TILE_SIZE = 25;
    private Canvas canvas;
    private Board gameBoard;
    
    private ArrayList<Tile> validMoves = new ArrayList<>();
    private Tile hoveredTile = null; // Track which tile the mouse is over
    private java.util.function.Consumer<Tile> onTileClickedHandler;

    public FXBoardPanel(Board board) {
    this.gameBoard = board;
    this.canvas = new Canvas(24 * TILE_SIZE, 24 * TILE_SIZE);
    getChildren().add(canvas);
    
    // 1. LISTEN FOR MOUSE MOVEMENT (HOVER EFFECT)
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

    // 2. LISTEN FOR MOUSE CLICKS
    canvas.setOnMouseClicked(event -> {
        int clickedCol = (int) (event.getX() / TILE_SIZE);
        int clickedRow = (int) (event.getY() / TILE_SIZE);
        
        if (gameBoard.isValidLocation(clickedRow, clickedCol)) {
            Tile clickedTile = gameBoard.getTile(clickedRow, clickedCol);
            
            // Check if this tile or its containing room is a valid move option
            Tile validMoveTarget = getCorrespondingValidMove(clickedTile);
            
            if (validMoveTarget != null && onTileClickedHandler != null) {
                // Pass the verified tile target back to the controller
                onTileClickedHandler.accept(validMoveTarget);
            }
        }
    });

    drawBoard();
}

// Helper method to see if a tile (or the room it belongs to) is in our valid moves list
private Tile getCorrespondingValidMove(Tile tile) {
    for (Tile move : validMoves) {
        if (move == tile) return move;
        if (tile.getConnectedRoom() != null && move.getConnectedRoom() == tile.getConnectedRoom()) {
            return move; // Found the room match!
        }
    }
    return null;
}

    public void setOnTileClickedListener(java.util.function.Consumer<Tile> listener) {
        this.onTileClickedHandler = listener;
    }

    // Instead of explicitly auto-highlighting, we just store the valid options quietly
    public void highlightPossibleMoves(ArrayList<Tile> tiles) {
        this.validMoves = tiles;
        drawBoard(); 
    }

    public void clearHighlights() {
        this.validMoves.clear();
        this.hoveredTile = null;
        drawBoard();
    }

    public void refreshPlayerPositions() {
        drawBoard();
    }

    // --- RENDERING PIPELINE ---
    private void drawBoard() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); 
        
        // --- PASS 1: RENDER BASE MAP ASSETS ---
        for (int r = 0; r < 24; r++) {
            for (int c = 0; c < 24; c++) {
                Tile tile = gameBoard.getTile(r, c);
                int x = c * TILE_SIZE;
                int y = r * TILE_SIZE;

                // 1. RENDER WALKWAYS AND DOORWAYS (Clean tan background)
                if (tile.isWalkway() || tile.isDoorway()) {
                    gc.setFill(Color.web("#d2b48c"));
                    gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                // 2. RENDER THE ROOM OBJECT BODIES
                } else if (tile.getConnectedRoom() != null) {
                    String roomName = tile.getConnectedRoom().getName();
                    switch (roomName) {
                        case "Kitchen": gc.setFill(Color.web("#ffd1dc")); break;
                        case "Ballroom": gc.setFill(Color.web("#c8dcff")); break;
                        case "Conservatory": gc.setFill(Color.web("#c6ebc5")); break;
                        case "Study": gc.setFill(Color.web("#e6c8fa")); break;
                        case "Hall": gc.setFill(Color.web("#ffe6b4")); break;
                        case "BilliardRoom": gc.setFill(Color.web("#b4f0f0")); break;
                        case "DiningRoom": gc.setFill(Color.web("#f0f0b4")); break;
                        case "Lounge": gc.setFill(Color.web("#ffb4dc")); break;
                        case "Library": gc.setFill(Color.web("#d2c8b4")); break;
                        case "Cellar": gc.setFill(Color.web("#969696")); break;
                        default: gc.setFill(Color.WHITE); break;
                    }
                    gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                    // Standard Aesthetic Dark Brown Room Walls
                    gc.setStroke(Color.web("#826446"));
                    gc.setLineWidth(2);
                    if (r > 0 && gameBoard.getTile(r - 1, c).isWalkway()) gc.strokeLine(x, y, x + TILE_SIZE, y);
                    if (r < 23 && gameBoard.getTile(r + 1, c).isWalkway()) gc.strokeLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE);
                    if (c > 0 && gameBoard.getTile(r, c - 1).isWalkway()) gc.strokeLine(x, y, x, y + TILE_SIZE);
                    if (c < 23 && gameBoard.getTile(r, c + 1).isWalkway()) gc.strokeLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE);
                }

                // Subtle base background grid layer line overlay
                gc.setStroke(Color.rgb(50, 50, 50, 0.12));
                gc.setLineWidth(1);
                gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        // --- PASS 2: RENDER INTERACTIVE HOVER EFFECTS LAYER ---
        if (hoveredTile != null) {
            Tile validTarget = getCorrespondingValidMove(hoveredTile);

            if (validTarget != null) {
                // Scenario A: Hovering a plain Walkway/Doorway -> Shimmer the single square box
                if (hoveredTile.isWalkway() || hoveredTile.isDoorway()) {
                    int x = hoveredTile.getCol() * TILE_SIZE;
                    int y = hoveredTile.getRow() * TILE_SIZE;
                    
                    gc.setFill(Color.rgb(222, 184, 107, 0.45));
                    gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    gc.setStroke(Color.web("#deb86b"));
                    gc.setLineWidth(1.5);
                    gc.strokeRect(x + 0.5, y + 0.5, TILE_SIZE - 1, TILE_SIZE - 1);
                } 
                // Scenario B: Hovering a Valid Room -> Trace ONLY the outer room walls in glowing Gold!
                else if (hoveredTile.getConnectedRoom() != null) {
                    gc.setStroke(Color.web("#deb86b")); // Glowing Mansion Gold
                    gc.setLineWidth(3.5);              // Thick distinct neon outline stroke

                    for (int r = 0; r < 24; r++) {
                        for (int c = 0; c < 24; c++) {
                            Tile tile = gameBoard.getTile(r, c);
                            if (tile.getConnectedRoom() == validTarget.getConnectedRoom()) {
                                int x = c * TILE_SIZE;
                                int y = r * TILE_SIZE;

                                // If the adjacent tile outside this wall is a walkway, light up that outer edge!
                                if (r > 0 && gameBoard.getTile(r - 1, c).isWalkway()) gc.strokeLine(x, y, x + TILE_SIZE, y);
                                if (r < 23 && gameBoard.getTile(r + 1, c).isWalkway()) gc.strokeLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE);
                                if (c > 0 && gameBoard.getTile(r, c - 1).isWalkway()) gc.strokeLine(x, y, x, y + TILE_SIZE);
                                if (c < 23 && gameBoard.getTile(r, c + 1).isWalkway()) gc.strokeLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE);
                            }
                        }
                    }
                }
            }
        }

        // --- PASS 3: RENDER TOKENS (Keep them on top layer) ---
        for (int r = 0; r < 24; r++) {
            for (int c = 0; c < 24; c++) {
                Tile tile = gameBoard.getTile(r, c);
                if (tile.isOccupied()) {
                    int x = c * TILE_SIZE;
                    int y = r * TILE_SIZE;
                    gc.setFill(Color.web("#a62337")); 
                    gc.fillOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
                    gc.setStroke(Color.WHITE);
                    gc.setLineWidth(1);
                    gc.strokeOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
                }
            }
        }
    }
}