public class SoilBlock extends Block {
   public SoilBlock(String n, int v) {
      super(n, v);
      this.type = "soil";
      this.property = "Clicking Soil QTEs creates random creatures and humans";
   }
   public void doQTE() {
      // create a random creature, max of 5 creatures
      if (planet.getCreatures().size() < 5) {
         planet.createCreature();
      }
      int num = 1 + random.nextInt(planet.getCreatures().size());
      // for num times increase a random creature's population
      while (num > 0) {
         int index = random.nextInt(planet.getCreatures().size());
         Creature c = planet.getCreatures().get(index);
         c.addPopulation(c.getPopulation()/2 + random.nextInt(c.getPopulation()/2));
         num--;
      }
      // if humans unlocked from objective, increase human population
      planet.addHumans(planet.getHumans()/3 + random.nextInt(planet.getHumans()/3));
   }
   public void doFailedQTE() {
      // just produces soil
      planet.addBlock(new SoilBlock(this.name, random.nextInt(500)));
   }
}