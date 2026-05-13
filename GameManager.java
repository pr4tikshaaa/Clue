import java.util.ArrayList;

public class GameManager {
    private ArrayList<Player> players;
    private CardDeck cardDeck;
    private int currentPlayerIndex;
    private int steps;
    private Board board;
    private ArrayList<Location> moves;

    public GameManager(ArrayList<Player> players) {
        this.players = players;
        cardDeck = new CardDeck();
        this.currentPlayerIndex = 0;
        board = new Board();
        cardDeck.dealCards(players);

        players.get(0).setPosition(2, 0);
        players.get(1).setPosition(2, 1);
        players.get(2).setPosition(2, 2);
        players.get(3).setPosition(2, 3);
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

    public void printGameState() {
        System.out.println("Current player: " +  players.get(currentPlayerIndex).getCharacterName() + " (" + players.get(currentPlayerIndex).getPlayerName() + ")");
    }

    public String makeAccusation(Card suspectGuess, Card weaponGuess, Card roomGuess) {
        String result = "Suspect accusation: " + suspectGuess.getName() + "\nWeapon accusation: " + weaponGuess.getName() + "\nRoom accusation: " + roomGuess.getName();
        CaseFile caseFile = cardDeck.getCaseFile();
        result += "\nAccusation is ";
        if (caseFile.isCorrect(suspectGuess, weaponGuess, roomGuess)) {
            result += "correct";
        } else {
            result += "incorrect";
        }
        return result;
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
    
    public int rollDice() {
        Dice dice1 = new Dice();
        Dice dice2 = new Dice();
        int dice1Dots = dice1.getNumDots();
        int dice2Dots = dice2.getNumDots();
        int roll = dice1Dots + dice2Dots;
        System.out.println("Dice 1: " + dice1Dots + "\nDice 2: " + dice2Dots + "\nRoll: " + roll);
        return roll;
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
        steps = rollDice();

        // temp movement test
        board.setPlayer(current, current.getCol() + 1, current.getRow());
        // movePlayer(steps);
        // current.setRoom();
    }

    public void playerMoves() {
        Player current = getCurrentTurn();
        System.out.println("\nPlayer " + (currentPlayerIndex + 1) + "'s turn");
        
        // TEMP TEST POSITION
        current.setPosition(2, 2);

        // TEMP TEST ROLL
        int roll = 3;

        System.out.println("Current Position: (" +current.getRow() + ", " + current.getCol() + ")");
        
        moves = new ArrayList<>();
        moves = board.getValidMoves(current, roll);
        System.out.println("\nValid Moves:");
        
        for (Location move : moves) {
            if (move instanceof Tile) {
                Tile tile = (Tile) move;
                
                System.out.println("Hallway: (" + tile.getRow() + ", " + tile.getCol() + ")");
            } 
            else if (move instanceof Room) {
                Room room = (Room) move;
                System.out.println("Room: " + room.getName());
            }
        }
    }

}
