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

    private Tile getCorrespondingValidMove(Tile tile) {
        for (Tile move : validMoves) {
            if (move == tile) return move;
            if (tile.getConnectedRoom() != null && move.getConnectedRoom() == tile.getConnectedRoom()) {
                return move; 
            }
        }
        return null;
    }

    // Dynamic Token Mapping Helper Method
    private Color getCharacterColor(String characterName) {
        if (characterName == null) return Color.WHITE;
        switch (characterName.trim()) {
            case "Miss Scarlet":    return Color.web("#e53935"); // Vibrant Crimson Red
            case "Col. Mustard":    return Color.web("#fdd835"); // Deep Yellow
            case "Mrs. Peacock":    return Color.web("#1e88e5"); // Royal Blue
            case "Mr. Green":       return Color.web("#43a047"); // Emerald Forest Green
            case "Prof. Plum":      return Color.web("#8e24aa"); // Classic Purple
            case "Dr. Orchid":      return Color.web("#ffb74d"); // Coral Orange / Pinkish White
            default:                return Color.web("#deb86b"); // Fallback Gold Accent
        }
    }

    public void setOnTileClickedListener(java.util.function.Consumer<Tile> listener) {
        this.onTileClickedHandler = listener;
    }

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
        
        // Track unique rooms to prevent drawing text names repeatedly over every single room tile
        ArrayList<String> namedRoomsRenderList = new ArrayList<>();

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

                // 2. RENDER THE ROOM OBJECT BODIES (Cohesive Dark Navy Hues)
                } else if (tile.getConnectedRoom() != null) {
                    String roomName = tile.getConnectedRoom().getName();
                    switch (roomName.replaceAll("\\s+", "")) {
                        case "Kitchen":       gc.setFill(Color.web("#060b1e")); break; // Velvet Midnight
                        case "Ballroom":      gc.setFill(Color.web("#0c1533")); break; // Rich Indigo Navy
                        case "Conservatory":  gc.setFill(Color.web("#09112a")); break; // Dark Teal-Navy Tint
                        case "Study":         gc.setFill(Color.web("#050a1c")); break; // Dark Shadow Charcoal Navy
                        case "Hall":          gc.setFill(Color.web("#111a3e")); break; // Clear Oxford Deep Blue
                        case "BilliardRoom":  gc.setFill(Color.web("#0a1330")); break; // Deep Sea Cobalt Navy
                        case "DiningRoom":    gc.setFill(Color.web("#040819")); break; // Jet Navy Base
                        case "Lounge":        gc.setFill(Color.web("#0e173a")); break; // Dark Royal Violet Navy
                        case "Library":       gc.setFill(Color.web("#081027")); break; // Obsidian Navy Tint
                        case "Cellar":        gc.setFill(Color.web("#161a24")); break; // Steel Grey Navy Core
                        default:              gc.setFill(Color.web("#0b1324")); break; 
                    }
                    gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);

                    // Room Walls
                    gc.setStroke(Color.web("#1c2d5a"));
                    gc.setLineWidth(2);
                    if (r > 0 && gameBoard.getTile(r - 1, c).isWalkway()) gc.strokeLine(x, y, x + TILE_SIZE, y);
                    if (r < 23 && gameBoard.getTile(r + 1, c).isWalkway()) gc.strokeLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE);
                    if (c > 0 && gameBoard.getTile(r, c - 1).isWalkway()) gc.strokeLine(x, y, x, y + TILE_SIZE);
                    if (c < 23 && gameBoard.getTile(r, c + 1).isWalkway()) gc.strokeLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE);
                }

                // Grid lines layout overlay
                gc.setStroke(Color.rgb(255, 255, 255, 0.04));
                gc.setLineWidth(1);
                gc.strokeRect(x, y, TILE_SIZE, TILE_SIZE);
            }
        }

        // --- PASS 2: LABELS TEXT RENDER LAYER ---
        gc.setFont(Font.font("System", FontWeight.BOLD, 10));
        for (int r = 0; r < 24; r++) {
            for (int c = 0; c < 24; c++) {
                Tile tile = gameBoard.getTile(r, c);
                if (tile.getConnectedRoom() != null) {
                    String rawRoomName = tile.getConnectedRoom().getName();
                    if (!namedRoomsRenderList.contains(rawRoomName) && !rawRoomName.equalsIgnoreCase("Cellar")) {
                        namedRoomsRenderList.add(rawRoomName);
                        
                        // Render room names shifted nicely inside bounds of their top-left initialization tiles
                        int textX = (c * TILE_SIZE) + 10;
                        int textY = (r * TILE_SIZE) + 20;
                        
                        // Drop Shadow Text Effect
                        gc.setFill(Color.BLACK);
                        gc.fillText(rawRoomName.toUpperCase(), textX + 1, textY + 1);
                        gc.setFill(Color.web("#deb86b")); // Mansion Gold
                        gc.fillText(rawRoomName.toUpperCase(), textX, textY);
                    }
                }
            }
        }

        // --- PASS 3: RENDER INTERACTIVE HOVER EFFECTS LAYER ---
        if (hoveredTile != null) {
            Tile validTarget = getCorrespondingValidMove(hoveredTile);

            if (validTarget != null) {
                if (hoveredTile.isWalkway() || hoveredTile.isDoorway()) {
                    int x = hoveredTile.getCol() * TILE_SIZE;
                    int y = hoveredTile.getRow() * TILE_SIZE;
                    
                    gc.setFill(Color.rgb(222, 184, 107, 0.45));
                    gc.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                    gc.setStroke(Color.web("#deb86b"));
                    gc.setLineWidth(1.5);
                    gc.strokeRect(x + 0.5, y + 0.5, TILE_SIZE - 1, TILE_SIZE - 1);
                } 
                else if (hoveredTile.getConnectedRoom() != null) {
                    gc.setStroke(Color.web("#deb86b")); 
                    gc.setLineWidth(3.5);              

                    for (int r = 0; r < 24; r++) {
                        for (int c = 0; c < 24; c++) {
                            Tile tile = gameBoard.getTile(r, c);
                            if (tile.getConnectedRoom() == validTarget.getConnectedRoom()) {
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
        }

        // --- PASS 4: RENDER DYNAMIC CHARACTER TOKENS LAYER ---
        // Reads coordinates directly from game state player collection data
        if (activePlayers != null) {
            for (Player p : activePlayers) {
                if (gameBoard.isValidLocation(p.getRow(), p.getCol())) {
                    int x = p.getCol() * TILE_SIZE;
                    int y = p.getRow() * TILE_SIZE;
                    
                    // Fills piece tracking color based on character names mapping directly
                    gc.setFill(getCharacterColor(p.getCharacterName())); 
                    gc.fillOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
                    
                    gc.setStroke(Color.web("#0b1324")); 
                    gc.setLineWidth(1.5);
                    gc.strokeOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
                }
            }
        }
    }
}