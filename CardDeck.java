import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class CardDeck {
    private Stack<Card> cardDeck;
    private CaseFile caseFile;

    public CardDeck() {
        cardDeck = new Stack<>();

        initializeDeck();
        shuffle();
        createCaseFile();
    }

    public void initializeDeck() {
        String suspect = "suspect";
        String weapon = "weapon";
        String room = "room";

        cardDeck.push(new Card(suspect, "Miss Scarlett"));
        cardDeck.push(new Card(suspect, "Colonel Mustard"));
        cardDeck.push(new Card(suspect, "Dr. Orchid"));
        cardDeck.push(new Card(suspect, "Mr. Green"));
        cardDeck.push(new Card(suspect, "Mrs. Peacock"));
        cardDeck.push(new Card(suspect, "Professor Plum"));

        cardDeck.push(new Card(weapon, "Candlestick"));
        cardDeck.push(new Card(weapon, "Dagger"));
        cardDeck.push(new Card(weapon, "Lead pipe"));
        cardDeck.push(new Card(weapon, "Revolver"));
        cardDeck.push(new Card(weapon, "Rope"));
        cardDeck.push(new Card(weapon, "Wrench"));

        cardDeck.push(new Card(room, "Ballroom"));
        cardDeck.push(new Card(room, "Billiard Room"));
        cardDeck.push(new Card(room, "Conservatory"));
        cardDeck.push(new Card(room, "Dining Room"));
        cardDeck.push(new Card(room, "Hall"));
        cardDeck.push(new Card(room, "Kitchen"));
        cardDeck.push(new Card(room, "Library"));
        cardDeck.push(new Card(room, "Lounge"));
        cardDeck.push(new Card(room, "Study"));
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
