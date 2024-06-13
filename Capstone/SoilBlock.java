/*
Van N
2024-05-31
Mr Guglielmi
Soil block in the planet composition
*/

/**
 * Soil block in the planet's composition
 *
 * @author Van N
 * @version 1.0
 */
public class SoilBlock extends Block {
   /**
    * Constructor for the class, creates block using name and volume, sets the description
    *
    * @param n the name of the block
    * @param v the volume the block holds
    */
   public SoilBlock(String n, int v) {
      super(n, v);
      this.type = "soil";
      this.property = "Clicking Soil QTEs creates random creatures and humans. Up to " + Planet.maxCreatures + " creatures can be created. Can make species extinct if you miss.";
   }

   /**
    * Increase the population of a species, creates one if there is not yet the maximum, increases humans
    */
   public void doQTE() {
      // create a random creature, unless at max creatures
      if (planet.getCreatures().size() < Planet.maxCreatures) {
         planet.createCreature();
      }
      int num = 1 + random.nextInt(planet.getCreatures().size());
      // for num times increase a random creature's population
      while (num > 0) {
         int index = random.nextInt(planet.getCreatures().size());
         Creature c = planet.getCreatures().get(index);
         if (c.getPopulation() > 0) {
            c.addPopulation(c.getPopulation() / 3 + random.nextInt(c.getPopulation() / 2));
            num--;
         }
      }
      // if humans unlocked from objective, increase human population
      if (planet.getHumans() > 0)
         planet.addHumans(1000 + planet.getHumans() / 3 + random.nextInt(planet.getHumans() / 2));
   }

   /**
    * Makes a species extinct
    */
   public void doFailedQTE() {
      int num = 1;
      while (num > 0 && !planet.getCreatures().isEmpty()) {
         int index = random.nextInt(planet.getCreatures().size());
         Creature c = planet.getCreatures().get(index);
         if (c.getPopulation() > 0) {
            c.addPopulation(-c.getPopulation());
            num--;
         }
      }
   }
}