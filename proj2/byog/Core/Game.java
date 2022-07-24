package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import byog.Core.WorldGenerator.Position;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        mainMenu();
        String selectedmenu = selectmenu();
        if (selectedmenu.equals("n")) {
            long seed = getSeed();
            WorldGenerator generator = new WorldGenerator(seed);
            ter.initialize(WIDTH, HEIGHT);
            generator.world = generateNewWorld(seed, generator);
            ter.renderFrame(generator.world);
            playGame(generator);
            StdDraw.pause(500);
            end();
        } else if (selectedmenu.equals("l")) {
            WorldGenerator generator = loadFile();
            ter.initialize(WIDTH, HEIGHT);
            ter.renderFrame(generator.world);
            playGame(generator);
            StdDraw.pause(500);
            end();
        } else if (selectedmenu.equals("q")) {
            System.exit(200);
        }
    }

    public void mainMenu() {
        StdDraw.setCanvasSize(WIDTH * 10,  HEIGHT * 20);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.black);
        StdDraw.enableDoubleBuffering();
        Font fontTitle = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(fontTitle);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.7, "CS61B: THE GAME");
        Font fontMenu = new Font("Monaco", Font.BOLD, 15);
        StdDraw.setFont(fontMenu);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.4, "NEW GAME (N)");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.35, "LOAD GAME (L)");
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.3, "QUIT (Q)");
        StdDraw.show();
    }

    private String selectmenu() {
        String selectedMenu = "";
        while (selectedMenu.length() < 1) {
            while (StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                selectedMenu += Character.toString(nextKey);
            }
        }
        return selectedMenu.toLowerCase();
    }

    private long getSeed() {
        String seed = "";
        StdDraw.clear(Color.black);
        Font fontTitle = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(fontTitle);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5, "SEED: ");
        StdDraw.show();
        while (!seed.endsWith("s")) {
            while (StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                seed += Character.toString(nextKey);
                StdDraw.clear(Color.black);
                StdDraw.text(WIDTH * 0.5, HEIGHT * 0.5, "SEED: " + seed);
                StdDraw.show();
            }
        }
        seed = seed.substring(0, seed.length() - 1);
        return Long.parseLong(seed);
    }

    public void playGame(WorldGenerator generator) {
        String keysnotes = "";
        while (generator.world[generator.doorPos.x][generator.doorPos.y] != Tileset.UNLOCKED_DOOR) {
            hud(generator.world);
            while (StdDraw.hasNextKeyTyped()) {
                char nextKey = StdDraw.nextKeyTyped();
                keysnotes += Character.toString(nextKey);
                if (keysnotes.endsWith(":Q") || keysnotes.endsWith(":q")) {
                    saveToFile(generator);
                    System.exit(123);
                }
                makeOneMovement(generator, nextKey);
                ter.renderFrame(generator.world);
            }
        }
    }

    private void hud(TETile[][] world) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x, y);
            }
        }
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        String text = "";
        if (x >= 2 && x <= WIDTH - 3 && y >= 2 && y <= HEIGHT - 3) {
            if (world[x][y].equals(Tileset.DOT)) {
                text = "path";
            } else if (world[x][y].equals(Tileset.WALL)) {
                text = "wall";
            } else if (world[x][y].equals(Tileset.LOCKED_DOOR)) {
                text = "locked door";
            }
        }
        StdDraw.setPenColor(Color.white);
        StdDraw.text(WIDTH * 0.05, HEIGHT * 0.96, text);
        StdDraw.show();
    }

    public void end() {
        StdDraw.clear(Color.black);
        Font fontTitle = new Font("Arial", Font.BOLD, 40);
        StdDraw.setFont(fontTitle);
        StdDraw.setPenColor(Color.yellow);
        StdDraw.text(WIDTH * 0.5, HEIGHT * 0.7, "You win!");
        StdDraw.show();
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        //  Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        input = input.toLowerCase();
        Pattern newSeedp = Pattern.compile("n(\\d+)s([wasd]*):?q?");
        Matcher mNewSeed = newSeedp.matcher(input);
        Pattern loadp = Pattern.compile("l([wasd]*):?q?");
        Matcher mload = loadp.matcher(input);
        WorldGenerator generator = null;
        if (mNewSeed.find()) {
            //get seed.
            String target = mNewSeed.group(1);
            long seed = Long.parseLong(target);
            generator = new WorldGenerator(seed);
            //generage a new world with player@.
            generator.world = generateNewWorld(seed, generator);
            //make some movements.
            String movement = mNewSeed.group(2);
            generator.world = makeMovements(generator, movement);
        } else if (mload.find()) {
            //load a saved world and implement some movements.
            String movement = mload.group(1);
            generator = loadFile();
            generator.world = makeMovements(generator, movement);
        }
        if (input.endsWith(":q")) {
            saveToFile(generator);
        }
        return generator.world;
    }

    public TETile[][] generateNewWorld(long seed, WorldGenerator generator) {
        generator.initializeTiles();
        generator.drawManyRooms();
        generator.sortRoomList(generator.existingRooms);
        for (int i = 0; i < generator.existingRooms.size() - 1; i++) {
            generator.connectTwoRooms(generator.existingRooms.get(i),
                    generator.existingRooms.get(i + 1));
        }
        generator.addWalls();
        generator.setPlayer();
        return generator.world;
    }

    public TETile[][] makeMovements(WorldGenerator generator, String movement) {
        for (char c : movement.toCharArray()) {
            if (generator.world[generator.doorPos.x][generator.doorPos.y]
                    .equals(Tileset.UNLOCKED_DOOR)) {
                return generator.world;
            }
            makeOneMovement(generator, c);
        }
        return generator.world;
    }

    private TETile[][] makeOneMovement(WorldGenerator generator, char c) {
        Position nextPos = new Position(0, 0);
        if (c == 'a' || c == 'A') {
            nextPos.x = generator.playerPos.x - 1;
            nextPos.y = generator.playerPos.y;
        } else if (c == 'w' || c == 'W') {
            nextPos.x = generator.playerPos.x;
            nextPos.y = generator.playerPos.y + 1;
        } else if (c == 'd' || c == 'D') {
            nextPos.x = generator.playerPos.x + 1;
            nextPos.y = generator.playerPos.y;
        } else if (c == 's' || c == 'S') {
            nextPos.x = generator.playerPos.x;
            nextPos.y = generator.playerPos.y - 1;
        } else {
            return generator.world;
        }
        if (generator.world[nextPos.x][nextPos.y].equals(Tileset.LOCKED_DOOR)) {
            generator.world[nextPos.x][nextPos.y] = Tileset.UNLOCKED_DOOR;
        } else if (!generator.world[nextPos.x][nextPos.y].equals(Tileset.WALL)) {
            generator.world[generator.playerPos.x][generator.playerPos.y] = Tileset.DOT;
            generator.playerPos = nextPos;
            generator.world[generator.playerPos.x][generator.playerPos.y] = Tileset.PLAYER;
        }
        return generator.world;
    }

    public void saveToFile(WorldGenerator generator) {
        try {
            FileOutputStream fos = new FileOutputStream("D:\\cs61b\\cs61b2018\\proj2" +
                    "\\byog\\Core\\savedFile.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(generator);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public WorldGenerator loadFile() {
        WorldGenerator generator = null;
        try {
            FileInputStream fis = new FileInputStream("D:\\cs61b\\cs61b2018\\proj2" +
                    "\\byog\\Core\\savedFile.txt");
            ObjectInputStream inputStream = new ObjectInputStream(fis);
            generator = (WorldGenerator) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return generator;
    }

}
