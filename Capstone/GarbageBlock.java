public class GarbageBlock extends Block {
   public GarbageBlock(String n, int v) {
      super(n, v);
      this.type = "garbage";
      this.property = "Missing a QTE can reduce the population of creatures. Clicking a QTE will produce garbage.";
   }
   public void doQTE() {
   }
   public void doFailedQTE() {
      // reduce population of a random number of creatures
      if (planet.getCreatures().size() > 0) {
         int num = 1 + random.nextInt(planet.getCreatures().size());
         // for num times decrease a random creature's population
         while (num > 0) {
            int index = random.nextInt(planet.getCreatures().size());
            Creature c = planet.getCreatures().get(index);
            c.addPopulation(-random.nextInt(c.getPopulation()/2));
            num--;
         }
      }
   }
}