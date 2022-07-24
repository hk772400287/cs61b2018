package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import static byog.Core.WorldGenerator.*;

public class TestWorldGenerator {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        WorldGenerator generator = new WorldGenerator(8659);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        WorldGenerator.initializeTiles(world);
        generator.drawManyRooms(world);
        generator.sortRoomList(generator.existingRooms);
        for (int i = 0; i < generator.existingRooms.size() - 1; i++) {
            generator.connectTwoRooms(generator.existingRooms.get(i),
                    generator.existingRooms.get(i + 1), world);
        }
        generator.connectTwoRooms(generator.existingRooms.get(generator.existingRooms.size() - 2),
                generator.existingRooms.get(generator.existingRooms.size() - 1), world);
        generator.addWalls(world);
        generator.setPlayer(world);
        ter.renderFrame(world);

    }
}
