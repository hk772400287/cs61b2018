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
    private final long SEED;
    private final Random RANDOM;

    public WorldGenerator(long SEED) {
        this.SEED = SEED;
        RANDOM = new Random(SEED);
    }
    static class room {
        private Position p;
        private int width;
        private int height;
        static ArrayList<room> existingRooms = new ArrayList();

        public room(Position p, int width, int height) {
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

    public void drawOneRoom(room r, TETile[][] world) {
        for(int i = 0; i < r.height; i++) {
            for(int j = 0; j < r.width; j++) {
                world[j + r.p.x][i + r.p.y] = Tileset.GRASS;
            }
        }
    }

    public room randomRoom() {
        //width = 100, height = 70
        // 1 <= x <= 99  1 <= y <= 69
        while (true) {
            int x = RANDOM.nextInt(WIDTH - 2) + 1;
            int y = RANDOM.nextInt(HEIGHT - 2) + 1;
            Position p = new Position(x, y);
            int w = RANDOM.nextInt(5) + 2;
            int h = RANDOM.nextInt(5) + 2;
            if (x + w < WIDTH && y + h < HEIGHT) {
                return new room(p, w, h);
            }
        }
    }

    public void drawManyRooms(TETile[][] world) {
        int roomNumber = RANDOM.nextInt(10) + 10;
        for(int i = 0; i < roomNumber; i++) {
            room r = randomRoom();
            if (r.isOverlap(world)) {
                continue;
            }
            drawOneRoom(r, world);
        }
    }


    public void connectTwoRooms(room r1, room r2, TETile[][] world) {
        int XrangeFrom = max(r1.p.x, r2.p.x);
        int XrangeTo = min(r1.p.x + r1.width - 1, r2.p.x + r2.width - 1);
        int YrangeFrom = max(r1.p.y, r2.p.y);
        int YrangeTo = min(r1.p.y + r1.height - 1, r2.p.y + r2.height - 1);
        if (XrangeFrom <= XrangeTo) {
            int Xpath = RANDOM.nextInt(XrangeTo - XrangeFrom + 1) + XrangeFrom;
            for(int i = YrangeTo; i < YrangeFrom; i++) {
                world[Xpath][i] = Tileset.GRASS;
            }
        } else if (YrangeFrom <= YrangeTo) {
            int Ypath = RANDOM.nextInt(YrangeTo - YrangeFrom + 1) + YrangeFrom;
            for(int i = XrangeTo; i < XrangeFrom; i++) {
                world[i][Ypath] = Tileset.GRASS;
            }
        } else {
                drawL(r1, r2, world);
            }
    }

    private void drawL(room r1, room r2, TETile[][] world) {
        int y0 = RANDOM.nextInt(r1.height) + r1.p.y;
        int x0 = RANDOM.nextInt(r2.width) + r2.p.x;
        int Xfrom = min(x0, r1.p.x);
        int Xto = max(x0, r1.p.x);
        for(int i = Xfrom; i <= Xto; i++) {
            world[i][y0] = Tileset.GRASS;
        }
        int Yfrom = min(y0, r2.p.y);
        int Yto = max(y0, r2.p.y);
        for(int i = Yfrom; i <= Yto; i++) {
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
