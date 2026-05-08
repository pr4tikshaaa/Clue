import java.util.ArrayList;

public class ClueLauncher {
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

/*         System.out.println("\nRolling die for Player 1:");
        game.rollDice(); */

        System.out.println("\nChecking Player 1's accusation:");
        Card suspectCard = new Card("suspect", "Miss Scarlet");
        Card weaponCard = new Card("weapon", "Candlestick");
        Card roomCard = new Card("room", "Conservatory");
        System.out.println(game.makeAccusation(suspectCard, weaponCard, roomCard));

        game.takeTurn();
        game.takeTurn();
        game.takeTurn();
        game.takeTurn();
        game.takeTurn();
        game.takeTurn();
    }
}
