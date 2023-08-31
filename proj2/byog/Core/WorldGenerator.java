package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class WorldGenerator implements Serializable {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private final Random random;
    protected Position playerPos;
    private Position doorPos;

    private ArrayList<Position> dots;
    protected ArrayList<Room> existingRooms;
    protected TETile[][] world;

    public Position getDoorPos() {
        return this.doorPos;
    }

    public WorldGenerator(long seed) {
        this.random = new Random(seed);
        this.playerPos = null;
        this.doorPos = null;
        this.dots = new ArrayList<>();
        this.existingRooms = new ArrayList<>();
        this.world = new TETile[WIDTH][HEIGHT];
    }
    protected class Room implements Serializable {
        private Position p;
        private int width;
        private int height;


        protected Room(Position p, int width, int height) {
            this.p = p;
            this.width = width;
            this.height = height;
        }
        private Boolean isOverlap() {
            for (int i = -1; i <= height; i++) {
                for (int j = -1; j <= width; j++) {
                    if (world[j + p.x][i + p.y] == Tileset.DOT) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    protected static class Position implements Serializable {
        protected int x;
        protected int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void drawOneRoom(Room r) {
        for (int i = 0; i < r.height; i++) {
            for (int j = 0; j < r.width; j++) {
                world[j + r.p.x][i + r.p.y] = Tileset.DOT;
                dots.add(new Position(j + r.p.x, i + r.p.y));
            }
        }
    }

    public Room randomRoom() {
        while (true) {
            final int x = random.nextInt(WIDTH - 6) + 3;
            final int y = random.nextInt(HEIGHT - 6) + 3;
            Position p = new Position(x, y);
            int w = random.nextInt(5) + 2;
            int h = random.nextInt(5) + 2;
            if (x + w < WIDTH - 2 && y + h < HEIGHT - 2) {
                Room room = new Room(p, w, h);
                return room;
            }
        }
    }

    public void drawManyRooms() {
        int roomNumber = random.nextInt(10) + 15;
        for (int i = 0; i < roomNumber; i++) {
            Room r = randomRoom();
            if (r.isOverlap()) {
                continue;
            }
            drawOneRoom(r);
            existingRooms.add(r);
        }
    }


    public void connectTwoRooms(Room r1, Room r2) {
        int xRangeFrom = max(r1.p.x, r2.p.x);
        int xRangeTo = min(r1.p.x + r1.width - 1, r2.p.x + r2.width - 1);
        int yRangeFrom = max(r1.p.y, r2.p.y);
        int yRangeTo = min(r1.p.y + r1.height - 1, r2.p.y + r2.height - 1);
        if (xRangeFrom <= xRangeTo) {
            int xPath = random.nextInt(xRangeTo - xRangeFrom + 1) + xRangeFrom;
            for (int i = yRangeTo; i < yRangeFrom; i++) {
                world[xPath][i] = Tileset.DOT;
                dots.add(new Position(xPath, i));
            }
        } else if (yRangeFrom <= yRangeTo) {
            int yPath = random.nextInt(yRangeTo - yRangeFrom + 1) + yRangeFrom;
            for (int i = xRangeTo; i < xRangeFrom; i++) {
                world[i][yPath] = Tileset.DOT;
                dots.add(new Position(i, yPath));
            }
        } else {
            drawL(r1, r2);
        }
    }

    private void drawL(Room r1, Room r2) {
        int y0 = random.nextInt(r1.height) + r1.p.y;
        int x0 = random.nextInt(r2.width) + r2.p.x;
        int xFrom = min(x0, r1.p.x);
        int xTo = max(x0, r1.p.x);
        for (int i = xFrom; i <= xTo; i++) {
            world[i][y0] = Tileset.DOT;
            dots.add(new Position(i, y0));
        }
        int yFrom = min(y0, r2.p.y);
        int yTo = max(y0, r2.p.y);
        for (int i = yFrom; i <= yTo; i++) {
            world[x0][i] = Tileset.DOT;
            dots.add(new Position(x0, i));
        }
    }

    public void initializeTiles() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void addWalls() {
        ArrayList<Position> wallList = new ArrayList<>();
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y] == Tileset.DOT) {
                    for (Position p : neighbors(x, y)) {
                        if (world[p.x][p.y] == Tileset.NOTHING) {
                            world[p.x][p.y] = Tileset.WALL;
                            for (Position p0 : Arrays.copyOfRange(neighbors(p.x, p.y), 0, 3)) {
                                if (isWithinWindows(p0)) {
                                    if (world[p0.x][p0.y] == Tileset.DOT) {
                                        wallList.add(p);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        int wallListIndex = random.nextInt(wallList.size());
        doorPos = wallList.get(wallListIndex);
        world[doorPos.x][doorPos.y] = Tileset.LOCKED_DOOR;
    }


    private Boolean isWithinWindows(Position p) {
        return p.x >= 0 && p.x <= WIDTH - 1 && p.y >= 0 && p.y <= HEIGHT - 1;
    }

    private Position[] neighbors(int x, int y) {
        Position[] neighbor = new Position[8];
        neighbor[0] = new Position(x, y - 1);
        neighbor[1] = new Position(x - 1, y);
        neighbor[2] = new Position(x + 1, y);
        neighbor[3] = new Position(x, y + 1);
        neighbor[4] = new Position(x + 1, y + 1);
        neighbor[5] = new Position(x - 1, y + 1);
        neighbor[6] = new Position(x + 1, y - 1);
        neighbor[7] = new Position(x - 1, y - 1);
        return neighbor;
    }

    public void setPlayer() {
        int randomDotIndex = random.nextInt(dots.size());
        playerPos = dots.get(randomDotIndex);
        world[playerPos.x][playerPos.y] = Tileset.PLAYER;
    }


    public void sortRoomList() {
        int nearestRoomTo0 = getNearesRoomtTo0(existingRooms);
        switchTwoRooms(nearestRoomTo0, 0, existingRooms);
        for (int i = 1; i < existingRooms.size(); i++) {
            int indexOfNearest = searchNearest(existingRooms, i, existingRooms.size() - 1,
                    existingRooms.get(i - 1));
            switchTwoRooms(indexOfNearest, i, existingRooms);
        }
    }

    private int distanceBetweenTwoRooms(Room r1, Room r2) {
        int d = (r1.p.x - r2.p.x) * (r1.p.x - r2.p.x)
                + (r1.p.y - r2.p.y) * (r1.p.y - r2.p.y);
        return d;
    }

    private int getNearesRoomtTo0(ArrayList<Room> roomList) {
        Room roomAt0 = new Room(new Position(0, 0), 2, 2);
        int index = 0;
        int dTo0Min = Integer.MAX_VALUE; //distanceBetweenTwoRooms(roomList.get(0), roomAt0);
        for (Room r : roomList) {
            if (distanceBetweenTwoRooms(r, roomAt0) < dTo0Min) {
                dTo0Min = distanceBetweenTwoRooms(r, roomAt0);
                index = roomList.indexOf(r);
            }
        }
        return index;
    }

    private void switchTwoRooms(int x, int y, ArrayList<Room> roomList) {
        Room temp = roomList.get(x);
        roomList.set(x, roomList.get(y));
        roomList.set(y, temp);
    }

    private int searchNearest(ArrayList<Room> roomList, int x, int y, Room r) {
        int d = distanceBetweenTwoRooms(roomList.get(x), r);
        int index = x;
        for (int i = x; i <= y; i++) {
            if (distanceBetweenTwoRooms(roomList.get(i), r) < d) {
                d = distanceBetweenTwoRooms(roomList.get(i), r);
                index = i;
            }
        }
        return index;
    }
}
