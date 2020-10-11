package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, int ncalls) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    // Call fib(n) ncalls times (needs a loop!).!
    for (int i = 0; i < ncalls; i++) {
    	fibn = fib.fib(n);
    }

    // Get the current time in nanoseconds.!
    long end = System.nanoTime();

    // Return the average time converted to microseconds averaged over ncalls.
    return (end - start) / 1000.0 / ncalls;
  }

  /** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time in microseconds using the time method above.
    double t = averageTime(fib, n, 1);

    // If the time is (equivalent to) more than a second, return it.!
    if (t >= 1000000) {
    	return t;
    }

    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.!
    int numcalls = (int)(1000000/t);


    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }

  //private static UserInterface ui = new GUI("Fibonacci experiments");
  private static UserInterface ui = new TestUI();
  
  public static void doExperiments (Fib fib) {
	
	double constant = 0;
	double accTime = 0;
	int n;
	
	while (true) {
    	String input = ui.getInfo("Enter n: ");
    	
    	if (input == null)
    		break;
    	
    	if (input.length()==0) {
    		ui.sendMessage("Invalid entry, restarting.");
			continue;
    	}
    	
    	for (int i = 0; i < input.length(); i++) {
    		if (input.charAt(i) < 48 || input.charAt(i) > 57) {
    			ui.sendMessage("Please enter a positive integer, restarting.");
    			break;
    		}
    		else {
    			n = Integer.parseInt(input);
    	    	
    	    	if (constant == 0) {
    	    		accTime = accurateTime(fib,n);
    	        	constant = accTime / fib.O(n);
    	        	ui.sendMessage("fib(" + n + ") = " + fib.fib(n) + " in " + accTime + " microseconds.");
    	        	break;
    	    	}
    	    	
    	    	else {
    	    		double estimateTime = constant * fib.O(n);
    	    		ui.sendMessage("Estimated time on input " + n + " is " + estimateTime + " microseconds.");
    	    		
    	    		if (estimateTime > (3600000000.0)) {
    	    			
    	    			ui.sendMessage("Estimated time is more than an hour.\n" + 
    	    					"I will ask you if you really want to run it.");
    	    			
    	    			String[] options = {"Yes","No"};
    	    					
    	    			int o = ui.getCommand(options);
    	    					
    	    			switch (o) {
    	    				case -1: 
    	    					ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
    	    					break;
    	    				case 0:
    	    					continue;
    	    				case 1: 
    	    					break;
    	    			}
    	    			break;
    				}
    	    		accTime = accurateTime(fib,n);
    	    		double percErr = ((estimateTime-accTime)/accTime)*100;
    	    		ui.sendMessage("fib(" + n + ") = " + fib.fib(n) + " in " + accTime + " microseconds. " + percErr + "% error.");
    	    		constant = accTime / fib.O(n);
    	    		break;
    	    	}
    		}
    	}
     }
  }

  public static void doExperiments () {
    // EXERCISE 10
	  String[] commands = {
				"ExponentialFib",
				"LinearFib",
				"LogFib",
				"ConstantFib",
				"MysteryFib",
		"Exit"};
	  
	  while (true) {
		  int c = ui.getCommand(commands);
		  
		  switch(c) {
		  	case -1:
				ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
				break;
			case 0:
				doExperiments(new ExponentialFib());
				break;
			case 1:
				doExperiments(new LinearFib());
				break;
			case 2:
				doExperiments(new LogFib());
				break;
			case 3:
				doExperiments(new ConstantFib());
				break;
			case 4:
				doExperiments(new MysteryFib());
				break;
			case 5:
				return;
		  }
	  }
	  
  }

  static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new ConstantFib();
			
    System.out.println(efib);
    for (int i = 0; i < 11; i++)
      System.out.println(i + " " + efib.fib(i));
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1 = averageTime(efib, n1, 1000);
    System.out.println("n1 " + n1 + " time1 " + time1);
    
    int ncalls = (int)(1000000/time1);
    double avTime = averageTime(efib,n1,ncalls);
    System.out.println("The average time for n1 is " + avTime);
    double acTime = accurateTime(efib, n1);
    System.out.println("The accurate time for n1 is " + acTime);
    
    // Calculate constant:  time = constant times O(n).
    double c = time1 / efib.O(n1);
    System.out.println("c " + c);
    
    // Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.O(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    
    // Calculate actual running time for n2=30.
    double time2 = averageTime(efib, n2, 100);
    System.out.println("n2 " + n2 + " actual time " + time2);
    
    double avTime2 = averageTime(efib, n2, ncalls);
    System.out.println("the average time for n2 is " + avTime2);
    double acTime2 = accurateTime(efib, n2);
    System.out.println("the accurate time for n2 is " + acTime2);
    
    //step 4 (how long it would take to computer fib(100))
    int n3 = 100;
    double time3est = c * efib.O(n3);
    System.out.println("running fib(100) takes " + time3est);
  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    //labExperiments();
    //doExperiments(new LinearFib());
    doExperiments();
  }
}
