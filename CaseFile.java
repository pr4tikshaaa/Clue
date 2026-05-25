/**
 * Represents the hidden case file for the Clue game.
 * 
 * The case file contains the correct suspect, weapon, and room cards that players have to identify during the game.
 */
public class CaseFile {
    /** The correct suspect card. */
    private Card suspect;
    /** The correct weapon card. */
    private Card weapon;
    /** The correct room card. */
    private Card room;

    /**
     * Constructs the case file with one suspect, one weapon, and one room card.
     * 
     * @param suspect (correct suspect card)
     * @param weapon (correct weapon card)
     * @param room (correct room card)
     */
    public CaseFile(Card suspect, Card weapon, Card room) {
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    /**
     * Checks if a player's guess for what's in the case file is correct.
     * 
     * @param suspectGuess (suspect guess)
     * @param weaponGuess (weapon guess)
     * @param roomGuess (room guess)
     * @return (returns true if it is correct, false if not)
     */
    public boolean isCorrect(Card suspectGuess, Card weaponGuess, Card roomGuess) {
        if (suspectGuess == null || weaponGuess == null || roomGuess == null) {
            return false;
        }

        return suspect.equals(suspectGuess) && weapon.equals(weaponGuess) && room.equals(roomGuess);
    }

    /**
     * String representation of the case file.
     * 
     * @return (returns suspect name, weapon name, and room name)
     */
    public String toString() {
        return suspect.toString() + ", " + weapon.toString() + ", " + room.toString();
    }

}
