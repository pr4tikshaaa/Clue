// import javax.swing.*;
// import java.awt.*;

// public class BoardPanel extends JPanel {
//     private Board gameBoard;
//     private final int TILE_SIZE = 25; // Adjust this to make the window bigger or smaller

//     public BoardPanel(Board gameBoard) {
//         this.gameBoard = gameBoard;
//         // Automatically sizes the panel to fit your 24x24 grid perfectly
//         setPreferredSize(new Dimension(24 * TILE_SIZE, 24 * TILE_SIZE));
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         Graphics2D g2d = (Graphics2D) g;
        
//         // This ensures pixel art or sharp shapes don't get blurry when scaled
//         g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

//         // Loop through your 24x24 grid from your Board class
//         // Loop through your 24x24 grid from your Board class
// // Loop through your 24x24 grid from your Board class
// // Loop through your 24x24 grid from your Board class
// for (int r = 0; r < 24; r++) {
//     for (int c = 0; c < 24; c++) {
//         Tile tile = gameBoard.getTile(r, c);
//         int x = c * TILE_SIZE;
//         int y = r * TILE_SIZE;

//         // 1. PRIORITIZE WALKWAYS AND DOORWAYS
//         if (tile.isWalkway() || tile.isDoorway()) {
//             g2d.setColor(new Color(210, 180, 140)); // Light tan/grey walkway color
//             g2d.fillRect(x, y, TILE_SIZE, TILE_SIZE);

//         // 2. THEN HANDLE THE ROOM BODIES
//         } else if (tile.getConnectedRoom() != null) {
//             String roomName = tile.getConnectedRoom().getName();

//             switch (roomName) {
//                 case "Kitchen": g2d.setColor(new Color(255, 200, 200)); break;       // Pastel Pink
//                 case "Ballroom": g2d.setColor(new Color(200, 220, 255)); break;      // Pastel Blue
                
//                 case "Conservatory": 
//                     g2d.setColor(new Color(198, 235, 197)); 
//                     g2d.fillRect(x, y, TILE_SIZE, TILE_SIZE);
//                     g2d.setColor(new Color(255, 255, 255, 60)); 
//                     g2d.drawRect(x, y, TILE_SIZE, TILE_SIZE);
                    
//                     if (r == 19 && c == 2) {
//                         g2d.setColor(new Color(211, 118, 75)); g2d.fillRect(x + 10, y + 18, 12, 10);
//                         g2d.setColor(new Color(184, 91, 53));  g2d.fillRect(x + 8, y + 16, 16, 3);
//                         g2d.setColor(new Color(78, 159, 61));  g2d.fillOval(x + 6, y + 4, 12, 12);
//                         g2d.fillOval(x + 14, y + 4, 12, 12);   g2d.fillOval(x + 10, y + 1, 12, 12);
//                         g2d.setColor(new Color(175, 226, 161));g2d.fillOval(x + 12, y + 3, 4, 4);
//                     }
//                     break;
                    
//                 case "Study": g2d.setColor(new Color(230, 200, 250)); break;         // Pastel Purple
//                 case "Hall": g2d.setColor(new Color(255, 230, 180)); break;          // Pastel Orange
//                 case "BilliardRoom": g2d.setColor(new Color(180, 240, 240)); break;  // Pastel Cyan
//                 case "DiningRoom": g2d.setColor(new Color(240, 240, 180)); break;    // Pastel Yellow
//                 case "Lounge": g2d.setColor(new Color(255, 180, 220)); break;         // Coral Pink
//                 case "Library": g2d.setColor(new Color(210, 200, 180)); break;        // Soft Gray/Brown
//                 case "Cellar": g2d.setColor(new Color(150, 150, 150)); break;         // Center Cellar
//                 default: g2d.setColor(Color.WHITE); break;
//             }
            
//             // Only fill standard rooms here (Conservatory handles its own fill above)
//             if (!roomName.equals("Conservatory")) {
//                 g2d.fillRect(x, y, TILE_SIZE, TILE_SIZE);
//             }

//             // --- SMART ROOM OUTLINE PASS ---
//             // Define a darker brown color for the room outline border
//             g2d.setColor(new Color(130, 100, 70)); 
//             g2d.setStroke(new BasicStroke(2)); // Makes the border line slightly thicker and cleaner

//             // Check TOP neighbor: Draw line if neighbor is a Walkway (but NOT a Doorway)
//             if (r > 0) {
//                 Tile neighbor = gameBoard.getTile(r - 1, c);
//                 if (neighbor.isWalkway()) g2d.drawLine(x, y, x + TILE_SIZE, y);
//             } else {
//                 g2d.drawLine(x, y, x + TILE_SIZE, y); // Map edge border
//             }

//             // Check BOTTOM neighbor
//             if (r < 23) {
//                 Tile neighbor = gameBoard.getTile(r + 1, c);
//                 if (neighbor.isWalkway()) g2d.drawLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE);
//             } else {
//                 g2d.drawLine(x, y + TILE_SIZE, x + TILE_SIZE, y + TILE_SIZE);
//             }

//             // Check LEFT neighbor
//             if (c > 0) {
//                 Tile neighbor = gameBoard.getTile(r, c - 1);
//                 if (neighbor.isWalkway()) g2d.drawLine(x, y, x, y + TILE_SIZE);
//             } else {
//                 g2d.drawLine(x, y, x, y + TILE_SIZE);
//             }

//             // Check RIGHT neighbor
//             if (c < 23) {
//                 Tile neighbor = gameBoard.getTile(r, c + 1);
//                 if (neighbor.isWalkway()) g2d.drawLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE);
//             } else {
//                 g2d.drawLine(x + TILE_SIZE, y, x + TILE_SIZE, y + TILE_SIZE);
//             }
            
//             // Reset stroke back to default for grid lines
//             g2d.setStroke(new BasicStroke(1));
//         }

//         // 3. Draw thin tile borders across the whole board
//         g2d.setColor(new Color(50, 50, 50, 40)); 
//         g2d.drawRect(x, y, TILE_SIZE, TILE_SIZE);

//         // 4. Draw the player token if the tile is occupied
//         if (tile.isOccupied()) {
//             g2d.setColor(Color.RED); 
//             g2d.fillOval(x + 4, y + 4, TILE_SIZE - 8, TILE_SIZE - 8);
//         }
//     }
// }
//     }
// }