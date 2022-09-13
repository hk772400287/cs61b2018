package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;
    public StrangeBitwiseGenerator(int period) {
        this.state = 0;
        this.period = period;
    }

    public double next() {
        state = state + 1;
        int weirdState = state & (state >> 7) % period;
        return normalize(weirdState % period);
    }

    private double normalize(int x) {
        double k = 2.0 / this.period;
        return x * k - 1;
    }
}
