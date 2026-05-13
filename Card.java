public class Card {
    // private String type;
    private String name;
    private String type;

    public Card(String type, String name) {
        // this.type = type;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    } 

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card other = (Card) obj;
            return this.name.equals(other.name) && this.type.equals(other.type);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return type.hashCode() + name.hashCode();
    }
}
