package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        for (int x : arb) {
            System.out.println(x);
        }
        assertEquals(new Integer(1), arb.peek());
        assertEquals(new Integer(1), arb.dequeue());
        assertEquals(new Integer(2), arb.dequeue());
        arb.enqueue(6);
        assertEquals(new Integer(3), arb.dequeue());
        for (int x : arb) {
            System.out.println(x);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
