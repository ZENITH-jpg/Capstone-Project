public class SoilBlock extends Block {
   public SoilBlock(String n, int v) {
      super(n, v);
      this.type = "soil";
      this.property = "Clicking Soil QTEs creates random creatures and humans. Up to "+Planet.maxCreatures+" creatures can be created.";
   }
   public void doQTE() {
      if (GamePanel.creaturesCanAppear) {
         // create a random creature, unless at max creatures
         if (planet.getCreatures().size() < Planet.maxCreatures) {
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
         if (planet.getHumans() > 0)
            planet.addHumans(planet.getHumans()/3 + random.nextInt(planet.getHumans()));
      }
   }
   public void doFailedQTE() {
   }
}