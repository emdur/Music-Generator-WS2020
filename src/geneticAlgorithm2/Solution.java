package geneticAlgorithm2;

import java.util.ArrayList;

import org.jfugue.pattern.Pattern;
import org.jfugue.realtime.RealtimePlayer;

public class Solution {
	//The solution holds a population in it that can evolve.
	//The method evaluateOnce lets the population evolve by 1 generation
	// and plays the pattern with the realtimeplayer it gets as a parameter from the GUI class.

	private Population population;
	private int genLimit;
	
	//The tracktable is not used at the moment, but all the played patterns can potentially be stored there.
	//public TrackTable trackTable;
	

	public Population getPopulation() {
		return this.population;
	}

	public void setPopulation(Population population) {
		this.population = population;
	}

	public Solution(int generationLimit) {
		this.genLimit = generationLimit;
		//this.trackTable = new TrackTable(generationLimit, 1.0d);
		setPopulation(new Population());
	}
	public Pattern evaluateOnce(RealtimePlayer player, int generationIndex, int bpm, ArrayList<Critic> criticList) {
		Pattern pattern = null;
		if(generationIndex <= genLimit) {
			System.out.println("Generation: " + generationIndex);
			pattern = population.evolve(criticList);
			pattern.setInstrument(12);
			//Instruments: 9:Glockenspiel; 12: Marimba, 96:Rain, 102 Synth "Echoes", 123 Bird Tweet, 127 Gun Shot, 108 Kalimba, 110 Fiddle
			//116 Taiko Drum, 118 Synth Drum, 124 Telephone Ring
			pattern.setTempo(bpm);
			//trackTable.put(0, generationIndex-1, pattern);
			player.play(pattern);
		
		}
		return pattern;
	}
	public void resetDuration() {
		this.population.resetNoteDurations();
	}

}