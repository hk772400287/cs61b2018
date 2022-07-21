package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import static byog.Core.WorldGenerator.*;

public class TestWorldGenerator {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
//        WorldGenerator generator = new WorldGenerator(865562189400100566L);
//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);
//        TETile[][] world = new TETile[WIDTH][HEIGHT];
//        WorldGenerator.initializeTiles(world);
//        generator.drawManyRooms(world);
//        for (int i = 0; i < WorldGenerator.Room.existingRooms.size() - 1; i++) {
//            generator.connectTwoRooms(WorldGenerator.Room.existingRooms.get(i),
//                    WorldGenerator.Room.existingRooms.get(i + 1), world);
//        }
//        generator.addWalls(world);
//        ter.renderFrame(world);

        Game game = new Game();
        TETile[][] worldState = game.playWithInputString("n5521916180655585618s");
        System.out.println(TETile.toString(worldState));
        TETile[][] worldState2 = game.playWithInputString("n8164642385731023482s");
        System.out.println(TETile.toString(worldState2));
    }
}
