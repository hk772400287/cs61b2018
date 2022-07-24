package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random random;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //Initialize random number generator
        this.random = new Random(seed);
    }

    public String generateRandomString(int n) {
        // Generate random string of letters of length n
        String returnString = "";
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(26);
            returnString += CHARACTERS[x];
        }
        return returnString;
    }

    public void drawFrame(String s) {
        //Take the string and display it in the center of the screen
        // If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.black);
        Font font = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 2, s);
        if (!gameOver) {
            Font font0 = new Font("Arial", Font.BOLD, 20);
            StdDraw.setFont(font0);
            StdDraw.line(0, height * 0.92, width, height * 0.92);
            StdDraw.text(width / 8, height * 0.96, "Round:" + round);
            String task;
            if (playerTurn) {
                task = "Type!";
            } else {
                task = "Watch!";
            }
            StdDraw.text(width / 2, height * 0.96, task);
            int randomNumber = random.nextInt(ENCOURAGEMENT.length);
            StdDraw.text(width * 7 / 8, height * 0.96, ENCOURAGEMENT[randomNumber]);
        }
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        // Display each character in letters, making sure to blank the screen between letters
        char[] charLetters = letters.toCharArray();
        StdDraw.enableDoubleBuffering();
        for (char letter : charLetters) {
            drawFrame(Character.toString(letter));
            StdDraw.pause(1000);
            drawFrame("");
            StdDraw.show();
            StdDraw.pause(500);
        }
        playerTurn = true;
        drawFrame("");
    }

    public String solicitNCharsInput(int n) {
        // Read n letters of player input
        String returnString = "";
        while (returnString.length() < n) {
            while (StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                returnString += Character.toString(nextKey);
                drawFrame(returnString);
            }
        }
        StdDraw.pause(500);
        return returnString;
    }

    public void startGame() {
        //Set any relevant variables before the game starts
        round = 1;
        String randomString;
        // Establish Game loop
        while (true) {
            playerTurn = false;
            drawFrame("Round:" + round);
            StdDraw.pause(800);
            randomString = generateRandomString(round);
            flashSequence(randomString);
            String typedString = solicitNCharsInput(round);
            if (typedString.equals(randomString)) {
                round += 1;
                continue;
            } else {
                gameOver = true;
                break;
            }
        }
        drawFrame("Game Over! You made it to round:");
    }
}
