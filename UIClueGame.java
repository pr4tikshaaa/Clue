import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * The UI for home screen, player setup etc.
 */

public class UIClueGame 
{
    private static final String HOME_NAME = "Home";
    private static final String NUM_PLAYERS = "Num of players";
    private static final String CHOOSE_PLAYERS = "Player Setup";
    //private static final String MAINBOARD_NAME = "Main Baoard";
    private JFrame myFrame;
    private JPanel theContainer; //to hold the different pages
    private CardLayout theCardLayout;

     public UIClueGame()
     {
        myFrame = new JFrame("Clue");
        myFrame.setSize(800, 600);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        theCardLayout = new CardLayout();
        theContainer = new JPanel(theCardLayout);
        theContainer.add(homeScreen(), HOME_NAME);
        theContainer.add(playerSetupScreen(), NUM_PLAYERS);
        theContainer.add(chooseCharacters(), CHOOSE_PLAYERS);

        myFrame.add(theContainer);
        myFrame.setVisible(true);
     }

     private JPanel homeScreen()
   {
        JPanel homePanel = new JPanel(new GridBagLayout());
        JLabel title = new JLabel("CLUE");
        title.setFont(new Font("Serif", Font.BOLD, 70));
        JButton startBtn = new JButton("Start Game");
        startBtn.addActionListener(e -> theCardLayout.show(theContainer, NUM_PLAYERS));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        homePanel.add(title, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 0, 0); // Add space between title and button
        homePanel.add(startBtn, gbc);
        return homePanel;
   }
     private JPanel playerSetupScreen()
   {
        JPanel playerPanel = new JPanel(new GridBagLayout());
        JLabel title1 = new JLabel("How many players?");

        String[] numPlayers = { "3", "4", "5", "6"};
        JComboBox<String> players = new JComboBox<>(numPlayers);
        
        JButton nextBtn = new JButton("Choose Characters →");
        nextBtn.addActionListener(e -> theCardLayout.show(theContainer, CHOOSE_PLAYERS));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        playerPanel.add(title1, gbc);
        gbc.gridy = 1;
        playerPanel.add(players, gbc);
        gbc.gridy = 2; 
        playerPanel.add(nextBtn, gbc);

        return playerPanel;
       }
     private JPanel chooseCharacters()
     {
          JPanel charactersPanel = new JPanel(new GridBagLayout());
          return charactersPanel;
     }

   public static void main (String[] args)
   {
        SwingUtilities.invokeLater(() ->
        {
            new UIClueGame();
        });
 
   }
}
