import synthesizer.GuitarString;
public class GuitarHero {
   // private static final double CONCERT_A = 440.0;
    //private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        //synthesizer.GuitarString stringA = new synthesizer.GuitarString(CONCERT_A);
        //synthesizer.GuitarString stringC = new synthesizer.GuitarString(CONCERT_C);
        GuitarString [] gs = new GuitarString[37];
        for (int i = 0; i < 37; i++) {
            double frequency = 440 * Math.pow(2, (i - 24) / 12.0);
            gs[i] = new GuitarString(frequency);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) >= 0) {
                    //stringA.pluck();
                    int index = keyboard.indexOf(key);
                    gs[index].pluck();
                } else {
                    continue;
                }
            }

            /* compute the superposition of samples */
           // double sample = stringA.sample() + stringC.sample();
            double sample = 0;
            for (int i = 0; i < 37; i++) {
                sample += gs[i].sample();
            }

                /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            //stringA.tic();
            //stringC.tic();
            for (int i = 0; i < 37; i++) {
                gs[i].tic();
            }
        }
    }
}
