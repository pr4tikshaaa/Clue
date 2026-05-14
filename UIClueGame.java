import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.util.*;


/**
 * The UI for home screen, player setup etc.
 */

public class UIClueGame 
{
    private static final String HOME_NAME = "Home";
    private static final String NUM_PLAYERS = "Num of players";
    private static final String CHOOSE_PLAYERS = "Player Setup";
    private static final String THE_BOARD = "Board";
    private ArrayList<Player> players = new ArrayList<>();
    private final String[] suspects = {" ", "Col. Mustard", "Miss Scarlet", "Prof. Plum", "Mr. Green", "Mrs. Peacock", "Mrs. White"};
    private int numPlayers;
    //private JLabel numPlayersLabel;
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
        nextBtn.addActionListener(e -> {
          this.numPlayers = Integer.parseInt((String)players.getSelectedItem());
          theContainer.add(chooseCharacters(this.numPlayers), CHOOSE_PLAYERS);
          theCardLayout.show(theContainer, CHOOSE_PLAYERS);
          });
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
     private JPanel chooseCharacters(int totalPlayers)
     {
        players.clear();
        JPanel choosingCharacters = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 1; i <= totalPlayers; i++)
        {
          int temp = i;
          gbc.gridy = temp;
          gbc.gridx = 0;
          players.add(new Player("Player " + temp, " "));
          //System.out.println(players.get(i-1).getPlayerName());// testing

          JPanel theRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
          JLabel thePlayer = new JLabel(players.get(temp-1).getPlayerName());
          JComboBox<String> theSuspects = new JComboBox<>(suspects);
          theSuspects.setSelectedItem(players.get(temp-1).getCharacterName());
          theSuspects.addActionListener(e -> {
            players.get(temp-1).setCharacterName((String)theSuspects.getSelectedItem());
          });

          theRow.add(thePlayer);
          theRow.add(theSuspects);
          choosingCharacters.add(theRow, gbc);
          //System.out.println("Player " + players.get(temp-1).getPlayerName() + " is playing: " + players.get(temp-1).getCharacterName());
        }
        gbc.gridy++;
        JButton nextBtn = new JButton("Start the game →");
        choosingCharacters.add(nextBtn, gbc);
        JLabel error = new JLabel("Choose different characters!");
        error.setVisible(false);
        gbc.gridy++;
        choosingCharacters.add(error, gbc);

        nextBtn.addActionListener(e -> {
          if (checkCharacters(players) == true)
          {
            error.setVisible(false);
            for (Player p : players) {
                System.out.println("Player " + p.getPlayerName() + " is playing: " + p.getCharacterName());
            }
            theContainer.add(boardScreen(), THE_BOARD);
            theCardLayout.show(theContainer, THE_BOARD);
          }
          else
          {
            error.setVisible(true);
          }
            
        });
      
        return choosingCharacters;

     }
     /**
      * TODO: Finish this method!
      * 
      */
     private JPanel boardScreen()
     {
        JPanel theBoard = new JPanel(new GridBagLayout());
        return theBoard;
     }

     private boolean checkCharacters(ArrayList<Player> players)
     {
        ArrayList<String> names = new ArrayList<String>();
        String character = "";
        for (int i = 0; i < players.size(); i++)
        {
            character = players.get(i).getCharacterName();
            if (names.contains(character) || character.equals(" "))
            {
              return false;
            }
            names.add(character);
        }
        return true;
     }

   public static void main (String[] args)
   {
        SwingUtilities.invokeLater(() ->
        {
            new UIClueGame();
        });
 
   }
}
