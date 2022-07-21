package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class WorldGenerator {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private final long seed;
    private final Random random;

    public WorldGenerator(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }
    static class Room {
        private Position p;
        private int width;
        private int height;
        static ArrayList<Room> existingRooms = new ArrayList();

        public Room(Position p, int width, int height) {
            this.p = p;
            this.width = width;
            this.height = height;
            existingRooms.add(this);
        }
        private Boolean isOverlap(TETile[][] world) {
            for (int i = -1; i <= height; i++) {
                for (int j = -1; j <= width; j++) {
                    if (world[j + p.x][i + p.y] == Tileset.GRASS) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void drawOneRoom(Room r, TETile[][] world) {
        for (int i = 0; i < r.height; i++) {
            for (int j = 0; j < r.width; j++) {
                world[j + r.p.x][i + r.p.y] = Tileset.GRASS;
            }
        }
    }

    public Room randomRoom() {
        //width = 100, height = 70
        // 1 <= x <= 99  1 <= y <= 69
        while (true) {
            int x = random.nextInt(WIDTH - 2) + 1;
            int y = random.nextInt(HEIGHT - 2) + 1;
            Position p = new Position(x, y);
            int w = random.nextInt(5) + 2;
            int h = random.nextInt(5) + 2;
            if (x + w < WIDTH && y + h < HEIGHT) {
                return new Room(p, w, h);
            }
        }
    }

    public void drawManyRooms(TETile[][] world) {
        int roomNumber = random.nextInt(10) + 10;
        for (int i = 0; i < roomNumber; i++) {
            Room r = randomRoom();
            if (r.isOverlap(world)) {
                continue;
            }
            drawOneRoom(r, world);
        }
    }


    public void connectTwoRooms(Room r1, Room r2, TETile[][] world) {
        int xRangeFrom = max(r1.p.x, r2.p.x);
        int xRangeTo = min(r1.p.x + r1.width - 1, r2.p.x + r2.width - 1);
        int yRangeFrom = max(r1.p.y, r2.p.y);
        int yRangeTo = min(r1.p.y + r1.height - 1, r2.p.y + r2.height - 1);
        if (xRangeFrom <= xRangeTo) {
            int xPath = random.nextInt(xRangeTo - xRangeFrom + 1) + xRangeFrom;
            for (int i = yRangeTo; i < yRangeFrom; i++) {
                world[xPath][i] = Tileset.GRASS;
            }
        } else if (yRangeFrom <= yRangeTo) {
            int yPath = random.nextInt(yRangeTo - yRangeFrom + 1) + yRangeFrom;
            for (int i = xRangeTo; i < xRangeFrom; i++) {
                world[i][yPath] = Tileset.GRASS;
            }
        } else {
            drawL(r1, r2, world);
        }
    }

    private void drawL(Room r1, Room r2, TETile[][] world) {
        int y0 = random.nextInt(r1.height) + r1.p.y;
        int x0 = random.nextInt(r2.width) + r2.p.x;
        int xFrom = min(x0, r1.p.x);
        int xTo = max(x0, r1.p.x);
        for (int i = xFrom; i <= xTo; i++) {
            world[i][y0] = Tileset.GRASS;
        }
        int yFrom = min(y0, r2.p.y);
        int yTo = max(y0, r2.p.y);
        for (int i = yFrom; i <= yTo; i++) {
            world[x0][i] = Tileset.GRASS;
        }
    }

    public void initializeTiles(TETile[][] w) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                w[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void addWalls(TETile[][] w) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (w[x][y] == Tileset.GRASS) {
                    for (Position p : neighbors(x, y)) {
                        if (w[p.x][p.y] == Tileset.NOTHING) {
                            w[p.x][p.y] = Tileset.WALL;
                        }
                    }
                }
            }
        }
    }

    private Position[] neighbors(int x, int y) {
        Position[] neighbor = new Position[8];
        neighbor[0] = new Position(x - 1, y - 1);
        neighbor[1] = new Position(x - 1, y);
        neighbor[2] = new Position(x - 1, y + 1);
        neighbor[3] = new Position(x, y + 1);
        neighbor[4] = new Position(x + 1, y + 1);
        neighbor[5] = new Position(x + 1, y);
        neighbor[6] = new Position(x + 1, y - 1);
        neighbor[7] = new Position(x, y - 1);
        return neighbor;
    }
}
