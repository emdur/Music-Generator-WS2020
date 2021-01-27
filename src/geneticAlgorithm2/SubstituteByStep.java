package geneticAlgorithm2;

import java.util.ArrayList;
import org.jfugue.theory.Note;

public class SubstituteByStep implements Critic {
	//THIS CRITIC IS NOT USED IN THE APPLICATION AT THE MOMENT.
	//IT DOES NOT USE EVOLUTION, SO SOME STEPS IN population.evolve() MUST BE SKIPPED FOR IT.

	@Override
	public double getCalculatedFitness(Individual individual) {
		// TODO Auto-generated method stub
		return 66;
	}

	@Override
	public ArrayList<Individual> mutate(Individual parent, ArrayList<Individual> individuals) {
		ArrayList<Individual> res = new ArrayList<Individual>();
		Individual changed = new Individual();
		changed.notes.addAll(sequence(parent.notes, 7));
		res.add(changed);
		return res;
	}
	
	private ArrayList<Note> sequence(ArrayList<Note> element, int step) {
		// takes the nth element of a sequence in steps. At some point the initial sequence reemerges.
		ArrayList<Note> result = new ArrayList<Note>();
		for(int z = 0; z < step; z++) 
		{
			for(int j = z; j < element.size(); j+=step) {
				result.add(element.get(j));
			}
		}
		return result;
		
	}

}
