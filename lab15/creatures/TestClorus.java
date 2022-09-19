package creatures;

import huglife.*;
import org.junit.Test;

import java.util.HashMap;

public class TestClorus {
    @Test
    public void testChoose() {
        Clorus c1 = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded1 = new HashMap<Direction, Occupant>();
        surrounded1.put(Direction.TOP, new Impassible());
        surrounded1.put(Direction.BOTTOM, new Plip());
        surrounded1.put(Direction.LEFT, new Plip());
        surrounded1.put(Direction.RIGHT, new Impassible());
        Action actual1 = c1.chooseAction(surrounded1);
        Action expected1 = new Action(Action.ActionType.STAY);

        Clorus c2 = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded2 = new HashMap<Direction, Occupant>();
        surrounded2.put(Direction.TOP, new Empty());
        surrounded2.put(Direction.BOTTOM, new Plip());
        surrounded2.put(Direction.LEFT, new Empty());
        surrounded2.put(Direction.RIGHT, new Impassible());
        Action actual2 = c2.chooseAction(surrounded2);
        Action expected2 = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        Clorus c3 = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded3 = new HashMap<Direction, Occupant>();
        surrounded3.put(Direction.TOP, new Empty());
        surrounded3.put(Direction.BOTTOM, new Impassible());
        surrounded3.put(Direction.LEFT, new Impassible());
        surrounded3.put(Direction.RIGHT, new Impassible());
        Action actual3 = c3.chooseAction(surrounded3);
        Action expected3 = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        Clorus c4 = new Clorus(0.8);
        HashMap<Direction, Occupant> surrounded4 = new HashMap<Direction, Occupant>();
        surrounded4.put(Direction.TOP, new Empty());
        surrounded4.put(Direction.BOTTOM, new Impassible());
        surrounded4.put(Direction.LEFT, new Impassible());
        surrounded4.put(Direction.RIGHT, new Impassible());
        Action actual4 = c4.chooseAction(surrounded4);
        Action expected4 = new Action(Action.ActionType.MOVE, Direction.TOP);
    }
}
