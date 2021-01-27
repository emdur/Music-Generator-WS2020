package geneticAlgorithm2;

import java.util.ArrayList;
import java.util.Random;

import org.jfugue.theory.Note;

public class ClosenessOfNotes implements Critic {
	//Criteria: Notes should be close together

	@Override
	public double getCalculatedFitness(Individual individual) {
		double fitness = 0;
		// compare the notes to each other & increase the fitness by the int difference between them
		// if the notes are closer together, which is the goal, the difference is small.
		//	--> reverse calculation in return statement
		
		int rests = 0;
		for(Note n : individual.notes) {
			if(n.isRest()) {
				rests++;
			}
		}
		double relativeVal = individual.notes.size()/(individual.notes.size()-rests);
		for(Note i : individual.notes) {
			if(!i.isRest()) {
				//each note is compared to all the other notes in the pattern
				for(Note j : individual.notes) {
					if(!j.isRest()) {
						if(i.getValue() < j.getValue()) {
							fitness += (j.getValue()-i.getValue())*relativeVal;
						} else {
							fitness += (i.getValue()-j.getValue())*relativeVal;
						}
					}
				}	
			}
			
		}
		fitness = 100-(fitness/100);
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
				note = new Note(ran.nextInt(128));
				note.setDuration(individuals.get(i).notes.get(noteToChange).getDuration());
				mutants.get(i).notes.set(noteToChange, note);
			}
		}
		return mutants;
	}

}
