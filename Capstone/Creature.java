/*
Van N
2024-05-31
Mr Guglielmi
Animals that exist in the game which will either appear or die
*/

import java.util.Random;

/**
 * Creates planets and animals that exist in the game which will grow in population or go extinct
 *
 * @author Van N
 * @version 1.0
 */
public class Creature {
   /**
    * random instance for creature, makes a random number of certain species
    */
   private static Random random = new Random();
   /**
    * Possible animals that exist within the game
    */
   private static String[] possibleSpecies = new String[]{"Lemurs", "Moquitos", "Baboons", "Baobabs", "Octopi", "Platypi", "Bats", "Iguanas", "Conifers", "Ferns"};
   /**
    * String containing the animal's name
    */
   private String species;
   /**
    * The population of the species of animal
    */
   private int population; // no setter, only adder

   /**
    * Constructor of Creature
    *
    * @param speciesIndex index of species name corresponding to the current number of species
    * @param p            initial population of the creature
    */
   public Creature(int speciesIndex, int p) {
      species = possibleSpecies[speciesIndex];
      population = p;
   }

   /**
    * Randomizes the names of species at the start of a new game
    */
   public static void randomizeSpecies() {
      for (int i = possibleSpecies.length - 1; i > 0; i--) {
         int index = random.nextInt(i + 1);
         String temp = possibleSpecies[index];
         possibleSpecies[index] = possibleSpecies[i];
         possibleSpecies[i] = temp;
      }
   }

   /**
    * Gets the species name of creature
    *
    * @return species
    */
   public String getSpecies() {
      return species;
   }

   /**
    * Gets the creature population
    *
    * @return population
    */
   public int getPopulation() {
      return population;
   }

   /**
    * Adds to the creature population
    *
    * @param p amount to add
    */
   public void addPopulation(int p) {
      population += p;
      if (population < 0)
         population = 0;
   }
}