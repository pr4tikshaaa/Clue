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
        String room = players.get(currentPlayerIndex).getCurrentRoom();
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

    public void movePlayer(int steps) {
        Player player = getCurrentTurn();
        player.setPosition(player.getPosition() + steps);
    }

    public void takeTurn() {
        Player current = getCurrentTurn();
        System.out.println("\nPlayer " + (currentPlayerIndex + 1) + " (" + current.getCharacterName() + ")'s turn:");
        int steps = rollDice();
        movePlayer(steps);
        //current.setRoom();
        System.out.println("Moved to position " + current.getPosition());
        System.out.println("\nPlayer " + (currentPlayerIndex + 1) + " is in the " + players.get(currentPlayerIndex).getCurrentRoom());
    }

}
