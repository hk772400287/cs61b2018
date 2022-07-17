package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Hexagon {

    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 25;
    private static final Random RANDOM = new Random(SEED);

    private static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position() {
            this.x = 0;
            this.y = 0;
        }
    }

    public static void initializeTiles(TETile[][] w) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                w[x][y] = Tileset.NOTHING;
            }
        }
    }

    public static void addHexagon(TETile[][] w, Position p, int s, TETile t) {
        for(int i = s; i <= max(s); i = i + 2) {
            int[] startl = leftStart(i, w, p, s);
            for(int j = startl[0]; j < startl[0] + i; j += 1) {
                w[j][startl[1]] = t;
                w[j][startl[2]] = t;
            }
        }
    }

    private static int max(int s) {
        return s + 2 * (s - 1);
    }

    private static int[] leftStart(int n, TETile[][] w, Position p, int s) {
        int[] startl = new int[3];
        int change = (n - s) / 2;
        startl[0] = p.x - change;
        startl[1] = p.y + change;
        startl[2] = p.y + s * 2 - 1 - change;
        return startl;
    }

    public static void manyHexagons(TETile[][] w, Position p, int s) {
        Position p2 = lefttop(p, s);
        Position p1 = lefttop(p2, s);
        Position p3 = righttop(p, s);
        Position p4 = righttop(p3, s);
        drawColumn(5, w, p, s);
        drawColumn(4, w, p2, s);
        drawColumn(4, w, p3, s);
        drawColumn(3, w, p1, s);
        drawColumn(3, w, p4, s);
    }

    public static void drawColumn(int n, TETile[][] w, Position p, int s) {
        for (int i = 0; i < n; i += 1) {
            TETile t = randomTile();
            addHexagon(w, p, s, t);
            p = top(p, s);
        }
    }

    private static Position top(Position p, int s) {
        Position topl = new Position();
        topl.x = p.x;
        topl.y = p.y + 2 * s;
        return topl;
    }

    private static Position lefttop(Position p, int s) {
        Position leftTop = new Position();
        leftTop.x = p.x - (2 * s - 1);
        leftTop.y = p.y + s;
        return leftTop;
    }

    private static Position righttop(Position p, int s) {
        Position rightTop = new Position();
        rightTop.x = p.x + (2 * s - 1);
        rightTop.y = p.y + s;
        return rightTop;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.MOUNTAIN;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        initializeTiles(world);
        Position p = new Position(20, 10);
        //addHexagon(world, p, 4, t);
        manyHexagons(world, p, 2);
        ter.renderFrame(world);
    }
}
