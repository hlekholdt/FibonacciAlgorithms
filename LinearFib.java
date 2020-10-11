package prog03;

/**
 *
 * @author hle
 */
public class LinearFib implements Fib {
	
	/** The Fibonacci number generator 0, 1, 1, 2, 3, 5, 8, 13, 21,  ...
	@param n index
	@return nth Fibonacci number
    */
    public double fib (int n) {
    	
    	if (n == 0) 
    		return 0;
    	
    	int temp = 0;
    	int a = 0;
    	int b = 1;
    	for (int i = 1; i < n; i++) {
    		temp = a + b;
    		a = b;
    		b = temp;
    	}
    	return temp;
    }

    /** The order O() of the implementation.
	@param n index
	@return the function of n inside the O()
    */
    public double O (int n) {
    	return n;
    }
}
