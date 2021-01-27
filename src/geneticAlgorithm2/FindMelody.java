package geneticAlgorithm2;

import java.util.ArrayList;
import java.util.Random;

import org.jfugue.theory.Note;

public class FindMelody implements Critic {
	//THIS CRITIC IS NOT USED IN THE APPLICATION AT THE MOMENT.
	//The fitness value is higher the closer the pattern is to a predefined
	// melody that is passed to the constructor.
	private Individual melodyToFind;
	public FindMelody(Individual melody) {
		this.melodyToFind = melody;
	}
	
	@Override
	public double getCalculatedFitness(Individual individual) {
		double fitness = 0;
		for(int i = 0; i < melodyToFind.notes.size(); i++) {
			if(melodyToFind.notes.get(i).equals(individual.notes.get(i))) {
				fitness += 100/(double)individual.notes.size();
			}
		}
		return fitness;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Individual> mutate(Individual parent, ArrayList<Individual> individuals) {
		// create a random new Note & put it in the place of a note in the parent pattern (random index)
		ArrayList<Individual> mutants = (ArrayList<Individual>) individuals.clone();
		int noteToChange = 0;
		int rests = 0;
		for(Note n : parent.notes) {
			if(n.isRest()) {
				rests++;
			}
		}
		Random ran = new Random();
		for(int i = 0; i < individuals.size(); i++) {
			int size = individuals.get(i).notes.size();
			noteToChange = ran.nextInt(size);
			int val = ran.nextInt(100);
			if(val > 95 && rests < size/3) {
				Note note = new Note("R");
				note.setDuration(individuals.get(i).notes.get(noteToChange).getDuration());
				mutants.get(i).notes.set(noteToChange, note);
			} else {
				Note note = new Note(ran.nextInt(128));
				note.setDuration(individuals.get(i).notes.get(noteToChange).getDuration());
				mutants.get(i).notes.set(noteToChange, note);
			}
			noteToChange = ran.nextInt(size);
			val = ran.nextInt(100);
			if(val > 95 && rests < size/3) {
				Note note = new Note("R");
				note.setDuration(individuals.get(i).notes.get(noteToChange).getDuration());
				mutants.get(i).notes.set(noteToChange, note);
			} else {
				Note note = new Note(ran.nextInt(128));
				note.setDuration(individuals.get(i).notes.get(noteToChange).getDuration());
				mutants.get(i).notes.set(noteToChange, note);
			}
		}
		mutants.remove(mutants.size()-1);
		mutants.add(parent);
		return mutants;
	}

}
