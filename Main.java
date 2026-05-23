import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Swing GUI setup must happen on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // 1. Initialize your existing Board logic
            Board myBoard = new Board();

            // 2. Setup the outer window frame
            JFrame frame = new JFrame("Cute Pixel Clue");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            // 3. Add your custom visual board panel to the window
            BoardPanel visualBoard = new BoardPanel(myBoard);
            frame.add(visualBoard);

            // 4. Compact the frame tightly around your 24x24 panel and show it
            frame.pack();
            frame.setLocationRelativeTo(null); // Centers window on screen
            frame.setVisible(true);
        });
    }
}