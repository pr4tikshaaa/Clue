public class Card {
    private String type;
    private String name;

    public Card(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    } 

    public String getType() {
        return type;
    }

    public String toString() {
        return name + " (" + type + ")";
    }

    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card other = (Card) obj;
            return this.name.equals(other.name) && this.type.equals(other.type);
        } else {
            return false;
        }
    }

    public int hashcode() {
        return type.hashCode() + name.hashCode();
    }
}
