package geneticAlgorithm2;

import java.util.ArrayList;

public interface Critic {

	double getCalculatedFitness(Individual individual);

	java.util.ArrayList<Individual> mutate(Individual parent, ArrayList<Individual> individuals);

}