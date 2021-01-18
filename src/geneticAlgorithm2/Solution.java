package geneticAlgorithm2;

import java.util.ArrayList;

import org.jfugue.pattern.Pattern;
import org.jfugue.pattern.TrackTable;
import org.jfugue.realtime.RealtimePlayer;

public class Solution {

	private Population population;
	private int genLimit;
	public TrackTable trackTable;
	

	public Population getPopulation() {
		return this.population;
	}

	public void setPopulation(Population population) {
		this.population = population;
	}

	public Solution(int generationLimit) {
		this.genLimit = generationLimit;
		this.trackTable = new TrackTable(generationLimit, 1.0d);
		setPopulation(new Population());
	}
	public Pattern evaluateOnce(RealtimePlayer player, int generationIndex, int bpm, ArrayList<Fitness> fitnessList, Boolean addBestFromLastRound) {
		Pattern pattern = null;
		if(generationIndex <= genLimit) {
			System.out.println("Generation: " + generationIndex);
			pattern = population.evolve(fitnessList, addBestFromLastRound);
			pattern.setInstrument(9);
			//9:Glockenspiel; 96:Rain, 102 Synth "Echoes", 123 Bird Tweet, 127 Gun Shot, 108 Kalimba, 110 Fiddle
			//116 Taiko Drum, 118 Synth Drum, 124 Telephone Ring
			pattern.setTempo(bpm);
			//double pl = population.getBest().getDuration(); //returns the patternduration (2 = 2 bars)
			trackTable.put(0, generationIndex-1, pattern);
			player.play(pattern);
	    	System.out.println(player.getCurrentTime());
		
		}
		return pattern;
	}

}