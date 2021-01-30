package geneticAlgorithm2;

import java.util.*;

import org.jfugue.pattern.Pattern;
import org.jfugue.theory.Note;
public class Population {

	/**
	 * Population = List of the Lists that hold the numbers that will make up a pattern = List of Patterns
	 */
	public ArrayList<Individual> patterns = new ArrayList<Individual>();
	
	private int patternLength;
	public int getPatternLength() {
		return patternLength;
	}
	public void setPatternLength(int patternLength) {
		this.patternLength = patternLength;
	}
	private double maxFitness;
	public double getMaxFitness() {
		return this.maxFitness;
	}
	public void setMaxFitness(double maxFitness) {
		this.maxFitness = maxFitness;
	}
	private Boolean addBestFromLastRound = true;
	public Boolean getAddBestFromLastRound() {
		return addBestFromLastRound;
	}
	public void setAddBestFromLastRound(Boolean addBestFromLastRound) {
		this.addBestFromLastRound = addBestFromLastRound;
	}
	public Population(int patternlength) {
		setPatternLength(patternlength);
		this.randomize();
	}
	public Population() {
		setPatternLength(8);
		this.randomize();
	}
	public void resetNoteDurations() {
		for(int j = 0; j < patterns.size(); j++) {
			for(int i = 0; i < getPatternLength(); i++) {
				patterns.get(j).notes.get(i).setDuration(0.125);
			}
		}
	}

	void randomize() {
		patterns.clear();
		for(int i = 0; i < 20; i++) {
			Individual pattern = new Individual();
			Random ran = new Random();
			for(int j = 0; j < getPatternLength(); j++) {
				int n = ran.nextInt(128);
				Note no = new Note(n);
				no.setDuration(0.125);
				pattern.notes.add(no);
			}
			System.out.println(pattern.getDuration());
			patterns.add(pattern);
		}
	}
	
	double[] _saverDurations = new double[8];
	
	Pattern evolve(ArrayList<Critic> criticList) {
		//The process starts with the Selection because the first generation is random;
		//it is not supposed to be mutated already when the first fittest pattern is chosen.
		//That is why the Selection comes before the Crossover/Mutation process.
		
		if(!getAddBestFromLastRound()) {
			//The new population is generated via crossover & mutation at the end of each round.
			// Thus, if addBestFromLastRound is set to false, the effect would take one round to late
			// if the added ex-parent1 was not removed here. It needs to be replaced so the number
			// of population members stays the same.
			patterns.remove(patterns.size()-1);
			Individual replacer = new Individual();
			replacer.randomize();
			patterns.add(replacer);
		}
		
		//Selection
		
		//The extra variable "save" is necessary because the "parent1" is used and changes before
		//its value needs to be added to the next generation.
		Individual parent1 = getFittest(criticList, patterns);
		Individual save = new Individual();
		for(int j = 0; j < parent1.notes.size(); j++) {
			Note n = new Note();
			n.setValue(parent1.notes.get(j).getValue());
			n.setDuration(parent1.notes.get(j).getDuration());
			n.setRest(parent1.notes.get(j).isRest());
			save.notes.add(n);
		}
		
		System.out.println("Fittest: " + parent1.translateToPattern().toString());
		System.out.println("Fitness: " + parent1.getFitness());
		setMaxFitness(parent1.getFitness());
		System.out.println("\n");
		Pattern parent1pattern = parent1.translateToPattern();
		//CREATE THE NEXT POPULATION BY CROSSOVER & MUTATION
		//2. get the 2nd fittest
		patterns.remove(parent1);
		Individual parent2 = getFittest(criticList, patterns);
		patterns.add(parent1);
		
		if(!(criticList.size()==1 && criticList.get(0) instanceof FindMelody)) {//or sbs
			this.crossover(parent1, parent2);
		}
		for(Critic fitn : criticList) {
			ArrayList<Individual> temp = (fitn.mutate(parent1, patterns));
			patterns.clear();
			patterns.addAll(temp);
		}
		@SuppressWarnings("unchecked")
		ArrayList<Individual> temp2 = (ArrayList<Individual>) correctNoteDurations(parent1, patterns).clone();
		patterns.clear();
		patterns.addAll(temp2);
		//if the UserParameter addBestFromLastRound is true,
		//the fittest pattern moves on to the next round so the fitness can't sink
		// (otherwise the next round only has the patterns that spawned from the fittest & 2nd fittest pattern in Crossover
		// and then underwent Mutation)
		if(getAddBestFromLastRound()) {
			patterns.remove(patterns.size()-1);
			patterns.add(save);
		}
		for(Individual in : patterns) {
			System.out.println(in.translateToPattern());
		}
		System.out.println("\n");

		return parent1pattern;
	}
	private ArrayList<Individual> correctNoteDurations(Individual parent, ArrayList<Individual> pat) {
		int size = pat.size();
		Random ran = new Random();
		double barLength = 1;
		for(int i = 0; i < size; i++) {
			if(!pat.get(i).notes.equals(parent.notes)) {
				while(pat.get(i).getDuration() != barLength) {
					int j = ran.nextInt(pat.get(0).notes.size());
					
					double dura = pat.get(i).notes.get(j).getDuration();
					if(pat.get(i).getDuration() < barLength) {
						if(dura <= 0.25) {
							//It does not work to set the new duration directly in a line like this:
							//pat.get(i).notes.get(j).setDuration(dura+0.0625)
							//So a workaround like the following is needed. This comes up at multiple places
							//throughout the code in different classes.
							//Something that is merely accessed by reference can't be changed directly.
							Note note = new Note();
							note.setValue(pat.get(i).notes.get(j).getValue());
							note.setDuration(dura+0.0625);
							pat.get(i).notes.set(j, note);
						}
					} else if(pat.get(i).getDuration() > barLength) {
						if(dura >= 0.125) {
							Note note = new Note();
							note.setValue(pat.get(i).notes.get(j).getValue());
							note.setDuration(dura-0.0625);
							pat.get(i).notes.set(j, note);
						}
					}
				}
			}
			
		}
		return pat;
	}
	private void crossover(Individual p1, Individual p2){
		//create a list with offspring of the 2 fittest individuals
		int size = patterns.size();
		patterns.clear();
		for(int i = 0; i < size; i++) {
			Individual child = randomCrossover(p1, p2);
			patterns.add(child);
		}
	}
	private Individual randomCrossover(Individual p1, Individual p2) {
		Individual child = new Individual();
		Random ran = new Random();
		for(int j = 0; j < p1.notes.size(); j++) {
			int rand = ran.nextInt(100);
			Note no = new Note();
			if(rand < 50) {
				no = p1.notes.get(ran.nextInt(p1.notes.size()));
			} else {
				no = p2.notes.get(ran.nextInt(p2.notes.size()));
			}
			
			child.notes.add(no);
		}
		int rests = 0;
		for(Note n : child.notes) {
			if(n.isRest()) {
				rests++;
			}
		}
		if(rests > child.notes.size()/4) {
			for(int i = 0; i < child.notes.size(); i++) {
				if(rests > child.notes.size()/4 && child.notes.get(i).isRest()) {
					Note note = new Note(ran.nextInt(128));
					note.setDuration(child.notes.get(i).getDuration());
					child.notes.set(i, note);
					rests--;
				}
			}
		}
		return child;
	}
	
	private Individual best;
	Individual getFittest(ArrayList<Critic> fitnessList, ArrayList<Individual> patternsInternal) {
		Individual fittest = new Individual();
		ArrayList<Individual> fittestBunch = new ArrayList<Individual>();
		//find the fittest one
		for(Individual i : patternsInternal) {
			i.calculateFitness(fitnessList);
			if(fittest.getFitness() <= i.getFitness()) {
				fittest = i;
			}
		}
		//put all with the same highest fitness in a list
		for(Individual i : patternsInternal) {
			if(i.getFitness() == fittest.getFitness()) {
				fittestBunch.add(i);
			}
		}
		//return a random one of the ones with the same highest fitness
		Random ran = new Random();
		best = fittestBunch.get(ran.nextInt(fittestBunch.size()));
		return best;
	}

	org.jfugue.pattern.Pattern translateToLongPattern() {
		Pattern longPattern = new Pattern();
		for(Individual pattern : patterns) {
			longPattern.add(pattern.translateToPattern());
		}
		return longPattern;
	}

	public Individual getBest() {
		return best;
	}

	@SuppressWarnings("unchecked")
	public void setBest(Individual bestOne) {
		this.best = new Individual();
		this.best.notes = (ArrayList<Note>) bestOne.notes.clone();
	}

}