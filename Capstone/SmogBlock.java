/*
Van N
2024-05-31
Mr Guglielmi
Smog block in the planet composition, initiates a minigame
*/
public class SmogBlock extends Block {
   public SmogBlock(String n, int v) {
      super(n, v);
      this.type = "smog";
      this.property = "Missing QTEs reduces the population of creatures.";
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