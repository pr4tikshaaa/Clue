import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Represents deck of cards made up of Card objects.
 * Deck contains suspect, weapon, and room cards.
 * Creates case file, shuffles cards, and deals cards to players.
 * 
 */
public class CardDeck {
    /** Stack contains all the cards in the deck. */
    private Stack<Card> cardDeck;
    /** Ordered ArrayList of all cards before shuffling.  */
    private ArrayList<Card> orderedDeck;
    /** Holds casefile containing the three solution cards. */
    private CaseFile caseFile;

    /**
     * Constructs a CardDeck and initializes all Clue cards.
     * 
     * The deck is initialized, shuffled, and then a case file is created.
     */
    public CardDeck() {
        cardDeck = new Stack<>();
        orderedDeck = new ArrayList<>();

        String suspect = "suspect";
        String weapon = "weapon";
        String room = "room";

        orderedDeck.add(new Card(suspect, "Miss Scarlet"));
        orderedDeck.add(new Card(suspect, "Colonel Mustard"));
        orderedDeck.add(new Card(suspect, "Dr. Orchid"));
        orderedDeck.add(new Card(suspect, "Mr. Green"));
        orderedDeck.add(new Card(suspect, "Mrs. Peacock"));
        orderedDeck.add(new Card(suspect, "Professor Plum"));

        orderedDeck.add(new Card(weapon, "Candlestick"));
        orderedDeck.add(new Card(weapon, "Dagger"));
        orderedDeck.add(new Card(weapon, "Lead pipe"));
        orderedDeck.add(new Card(weapon, "Revolver"));
        orderedDeck.add(new Card(weapon, "Rope"));
        orderedDeck.add(new Card(weapon, "Wrench"));

        orderedDeck.add(new Card(room, "Ballroom"));
        orderedDeck.add(new Card(room, "Billiard Room"));
        orderedDeck.add(new Card(room, "Conservatory"));
        orderedDeck.add(new Card(room, "Dining Room"));
        orderedDeck.add(new Card(room, "Hall"));
        orderedDeck.add(new Card(room, "Kitchen"));
        orderedDeck.add(new Card(room, "Library"));
        orderedDeck.add(new Card(room, "Lounge"));
        orderedDeck.add(new Card(room, "Study"));

        initializeDeck();
        shuffle();
        createCaseFile();
    }

    /**
     * Adds all cards from ordered deck to the card deck that will be used for the game.
     */
    public void initializeDeck() {
        for (Card c : orderedDeck) {
            cardDeck.push(c);
        }
    }

    /**
     * Gets card given the name of it.
     * 
     * @param name (name of the card)
     * @return (return the Card object, or null if not found)
     */
    public Card getCard(String name) {
        for (int i = 0; i < orderedDeck.size(); i++) {
            if (name.equals(orderedDeck.get(i).getName())) {
                return orderedDeck.get(i);
            }
        }
        return null;
    }

    /**
     * Randomly shuffles the card deck.
     */
    public void shuffle() {
        Collections.shuffle(cardDeck);
    }

    /**
     * Creates the hidden case file for the game. Removes one suspect, one weapon, and one room from the deck
     * and adds it to the case file. The selection process makes sure that the cards chosen are random.
     */
    public void createCaseFile() {
        Card suspect = null;
        Card weapon = null;
        Card room = null;

        Stack<Card> tempStack = new Stack<>();

        while (!cardDeck.isEmpty()) {
            Card tempCard = cardDeck.peek();
            if (tempCard.getType().equals("suspect")) {
                suspect = cardDeck.pop();
                break;
            } else {
                tempStack.push(cardDeck.pop());
            }
        }

        while (!tempStack.isEmpty()) {
            cardDeck.push(tempStack.pop());
        }

        while (!cardDeck.isEmpty()) {
            Card tempCard = cardDeck.peek();
            if (tempCard.getType().equals("weapon")) {
                weapon = cardDeck.pop();
                break;
            } else {
                tempStack.push(cardDeck.pop());
            }
        }

        while (!tempStack.isEmpty()) {
            cardDeck.push(tempStack.pop());
        }

        while (!cardDeck.isEmpty()) {
            Card tempCard = cardDeck.peek();
            if (tempCard.getType().equals("room")) {
                room = cardDeck.pop();
                break;
            } else {
                tempStack.push(cardDeck.pop());
            }
        }

        while (!tempStack.isEmpty()) {
            cardDeck.push(tempStack.pop());
        }

        caseFile = new CaseFile(suspect, weapon, room);
    }

    /**
     * Deals cards to players and adds it to their respective player card hands.
     * 
     * @param players (players involved in game)
     */
    public void dealCards(ArrayList<Player> players) {
        int i = 0;

        while (!cardDeck.isEmpty()) {
            players.get(i % players.size()).addCard(cardDeck.pop());
            i++;
        }
    }

    /**
     * Returns case file for the game
     * 
     * @return (returns caseFile object)
     */
    public CaseFile getCaseFile() {
        return caseFile;
    }
}
