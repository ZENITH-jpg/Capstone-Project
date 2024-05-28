import java.util.Random;

public class Creature {
   private static Random random = new Random();
   private static String[] possibleSpecies = new String[] {"lemurs"};
   private String species;
   private int population; // no setter, only adder
   
   public Creature (int speciesIndex, int p) {
      species = possibleSpecies[speciesIndex];
      population = p;
   }
   
   public static void randomizeSpecies () {
      for (int i = possibleSpecies.length - 1; i > 0; i--) {
        int index = random.nextInt(i + 1);
        String temp = possibleSpecies[index];
        possibleSpecies[index] = possibleSpecies[i];
        possibleSpecies[i] = temp;
      }
   }
   
   public String getSpecies() {
      return species;
   }
   
   public int getPopulation() {
      return population;
   }
   
   public void addPopulation(int p) {
      population += p;
      if (population < 0)
         population = 0;
   }
}