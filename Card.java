/**
 * Represents a Clue card.
 * Each card as a name and a type (suspect, weapon, room).
 */
public class Card {
    /** Name of the card. */
    private String name;
    /** Type of card. */
    private String type;

    /**
     * Creates a Card object with a type and a name.
     * 
     * @param type (type of card, being either suspect, weapon, or room)
     * @param name (name of the specific card)
     */
    public Card(String type, String name) {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets name of card.
     * 
     * @return (returns name of card)
     */
    public String getName() {
        return name;
    } 

    /**
     * Gets type of card, in the form of a String.
     * 
     * @return (String representing the type of card)
     */
    public String getType() {
        return type;
    }

    /**
     * Sets name of card.
     * 
     * @param name (name of card)
     */
    public void setName(String name) {
        this.name = name;
    } 

    /**
     * Sets type of card.
     * 
     * @param type (type of card)
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns a string representing a card (used for debugging).
     * 
     * @return (returns String with the name and type)
     */
    @Override
    public String toString() {
        return name + " (" + type + ")";
    }

    /**
     * Checks if card is equal to given object.
     * Two cards are equal if they have the same name and type.
     * 
     * @return (returns true if equal, false if not)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card other = (Card) obj;
            return this.name.equals(other.name) && this.type.equals(other.type);
        } else {
            return false;
        }
    }

    /**
     * Generates a hash code value for the card.
     * 
     * @return (returns hash code of card)
     */
    @Override
    public int hashCode() {
        return type.hashCode() + name.hashCode();
    }
}
