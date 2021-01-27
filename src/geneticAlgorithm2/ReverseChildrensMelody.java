package geneticAlgorithm2;

import java.util.ArrayList;
import java.util.Random;

import org.jfugue.theory.Note;

public class ReverseChildrensMelody implements Critic {

	@Override
	public double getCalculatedFitness(Individual individual) {
		//this is the exact reverse to the ChildrensMelody Class (fitness = 100-ChildrensMelodyFitness)
		// + fitness for notes NOT in the octaves 4 or 5
		// a note must be further apart from the one after it more than 4 half tone steps
		
		double fitness = 0;
		double relativeVal = (100/(double)individual.notes.size());
		
		for(Note i : individual.notes) {
			Boolean canUpFitness = true;
			int nextIndex = individual.notes.indexOf(i)+1;
			if(nextIndex < individual.notes.size()) {
				Note n = individual.notes.get(nextIndex);
				if(i.getValue()==n.getValue() || Math.abs(i.getValue()-n.getValue()) > 4) {
					canUpFitness = false;
				}
				if (Math.abs(i.getValue()-n.getValue()) > 4 && Math.abs(i.getValue()-n.getValue()) < 12) {
					fitness += relativeVal-(Math.abs(i.getValue()-n.getValue()));
				}
			}
			if(!i.isRest() && canUpFitness) {
				for(int j = 48; j < 72; j+=2) {
					if(i.getValue() == j) {
						fitness += relativeVal;
					}
					if(j==52 || j==64 || j==59) {
						j--;
					}
				}
			}
			
		}
		
		return (100-fitness);
	}

	@Override
	public ArrayList<Individual> mutate(Individual parent, ArrayList<Individual> individuals) {
		// TODO Auto-generated method stub
		// create 3 new Notes in the value range between 48 & 72 and put them in the places of notes in the parent pattern (random index)
				@SuppressWarnings("unchecked")
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
						Note note = new Note(ran.nextInt(24)+48);
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
						Note note = new Note(ran.nextInt(24)+48);
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
						Note note = new Note(ran.nextInt(24)+48);
						note.setDuration(individuals.get(i).notes.get(noteToChange).getDuration());
						mutants.get(i).notes.set(noteToChange, note);
					}
				}
				return mutants;
	}

}
