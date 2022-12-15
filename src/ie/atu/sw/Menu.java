package ie.atu.sw;

import ie.atu.sw.utils.ConsoleColour;

import java.util.Scanner;

public class Menu {

	private Scanner s;
	private boolean keepRunning = true;

	public Menu() {
		s = new Scanner(System.in);
	}

	// Initialize Parser object
	//Parser p = new Parser();

	// Start ie.atu.sw.Menu & Process User Choice
	public void start() throws InterruptedException {
		showOptions();
		while (keepRunning) {

			// try-catch to deal with non-integer inputs generating NumberFormatExceptions
			try {
				int choice = Integer.parseInt(s.next());

				if (choice == 1) {
					System.out.println(ConsoleColour.RED);
					System.out.println("Choice 1 selected ");

				} else if (choice == 2) {

					System.out.println("Choice 2 selected ");

				} else if (choice == 3) {

					System.out.println("Choice 3 selected ");

				} else if (choice == 4) {

					System.out.println("Choice 4 selected ");

				} else if (choice == 5) {
					displayLoading();
					System.out.println();
					System.out.println("Choice 5 selected ");

				} else if(choice == 6) {
					System.out.println("Choice 6 selected ");
					System.out.println("Closing Application..");
					System.exit(0);
				} else {
					System.out.println("Invalid Choice");
					System.out.print("Select Option [1-6]>");
					System.out.println();
				}

			} catch (Exception e) {
				System.out.println("Please enter a number between 1 & 5 only. No characters.");
			}
		}
	}

	private void showOptions() throws InterruptedException {
		System.out.println(ConsoleColour.YELLOW);
		System.out.println("************************************************************");
		System.out.println("*       ATU - Dept. Computer Science & Applied Physics     *");
		System.out.println("*                                                          *");
		System.out.println("*              Virtual Threaded Text Indexer               *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Text File");
		System.out.println("(2) Configure Dictionary");
		System.out.println("(3) Configure Common Words");
		System.out.println("(4) Specify Output File");
		System.out.println("(5) Execute");
		System.out.println("(6) Quit");

		//Output a menu of options and solicit text from the user
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option [1-4]>");
		System.out.println();
	}

	public static void printProgress(int index, int total) {
		if (index > total) return;	//Out of range
		int size = 50; 				//Must be less than console width
		char done = '█';			//Change to whatever you like.
		char todo = '░';			//Change to whatever you like.

		//Compute basic metrics for the meter
		int complete = (100 * index) / total;
		int completeLen = size * complete / 100;

		/*
		 * A StringBuilder should be used for string concatenation inside a
		 * loop. However, as the number of loop iterations is small, using
		 * the "+" operator may be more efficient as the instructions can
		 * be optimized by the compiler. Either way, the performance overhead
		 * will be marginal.
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < size; i++) {
			sb.append((i < completeLen) ? done : todo);
		}

		/*
		 * The line feed escape character "\r" returns the cursor to the
		 * start of the current line. Calling print(...) overwrites the
		 * existing line and creates the illusion of an animation.
		 */
		System.out.print("\r" + sb + "] " + complete + "%");

		//Once the meter reaches its max, move to a new line.
		if (done == total) System.out.println("\n");
	}

	public void displayLoading() throws InterruptedException {
		System.out.print(ConsoleColour.YELLOW);	//Change the colour of the console text
		int size = 100;							//The size of the meter. 100 equates to 100%
		for (int i =0 ; i < size ; i++) {		//The loop equates to a sequence of processing steps
			printProgress(i + 1, size); 		//After each (some) steps, update the progress meter
			Thread.sleep(10);					//Slows things down so the animation is visible
		}
	}

}
