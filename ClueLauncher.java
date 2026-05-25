import java.util.ArrayList;

/**
 * Launches and tests the Clue game (text based).
 * Creates players, initializes the game, and runs sample turns.
 */

public class ClueLauncher {
    /**
     * Main method used to start the text-based Clue game.
     * Creates players, initializes the GameManager, displays game information, and runs test game actions.
     * 
     * @param args (arguments)
     */
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", "Miss Scarlet"));
        players.add(new Player("Player 2", "Colonel Mustard"));
        players.add(new Player("Player 3", "Dr. Orchid"));
        players.add(new Player("Player 4", "Mr. Green"));

        GameManager game = new GameManager(players);

        // NEW GAME DETAILS
        System.out.println("\n" + "Welcome to Clue!\n");
        System.out.println("Players:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getPlayerName() + " (" + players.get(i).getCharacterName() + ") ");
        }

        System.out.println("\n");

        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getPlayerName() + " (" + players.get(i).getCharacterName() + "):");
            for (int j = 0; j < players.get(i).getHand().size(); j++) {
                System.out.println(players.get(i).getCard(j));
            }
            System.out.println("\n");
        }

        System.out.println("Case file: " + game.getCaseFile());
        
        // System.out.println("\nRolling die for Player 1:");
        // game.rollDice();

        // Card suspectCard = new Card("suspect", "Miss Scarlet");
        // Card weaponCard = new Card("weapon", "Candlestick");
        //Card roomCard = new Card("room", "Conservatory");

        game.takeTurn();
        game.playerMoves();
        game.takeTurn();
        game.playerMoves();
        
        // System.out.println("\nChecking Player " + (game.getCurrentPlayerIndex() + 1) + "'s suggestion:");
        // System.out.println(game.makeSuggestion(suspectCard, weaponCard));
        // game.setNextTurn();
        // game.takeTurn();
        // game.setNextTurn();
        // game.takeTurn();
        // players.get(game.getCurrentPlayerIndex());

        // System.out.println("\nPlayer " + (game.getCurrentPlayerIndex() + 1) + " made a suggestion:");
        // System.out.println(game.makeSuggestion(new Card("suspect", "Miss Peacock"), new Card("weapon", "Revolver")));
        game.setNextTurn();

        game.getBoard().printBoard();
        
    }
}
