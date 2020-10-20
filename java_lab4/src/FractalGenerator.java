import java.awt.geom.Rectangle2D;


/**
 * This class provides the common interface and operations for fractal
 * generators that can be viewed in the Fractal Explorer.
 */
public abstract class FractalGenerator {

    /**
     * This static helper function takes an integer coordinate and converts it
     * into a double-precision value corresponding to a specific range.  It is
     * used to convert pixel coordinates into double-precision values for
     * computing fractals, etc.
     *
     * @param rangeMin the minimum value of the floating-point range
     * @param rangeMax the maximum value of the floating-point range
     *
     * @param size the size of the dimension that the pixel coordinate is from.
     *        For example, this might be the image width, or the image height.
     *
     * @param coord the coordinate to compute the double-precision value for.
     *        The coordinate should fall in the range [0, size].
     */
    public static double getCoord(double rangeMin, double rangeMax,
                                  int size, int coord) {

        assert size > 0;
        assert coord >= 0 && coord < size;

        double range = rangeMax - rangeMin;
        return rangeMin + (range * (double) coord / (double) size);
    }


    /**
     * Sets the specified rectangle to contain the initial range suitable for
     * the fractal being generated.
     */
    public abstract void getInitialRange(Rectangle2D.Double range);


    /**
     * Updates the current range to be centered at the specified coordinates,
     * and to be zoomed in or out by the specified scaling factor.
     */
    public void recenterAndZoomRange(Rectangle2D.Double range,
                                     double centerX, double centerY, double scale) {

        double newWidth = range.width * scale;
        double newHeight = range.height * scale;

        range.x = centerX - newWidth / 2;
        range.y = centerY - newHeight / 2;
        range.width = newWidth;
        range.height = newHeight;
    }


    /**
     * Given a coordinate <em>x</em> + <em>iy</em> in the complex plane,
     * computes and returns the number of iterations before the fractal
     * function escapes the bounding area for that point.  A point that
     * doesn't escape before the iteration limit is reached is indicated
     * with a result of -1.
     */
    public abstract int numIterations(double x, double y);

    // MY CODE

    static class Mandelbrot  {


        public static void getInitialRange (Rectangle2D.Double range){
            // TODO
            // MAYBE done

            range.x = -2;
            range.y = -1.5;

            range.width =  3;
            range.height = 3;
        }

        public static final int MAX_ITERATIONS = 2000;

        // TODO
        // Check if this is right

        public static int numIterations (double x, double y){

            ComplexNumber c = new ComplexNumber(x,y);
            ComplexNumber z = new ComplexNumber();

            int iterations = 0;

            //(x^2 + y^2)
            while (z.getX() * z.getX() + z.getY() * z.getY() <= 4 &&
                    iterations < MAX_ITERATIONS){

                //(Zx^2 + Cx - Zy^2 - Cy)
                z.setX(z.getX() * z.getX() + c.getX()
                        - z.getY() * z.getY() - c.getY());
                //(Zy^2 + Cy)
                z.setY(z.getY() * z.getY() +
                        c.getY());

                iterations++;
            }

            if (iterations >= MAX_ITERATIONS) return -1;

            return iterations;

        }
    };
}
