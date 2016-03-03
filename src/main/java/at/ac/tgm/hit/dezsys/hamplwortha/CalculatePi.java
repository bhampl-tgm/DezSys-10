package at.ac.tgm.hit.dezsys.hamplwortha;


public class CalculatePi implements Calculate {

    /**
     * @see Calculate#calc(long)
     */
    @Override
    public double calc(long iterations) {
        double res = 0;
        for (long i = 1; i < iterations; i += 4) {
            res += 1.0 / i - 1.0 / (i + 2);
        }
        return 4 * res;
    }

}
