Music Generator for Music in Digital Media Module WiSe 2020/21.

Music is generated based on a genetic algorithm.
The Java library JFugue was used for this project.


***

To test the application (Java must be installed on your computer for it to run):

1. Select "Muvolution.jar" from the list of files above.
2. Click "Download".
3. Double click the file you just downloaded.
4. Enjoy! :-)

***

Guide to using the application:

1. Type into the textfield for how many generations you want to let the population evolve.
2. Enable at least one of the Toggle Buttons. (Each of them represents a critic. The critics are explained in the next paragraph.)
3. Press "Play".

While the music is playing and the population is evolving, you can add and remove the critics as you like -
But at least one of them needs to be pressed down at all times while the music is playing.
If you forget and it stops, simply choose at least one critic and then press "Play" again to restart with a new population.

If you feel like anything does not work, you can always choose one critic, press the "Reset" and then the "Play" Button and it should work again.

To change the number of how many generations the next evolution process will last, type a new number in the textfield and press "Play".
If you want to stop, you can hit "Pause", and after that (without changing the number in the textfield) "Play" to resume with the current population or (optional: type new number in textfield) "Reset" and then "Play" to restart with a fresh population.


***

What the critics in "Muvolution" do:

Each critic has a hand in calculating the fitness of the melody of each individual in the population.
Each critic also has a mutation function: Which critics you choose affects how the melodies mutate.

Criteria of the critics:

ClosenessOfNotes: How close are the notes in a pattern to each other? --> Notes are close to each other = high fitness
CFriend: How many notes in the pattern are a „C“? --> Many notes in a pattern have the value C or close to C = high fitness
DurationCritic: Note Duration: e.g. 3 16th notes next to each other
Children‘s Melody: High fitness for a pattern if: Notes in octave 4 and 5; the values of 2 notes next to each other are less than 4 half tone steps apart; only notes in the key of C Major

The "Reverse"-Critics give high fitness by the opposite criteria to the ones they are named after.

Tipp: Try it for good effect with "ClosenessOfNotes" as the only critic, let the fitness reach 100 or close to 100 and then click "ReverseClosenessOfNotes".