package byog.lab6;


public class TestMemoryGame {

    public static void main(String[] args) {
        MemoryGame game = new MemoryGame(40, 40, 11);
        String x = game.generateRandomString(3);
        game.solicitNCharsInput(3);
    }
}
