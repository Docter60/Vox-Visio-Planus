/**
 * 
 */
package math;

/**
 * Mathg is a custom math library for game calculations.
 * @author Docter60
 *
 */
public class Mathg {

	/**
	 * Linear interpolation between two doubles by timestep t.
	 * @param a value a
	 * @param b value b
	 * @param t timestep
	 * @return a interpolated from b by timestep t
	 */
	public static double lerp(double a, double b, double t){
		return a + t * (b - a);
	}
	
}
