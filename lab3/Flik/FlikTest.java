import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testFlik() {
        //assertTrue(Flik.isSameNumber(128,128));
        assertTrue(Flik.isSameNumber(500,500));
        assertTrue(Flik.isSameNumber(-3,-3));
        assertFalse(Flik.isSameNumber(3,0));
    }
}
