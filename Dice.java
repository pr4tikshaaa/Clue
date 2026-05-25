/**
 * Dice object created, allowing player to roll a random number 1-6.
 */
public class Dice {
    /**
     * The number rolled on the die.
     */
    private int numDots;

    /** Constructs dice object and rolls it once. */
    public Dice() {
        roll();
    }

    /**
     * Returns value rolled with the die.
     * 
     * @return (returns number rolled)
     */
    public int getNumDots() {
        return numDots;
    }

    /**
     * Generates random number between 1-6.
     */
    public void roll() {
        numDots = (int)(Math.random() * 6) + 1;
    }
}