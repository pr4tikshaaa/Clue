import java.util.ArrayList;

/**
 * Manages overall clue game logic.
 * Controls player turns, player movement, rolling dice, making suggestions, making accusations, and certain card management.
 */
public class GameManager {
    /** ArrayList represents all Players involved in game. */
    private ArrayList<Player> players;
    /** Card deck to be used in game. */
    private CardDeck cardDeck;
    /** Keeps track of whose turn it is throughout the game (index by 0). */
    private int currentPlayerIndex;
    /** Board to be used in game. */
    private Board board;
    /** List of possible moves given a starting location and the number of steps. */
    private ArrayList<Tile> moves;

    /**
     * Constucts a GameManager object and initializes the game state.
     * 
     * Cards are dealt to players, and players are placed at their starting positions on the board.
     * 
     * @param players (players involved in game)
     */
    public GameManager(ArrayList<Player> players) {
        this.players = players;
        cardDeck = new CardDeck();
        this.currentPlayerIndex = 0;
        board = new Board();
        cardDeck.dealCards(players);

        if (players.size() > 0) board.setPlayer(players.get(0), 23, 9);
        if (players.size() > 1) board.setPlayer(players.get(1), 23, 14);
        if (players.size() > 2) board.setPlayer(players.get(2), 17, 0);
        if (players.size() > 3) board.setPlayer(players.get(3), 0, 16);
        if (players.size() > 4) board.setPlayer(players.get(4), 7, 23);
        if (players.size() > 5) board.setPlayer(players.get(5), 18, 23);

        board.printBoard();
    }

    /**
     * Gets the player whose turn it currently is.
     * 
     * @return (return current player)
     */
    public Player getCurrentTurn() {
        currentPlayerIndex = currentPlayerIndex % players.size();
        return players.get(currentPlayerIndex);
    }

    /**
     * Sets following turn to the next player.
     * 
     * @return (return next player)
     */
    public Player setNextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(currentPlayerIndex);
    }

    /**
     * Get index of current player.
     * 
     * @return (int of current player index)
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Gets index of next player's turn.
     * 
     * @return (the next player index)
     */
    public int getNextTurn() {
        return (currentPlayerIndex + 1) % players.size();
    }

    /**
     * Gets the case file of the game.
     * 
     * @return (return CaseFile object)
     */
    public CaseFile getCaseFile() {
        return cardDeck.getCaseFile();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void printGameState() {
        System.out.println("Current player: " +  players.get(currentPlayerIndex).getCharacterName() + " (" + players.get(currentPlayerIndex).getPlayerName() + ")");
    }

    public boolean makeAccusation(String suspectGuess, String weaponGuess, String roomGuess) {
        CaseFile caseFile = cardDeck.getCaseFile();
        if (caseFile.isCorrect(cardDeck.getCard(suspectGuess), cardDeck.getCard(weaponGuess), cardDeck.getCard(roomGuess))) {
            return true;
        } else {
            return false;
        }
    }

    public String makeSuggestion(Card suspectGuess, Card weaponGuess) {
        String room = board.getRoom(players.get(currentPlayerIndex));
        if (room.equals("Walkway") || room.equals("Doorway") || room.equals(null)) {
            return "Not in room. Suggestion cannot be made.";
        }
        Card roomCard = new Card("room", room);
        String result = "\nSuspect suggestion: " + suspectGuess.getName() + "\nWeapon suggestion: " + weaponGuess.getName() + "\nRoom suggestion: " + room;

        for (int i = currentPlayerIndex + 1; i < players.size(); i++) {
            for (Card c : players.get(i).getHand()) {
                if (c.equals(suspectGuess) || c.equals(weaponGuess) || c.equals(roomCard)) {
                    return result + "\nCard is in play. Pass to Player " + (i+1);
                }
            }
        }

        for (int i = 0; i < currentPlayerIndex; i++) {
            for (Card c : players.get(i).getHand()) {
                if (c.equals(suspectGuess) || c.equals(weaponGuess) || c.equals(room)) {
                    return result + "\nCard is in play. Pass to Player " + (i+1);
                }
            }
        }

        return result + "\nCard is not found.";
    }
    
    public void rollDice() {
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
        int dice1Dots = dice1.getNumDots();
        int dice2Dots = dice2.getNumDots();
        int roll = dice1Dots + dice2Dots;
        System.out.println("Dice 1: " + dice1Dots + "\nDice 2: " + dice2Dots + "\nRoll: " + roll);
        
        Player player = players.get(currentPlayerIndex);
        player.setRoll(roll);
    }

    public void setPlayerPos(int r, int c) {
        Player player = getCurrentTurn();
        board.setPlayer(player, r, c);
    }

    public void takeTurn() {
        Player current = getCurrentTurn();
        System.out.println("\nPlayer " + (currentPlayerIndex + 1) + " (" + current.getCharacterName() + ")'s turn:");
        rollDice();
    }

    public void playerMoves() {
        Player current = getCurrentTurn();

        Tile t = board.getTile(current.getRow(), current.getCol());

        if (t.isRoom()) {
            System.out.println("Player " + (currentPlayerIndex + 1) + " is in the " + t.getConnectedRoom().getName());
        } else {
            System.out.print("Current Position: (" +current.getRow() + ", " + current.getCol() + ")");
        }
        
        moves = new ArrayList<>();
        moves = board.getValidMoves(current, current.getRoll());
        System.out.println("\nValid Moves:");
        
        for (Tile tile : moves) {
            System.out.println(tile.getRow() + ", " + tile.getCol());
        }

        Tile chosen = moves.get(0); // TEMP: auto-pick first move // used for debugging, only affects text-based game
        board.setPlayer(current, chosen.getRow(), chosen.getCol());
    }

    public Board getBoard() {
        return board;
    }

    public Player findDisprovingPlayer(String suspect, String weapon, String room) {
        for (int i = currentPlayerIndex + 1; i < players.size(); i++) {
            int nextIndex = i;
            ArrayList<String> matchingCards = getDisprovingCards(players.get(nextIndex), suspect, weapon, room);
            if (!matchingCards.isEmpty()) {
                return players.get(nextIndex);
            }
        }

        for (int i = 0; i < players.size(); i++) {
            int nextIndex = i;
            ArrayList<String> matchingCards = getDisprovingCards(players.get(nextIndex), suspect, weapon, room);
            if (!matchingCards.isEmpty()) {
                return players.get(nextIndex);
            }
        }

        return null;
    }

    public ArrayList<String> getDisprovingCards(Player player, String suspect, String weapon, String room) {
        ArrayList<String> matches = new ArrayList<>();
        if (player == null) {
            return matches;
        }

        for (Card card : player.getHand()) {
            String cardName = card.getName();

            if (cardName.equals(suspect) || cardName.equals(weapon) || cardName.equals(room)) {
                matches.add(cardName);
            }
        }

        return matches;
    }

}
