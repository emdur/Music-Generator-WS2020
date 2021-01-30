package geneticAlgorithm2;

import java.util.ArrayList;
import java.util.Random;

import org.jfugue.pattern.Pattern;
import org.jfugue.theory.Note;

public class Individual {

	/**
	 * Individual = List of numbers that make up a Pattern = Pattern
	 */
	public java.util.ArrayList<org.jfugue.theory.Note> notes = new ArrayList<Note>();
	public double getDuration() {
		double duration = 0;
		for(Note n : notes) {
			duration += n.getDuration();
		}
		return duration;
	}
	private double fitness;

	public double getFitness() {
		return this.fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public Individual() {
		this.setFitness(0);
	}
	public void randomize() {
		Random ran = new Random();
		for(int j = 0; j < 8; j++) {
			int n = ran.nextInt(128);
			Note no = new Note(n);
			no.setDuration(0.125);
			this.notes.add(no);
		}
	}
	public void calculateFitness(ArrayList<Critic> criticList) {
		double fit = 0;
		for(Critic fi : criticList) {
			fit += fi.getCalculatedFitness(this);
		}
		this.setFitness(fit/criticList.size());
	}
	public Individual changeAllNoteDurationsToRan() {
		double[] possibleDurations = new double[] {
				0.5, 0.25, 0.125, 0.0625
		};
		Random ran = new Random();
		double maxDuration = 1;
		double total = 0;
		for(int i = 0; i < notes.size()-1; i++) {
			if(total < maxDuration) {
				notes.get(i).setDuration(possibleDurations[ran.nextInt(possibleDurations.length)]);
				total += notes.get(i).getDuration();
			}
		}
		if(maxDuration-total > 0) {
			notes.get(notes.size()-1).setDuration(maxDuration-total);
		} else {
			for(int i = 0; i < notes.size(); i++) {
				if(notes.get(i).getDuration() == 0.5) {
					notes.get(i).setDuration(0.25);
					notes.get(notes.size()-1).setDuration(0.25);
					break;
				}
			}
		}
		return this;
	}
	public Individual changeNoteDurationToRan(int i1, int i2) {
		//fix this stuff: u can't change the duration of only 1 note. find a different solution
		double[] possibleDurations = new double[] {
				0.5, 0.25, 0.125, 0.0625
		};
		Random ran = new Random();
		double maxDuration = 1;
		double total = notes.get(i1).getDuration() + notes.get(i2).getDuration();
		if(total < maxDuration) {
			notes.get(i1).setDuration(possibleDurations[ran.nextInt(possibleDurations.length)]);
		}
		if(maxDuration-total > 0) {
			notes.get(i2).setDuration(maxDuration-total);
		}
		return this;
	}

	org.jfugue.pattern.Pattern translateToPattern() {
		Pattern pattern = new Pattern();
		for(Note n : notes) {
			pattern.add(n);
		}
		return pattern;
	}

}