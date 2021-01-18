package geneticAlgorithm2;

import java.util.ArrayList;
import java.util.Random;

import org.jfugue.theory.Note;

public class ReverseCFriend implements Fitness {
	//Criteria: Notes should not be "C"s (Octave does not matter)
	
		@Override
		public double getCalculatedFitness(Individual individual) {
			double fitness = 0;
			//calculate the relative weight of each note related to the patternlength
			double relativeVal = (100/(double)individual.notes.size());
			for(Note note : individual.notes) {
				if(Note.getToneStringWithoutOctave(note.getValue()) == "C" && !note.isRest()) {
					fitness += relativeVal;
				}
				else if(Note.getToneStringWithoutOctave(note.getValue()) == "C#" && !note.isRest()) {
					fitness += relativeVal/2;
				}
				
			}
			return (100-fitness);
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
				// more patterns --> higher probability of a pattern with a higher fitness --> 100% will be reached faster
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
			}
			return mutants;
		}
}
