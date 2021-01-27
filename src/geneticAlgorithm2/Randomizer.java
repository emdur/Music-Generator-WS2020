package geneticAlgorithm2;

import java.util.ArrayList;
import java.util.Random;

import org.jfugue.theory.Note;

public class Randomizer implements Critic {
	//This is not used in the application at the moment.
	
	// non-reliable: fitness doesn't increase with more increments;
	//it can decrease because the fitness of the parent that stayed
	//from the previous increment is also randomized in eacht getFitness() call
	//DO NOT USE A RANDOMIZER IF YOU WANT FITNESS TO INCREASE!
	
	@Override
	public double getCalculatedFitness(Individual individual) {
		Random ran = new Random();
		return ran.nextInt(100);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Individual> mutate(Individual parent, ArrayList<Individual> individuals) {
		// create a random new Note & put it in the place of a note in the parent pattern (random index)
		ArrayList<Individual> mutants = new ArrayList<Individual>();
		int noteToChange = 0;
		mutants.add(parent);
		Random ran = new Random();
		for(int i = 0; i < 10; i++) {
			Individual mutant = new Individual();
			mutant.notes = (ArrayList<Note>) parent.notes.clone();
			noteToChange = ran.nextInt(parent.notes.size());
			mutant.notes.set(noteToChange, new Note(ran.nextInt(120)));
			//change 2 notes - 2nd note:
			noteToChange = ran.nextInt(parent.notes.size());
			mutant.notes.set(noteToChange, new Note(ran.nextInt(120)));
			
			mutants.add(mutant);
		}
		return mutants;
	}

}
