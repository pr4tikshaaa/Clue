import java.util.ArrayList;

public class GameManager {
    private ArrayList<Player> players;
    private CardDeck cardDeck;
    private int currentPlayerIndex;
    private int steps;
    private Board board;
    private ArrayList<Tile> moves;

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

    public Player getCurrentTurn() {
        currentPlayerIndex = currentPlayerIndex % players.size();
        //System.out.println("Player " + (currentPlayerIndex + 1) + "'s turn");
        return players.get(currentPlayerIndex);
    }

    public Player setNextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(currentPlayerIndex);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getNextTurn() {
        return (currentPlayerIndex + 1) % players.size();
    }

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

    // public void movePlayer(int steps) {
    //     Player player = getCurrentTurn();
    //     player.setPosition(player.getPosition() + steps);
    // }

    public void setPlayerPos(int r, int c) {
        Player player = getCurrentTurn();
        board.setPlayer(player, r, c);
    }

    public void takeTurn() {
        Player current = getCurrentTurn();
        System.out.println("\nPlayer " + (currentPlayerIndex + 1) + " (" + current.getCharacterName() + ")'s turn:");
        rollDice();

        // temp movement test
        // board.setPlayer(current, current.getRow(), current.getRow());
        // movePlayer(steps);
        // current.setRoom();
    }

    public void playerMoves() {
        Player current = getCurrentTurn();
        //System.out.println("\nPlayer " + (currentPlayerIndex + 1) + "'s turn");
        
        // TEMP TEST POSITION
        // board.setPlayer(current, current.getRow(), current.getCol());
        //takeTurn();

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

        Tile chosen = moves.get(0); // TEMP: auto-pick first move
        board.setPlayer(current, chosen.getRow(), chosen.getCol());
    }

    public Board getBoard() {
        return board;
    }

    public int getSteps() {
        return steps;
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
