public class CaseFile {
    private Card suspect;
    private Card weapon;
    private Card room;

    public CaseFile(Card suspect, Card weapon, Card room) {
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    public boolean isCorrect(Card suspectGuess, Card weaponGuess, Card roomGuess) {
        if (suspectGuess == null || weaponGuess == null || roomGuess == null) {
            return false;
        }

        return suspect.equals(suspectGuess) && weapon.equals(weaponGuess) && room.equals(roomGuess);
    }

    public String toString() {
        return suspect.toString() + ", " + weapon.toString() + ", " + room.toString();
    }

}
