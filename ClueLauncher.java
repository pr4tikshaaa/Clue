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

        System.out.println("\nRolling dice for Player 1:");
        players.get(0).rollDice();

    }
}
