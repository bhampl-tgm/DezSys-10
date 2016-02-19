package at.ac.tgm.hit.dezsys.hamplwortha;


public class CaclulatePi implements Calculate {

    /**
     * @see Calculate#calc(int)
     */
    @Override
    public double calc(int iterations) {
        double res = 0;
        for (int i = 1; i < iterations; i += 4) {
            res += 1.0 / i - 1.0 / (i + 2);
        }
        return 4 * res;
    }

}
