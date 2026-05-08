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
        String result = "Suspect guess: " + suspectGuess.getName() + "\nWeapon guess: " + weaponGuess.getName() + "\nRoom guess: " + roomGuess.getName();
        CaseFile caseFile = cardDeck.getCaseFile();
        result += "\nAccusation is ";
        if (caseFile.isCorrect(suspectGuess, weaponGuess, roomGuess)) {
            result += "correct";
        } else {
            result += "incorrect";
        }
        return result;
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
        current.setRoom();
        System.out.println("Moved to position " + current.getPosition());
        System.out.println("\nPlayer " + (currentPlayerIndex + 1) + " is in the " + players.get(currentPlayerIndex).getCurrentRoom());
        setNextTurn();
    }

}
