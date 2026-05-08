import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class CardDeck {
    private Stack<Card> cardDeck;
    private ArrayList<Card> orderedDeck;
    private CaseFile caseFile;
/*  private ArrayList<Card> suspectCards;
    private ArrayList<Card> weaponCards;
    private ArrayList<Card> roomCards; */

    public CardDeck() {
        cardDeck = new Stack<>();
        orderedDeck = new ArrayList<>();

        String suspect = "suspect";
        String weapon = "weapon";
        String room = "room";

        orderedDeck.add(new Card(suspect, "Miss Scarlett"));
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

    public void initializeDeck() {
        for (Card c : orderedDeck) {
            cardDeck.push(c);
        }
    }

    public Card getCard(String name) {
        for (int i = 0; i < orderedDeck.size(); i++) {
            if (name.equals(orderedDeck.get(i).getName())) {
                return orderedDeck.get(i);
            }
        }
        return null;
    }

    public void shuffle() {
        Collections.shuffle(cardDeck);
    }

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

    public void dealCards(ArrayList<Player> players) {
        int i = 0;

        while (!cardDeck.isEmpty()) {
            players.get(i % players.size()).addCard(cardDeck.pop());
            i++;
        }
    }

    public CaseFile getCaseFile() {
        return caseFile;
    }
}
