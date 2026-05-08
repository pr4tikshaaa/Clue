public class Dice {
    private int numDots;

    public Dice() {
        roll();
    }

    public int getNumDots() {
        return numDots;
    }

    public void roll() {
        numDots = (int)(Math.random() * 6) + 1;
    }
}