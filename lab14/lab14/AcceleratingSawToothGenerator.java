package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;
    private int periodStart;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.state = 0;
        this.period = period;
        this.factor = factor;
        this.periodStart = 0;
    }

    public double next() {
        state += 1;
        if (state - periodStart >= period) {
            periodStart += period;
            period = (int) Math.floor(period * factor);
        }
        return normalize(state - periodStart);
    }

    private double normalize(int x) {
        double k = 2.0 / this.period;
        return x * k - 1;
    }
}
