package at.ac.tgm.hit.dezsys.hamplwortha;

/**
 * This class handles calculations.
 *
 * @author Burkhard Hampl [bhampl@student.tgm.ac.at]
 * @version 1.0
 */
public interface Calculate {
    /**
     * Calculates a given algorithm or calculation
     *
     * @param iterations the iterations of the calculation
     * @return the result
     */
    double calc(long iterations);
}
