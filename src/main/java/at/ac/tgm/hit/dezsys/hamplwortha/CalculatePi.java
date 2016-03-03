package at.ac.tgm.hit.dezsys.hamplwortha;

/**
 * This class is a implementation of calculate with pi
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public class CalculatePi implements Calculate {

    @Override
    public double calc(long iterations) {
        double res = 0;
        for (long i = 1; i < iterations; i += 4) {
            res += 1.0 / i - 1.0 / (i + 2);
        }
        return 4 * res;
    }

}
