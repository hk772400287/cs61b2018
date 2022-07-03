import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();

    @Test
    public void testStudentArrayDeque() {
        Integer a = 0;
        Integer b = 0;
        String ops = "";
        for (int i = 0; i < 1000; i++) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.25) {
                sad.addFirst(i);
                ads.addFirst(i);
                ops = ops + "addFirst(" + i + ")\n";
            } else if (numberBetweenZeroAndOne >= 0.25 && numberBetweenZeroAndOne < 0.5) {
                sad.addLast(i);
                ads.addLast(i);
                ops = ops + "addLast(" + i + ")\n";
            } else if (numberBetweenZeroAndOne >= 0.5 && numberBetweenZeroAndOne < 0.75) {
                if (sad.removeFirst() == null || ads.removeFirst() == null) {
                    continue;
                } else {
                    a = sad.removeFirst();
                    b = ads.removeFirst();
                    ops = ops + "removeFirst()\n";
                }
            } else {
                if (sad.removeLast() == null || ads.removeLast() == null) {
                    continue;
                } else {
                    a = sad.removeLast();
                    b = ads.removeLast();
                    ops = ops + "removeLast()\n";
                }
            }
            assertEquals(ops, b, a);
        }
    }



}
