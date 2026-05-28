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

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCharacterName().equals("Dr. Orchid")) {
                board.setPlayer(players.get(i), 23, 14);
            } else if (players.get(i).getCharacterName().equals("Colonel Mustard")) {
                board.setPlayer(players.get(i), 7, 23);
            } else if (players.get(i).getCharacterName().equals("Miss Scarlet")) {
                board.setPlayer(players.get(i), 0, 16);
            } else if (players.get(i).getCharacterName().equals("Professor Plum")) {
                board.setPlayer(players.get(i), 4, 0);
            } else if (players.get(i).getCharacterName().equals("Mr. Green")) {
                board.setPlayer(players.get(i), 23, 9);
            } else if (players.get(i).getCharacterName().equals("Mrs. Peacock")) {
                board.setPlayer(players.get(i),17, 0);
            }
        }
        
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

    /**
     * Gets list of players in game.
     * 
     * @return (returns ArrayList of players)
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Prints current game state.
     */
    public void printGameState() {
        System.out.println("Current player: " +  players.get(currentPlayerIndex).getCharacterName() + " (" + players.get(currentPlayerIndex).getPlayerName() + ")");
    }

    /**
     * Checks if accusation is correct.
     * 
     * @param suspectGuess (the suspect guessed)
     * @param weaponGuess (the weapon guessed)
     * @param roomGuess (the room guessed)
     * @return (true if accusation is correct, false is incorrect)
     */
    public boolean makeAccusation(String suspectGuess, String weaponGuess, String roomGuess) {
        CaseFile caseFile = cardDeck.getCaseFile();
        if (caseFile.isCorrect(cardDeck.getCard(suspectGuess), cardDeck.getCard(weaponGuess), cardDeck.getCard(roomGuess))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Processes a player's suggestion.
     * Suggestion includes a suspect, weapon, and room.
     * Other players are checked (in order of play) to see if they have at least one of the cards being suggested to be able to disprove the suggestion.
     * 
     * @param suspectGuess (suspect guessed)
     * @param weaponGuess (weapon guessed)
     * @return (return strings that state if the cards in the suggestion are found or not (used for debugging in text-based game))
     */
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
                if (c.equals(suspectGuess) || c.equals(weaponGuess) || c.equals(roomCard)) {
                    return result + "\nCard is in play. Pass to Player " + (i+1);
                }
            }
        }

        return result + "\nCard is not found.";
    }
    
    /**
     * Allows players to roll two dice and stores total roll.
     */
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

    /**
     * Sets the current player's position on the board.
     * 
     * @param r (destination row)
     * @param c (destination column)
     */
    public void setPlayerPos(int r, int c) {
        Player player = getCurrentTurn();
        board.setPlayer(player, r, c);
    }

    /**
     * Takes player's turn by rolling the dice and printing player info and output to console.
     */
    public void takeTurn() {
        Player current = getCurrentTurn();
        System.out.println("\nPlayer " + (currentPlayerIndex + 1) + " (" + current.getCharacterName() + ")'s turn:");
        rollDice();
    }

    /**
     * This method was used for debugging.
     * Displays a player's current position, and based on that, prints all possible destination coordinates.
     * Then, auto-picks the first the first valid succeeding move from that list and sets player there.
     */
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

        Tile chosen = moves.get(0); // auto-pick first move // used for debugging, only affects text-based game
        board.setPlayer(current, chosen.getRow(), chosen.getCol());
    }

    /**
     * Returns board object at whatever state.
     * 
     * @return game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Finds first player who can disprove a suggestion.
     * 
     * @param playerIndex of disproving player
     * @return Player object of disproving player
     */
    public Player findDisprovingPlayer(String suspect, String weapon, String room) {
        for (int i = currentPlayerIndex + 1; i < players.size(); i++) { 
            ArrayList<String> matchingCards = getDisprovingCards(players.get(i), suspect, weapon, room); 
            if (!matchingCards.isEmpty()) {
                return players.get(i); 
            } 
        }
        
        for (int i = 0; i < players.size(); i++) {
            ArrayList<String> matchingCards = getDisprovingCards(players.get(i), suspect, weapon, room); 
            if (!matchingCards.isEmpty()) { 
                return players.get(i); 
            } 
        } 
        
        return null;
    }

    /**
     * Gets all cards the disproving player is able to disprove suggestion with.
     * 
     * @param player is the disproving player
     * @param suspect is the suggested suspect
     * @param weapon is the suggested weapon
     * @param room is the suggested room
     * @return all possible disproving card names
     */
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
