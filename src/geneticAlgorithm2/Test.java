package geneticAlgorithm2;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		new GUI();
	}
	
	//fix:
	/*
	 * fix substituebystep (it stops aber 2 gens, does not work correctly)
	 * let the user decide the interval for substitutebystep
	 * 
	 * get duration of bar/impulse to calculate a generation from player
	 * 
	 * give a pattern higher fitness if it's close to the desired value (even if it's not quite it)
	 * 
	 * implement crossover methods
	 * detach crossover and mutate from the fitness interface, fitness interface = "critic"
	 * --> done, but is that clever? maybe use the crossover methods in the fitness classes
	 * 
	 * make a fitness that evaluates the "beauty" of the pattern by comparing the note lengths in the pattern to each other
	 * --> done
	 * 
	 * improve the user interface and the code for it, give the user more parameters and describe the options better
	 * improve the code structure of gui/solution/population, maybe make solution a singleton
	 * 
	 * make sure the fitnesses work together or keep the user from using the ones together that do not
	 * 
	 * make an "update number of generations" button & implement needed changeGenNum method in solution/population

	*/
}