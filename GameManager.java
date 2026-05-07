import java.util.ArrayList;

public class GameManager {
    private ArrayList<Player> players;
    private CardDeck cardDeck;
    private int currentPlayerIndex;

    public GameManager(ArrayList<Player> players) {
        this.players = players;
        cardDeck = new CardDeck();
        this.currentPlayerIndex = 0;

        cardDeck.dealCards(players);
    }

    public Player getCurrentTurn() {
        return players.get(currentPlayerIndex % players.size());
    }

    public Player getNextTurn() {
        return players.get((currentPlayerIndex + 1) % players.size());
    }

    public CaseFile getCaseFile() {
        return cardDeck.getCaseFile();
    }

    public void printGameState() {
        System.out.println("Current player: " +  players.get(currentPlayerIndex).getCharacterName() + " (" + players.get(currentPlayerIndex).getPlayerName() + ")");
    }


}
