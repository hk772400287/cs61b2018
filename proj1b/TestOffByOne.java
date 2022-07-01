import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.**/
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars1() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('r', 'q'));
        assertTrue(offByOne.equalChars('&', '%'));
    }

    @Test
    public void testEqualChars2() {
        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('z', 'a'));
        assertFalse(offByOne.equalChars('a', 'a'));
    }
    // Your tests go here.
//    Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
}
