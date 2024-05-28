public class Creature {
   private static String[] possibleSpecies = new String[] {"lemurs"};
   private String species;
   private int population; // no setter, only adder
   
   public Creature (String s, int p) {
      species = s;
      population = p;
   }
   
   public String getSpecies() {
      return species;
   }
   
   public int getPopulation() {
      return population;
   }
   
   public void addPopulation(int p) {
      population += p;
   }
}