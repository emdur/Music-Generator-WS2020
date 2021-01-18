package geneticAlgorithm2;

import java.util.ArrayList;
import java.util.Random;

import org.jfugue.theory.Note;

public class ReverseDurationCritic implements Fitness {
	@Override
	public double getCalculatedFitness(Individual individual) {
		//calculate the fitness of an Individual based on the duration of the notes.
		//criteria: fitness is higher if ...
		double fitness = 0;
		double partFit = 100/(double)individual.notes.size();
		for(int i = 0; i < individual.notes.size(); i++) {
			double dura = individual.notes.get(i).getDuration();
			double duraBefore = 0;
			double duraAfter = 0;
			double duraBeforeBefore = 0;
			double duraAfterAfter = 0;
			if(i-1 >= 0) {
				duraBefore = individual.notes.get(i-1).getDuration();
			}
			if(i+1 < individual.notes.size()) {
				duraAfter = individual.notes.get(i+1).getDuration();
			}
			if(i-2 >= 0) {
				duraBeforeBefore = individual.notes.get(i-2).getDuration();
			}
			if(i+2 < individual.notes.size()) {
				duraAfterAfter = individual.notes.get(i+2).getDuration();
			}
			if(dura == 0.5) {
				if(duraBefore != dura && duraAfter != dura) {
					fitness += partFit;
				}
			}
			if(dura == 0.25) {
				if(duraBefore == dura && duraAfter != dura || duraBefore != dura && duraAfter == dura) {
					fitness += partFit;
				}
			}
			if(dura == 0.125 || dura == 0.0625) {
				if(duraBefore == dura && duraAfter != dura || duraBefore != dura && duraAfter == dura
						|| duraBefore == dura && duraBeforeBefore == dura && duraAfter != dura
						|| duraBefore != dura && duraAfterAfter == dura && duraAfter == dura) {
					fitness += partFit;
				}
			}
		}
		
		return (100-fitness);
	}

	double[] possibleDurations = new double[] {
			0.5, 0.25, 0.125, 0.0625
	};
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Individual> mutate(Individual parent, ArrayList<Individual> individuals) {
		//change the durations of 2 notes per individual
		ArrayList<Individual> mutants = (ArrayList<Individual>) individuals.clone();
		
		Random ran = new Random();
		int note1i = 0;
		int note2i = 0;
		for(int j = 0; j < mutants.size(); j++) {
			if(!mutants.get(j).notes.equals(parent.notes)) {
				note1i = ran.nextInt(parent.notes.size());
				note2i = ran.nextInt(parent.notes.size());
				Note note1 = mutants.get(j).notes.get(note1i);
				Note note2 = mutants.get(j).notes.get(note2i);
				double duraBoth = note1.getDuration()+note1.getDuration();
				double dura1 = 1;
				while(dura1 >= duraBoth) {
					dura1 = possibleDurations[ran.nextInt(possibleDurations.length)];
				}
				note1.setDuration(dura1);
				note2.setDuration(duraBoth - dura1);
				mutants.get(j).notes.set(note1i, note1);
				mutants.get(j).notes.set(note1i, note2);
			}
			
		}
		return correctNoteDurations(parent, mutants);
	}
	private ArrayList<Individual> correctNoteDurations(Individual parent, ArrayList<Individual> patterns) {
		int size = patterns.size();
		Random ran = new Random();
		double barLength = 1;
		for(int i = 0; i < size; i++) {
			if(!patterns.get(i).notes.equals(parent.notes)) {
				while(patterns.get(i).getDuration() != barLength) {
					int j = ran.nextInt(patterns.get(0).notes.size());
					
					double dura = patterns.get(i).notes.get(j).getDuration();
					if(patterns.get(i).getDuration() < barLength) {
						if(dura <= 0.25) {
							patterns.get(i).notes.get(j).setDuration(dura+0.0625);
						}
					} else if(patterns.get(i).getDuration() > barLength) {
						if(dura >= 0.125) {
							patterns.get(i).notes.get(j).setDuration(dura-0.0625);
						}
					}
				}
			}
			
		}
		return patterns;
	}
}
