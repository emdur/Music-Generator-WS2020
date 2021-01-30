# Music Generator for Music in Digital Media Module WiSe 2020/21.

Music is generated based on a genetic algorithm.
The Java library JFugue was used for this project.


***

## To test the application (Java must be installed on your computer for it to run):

1. Select "Muvolution.jar" from the list of files above.
2. Click "Download".
3. Double click the file you just downloaded.
4. Enjoy! :-)

***

## Guide to using the application:

***1. Type into the textfield for how many generations you want to let the population evolve.***
***2. Enable at least one of the Toggle Buttons, e.g. "CFriend". (Each of them represents a critic. The critics are explained in detail in the next paragraph.)***
***3. Press "Play".***

While the music is playing and the population is evolving, you can add and remove the critics as you like -
But at least one of them needs to be pressed down at all times while the music is playing.
If you forget and it stops, simply choose at least one critic and then press "Play" again to restart with a new population.

If you ever feel like anything does not work, you can always choose one critic, press the "Reset" and the "Play" Button and it should work again.

As soon as the number of generations that the population was initiated with has been reached, the music will stop. If you press "Play" now, a fresh population will be generated (the old one won't resume).

***

## What the critics in "Muvolution" do:

Each critic has a hand in calculating the fitness of the melody of each individual in the population.
Each critic also has a mutation function: Which critics you choose affects how the melodies mutate.

### Criteria of the critics:

**ClosenessOfNotes:** How close are the notes in a pattern to each other? --> Notes are close to each other = high fitness
**CFriend:** How many notes in the pattern are a „C“? --> Many notes in a pattern have the value C or close to C = high fitness
**DurationCritic:** Note Duration: e.g. 3 16th notes next to each other
**Children‘s Melody:** High fitness for a pattern if: Notes in octave 4 and 5; the values of 2 notes next to each other are less than 4 half tone steps apart; only notes in the key of C Major

The **"Reverse"**-Critics give high fitness by the opposite criteria to the ones they are named after.

*Tipp: Try it for good effect with "ClosenessOfNotes", let the fitness reach 100 or close to 100 and then click "ReverseClosenessOfNotes".*

***

## Quick Guide to the Java Code / Classes:

Test.java --> Entrance class for the .jar file, creates an instance of the Graphical User Interface

GUI.java --> This is where the User Interface is defined, as is the RealtimePlayer and the "Solution" that the User interacts with

Solution.java --> This class has an instance of "Population" in it, so it has the actual population that evolves and a function called "evaluateOnce" that tells the population to evolve and makes sure the returned pattern is played correctly.

Population.java --> This class has a list of "Individuals" and some functions needed for what may be the most important function: "evolve".

Individual.java --> Has a list of notes and some additional properties like the "fitness" and functions like the function that calculates its fitness with a list of critics as a parameter.

Interface Critic.java --> Has a "mutate" and a "calculateFitness" function. What the classes that implement this interface do is explained in the paragraph above.