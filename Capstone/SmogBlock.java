/*
Van N
2024-05-31
Mr Guglielmi
Smog block in the planet composition, initiates a minigame
*/
/**
Smog block in the planet's composition
@author Van N
@version 1.0
*/
public class SmogBlock extends Block {
   /**
    * Constructor for the class, creates block using name and volume, sets the description
    * @param n the name of the block
    * @param v the volume the block holds
    */
   public SmogBlock(String n, int v) {
      super(n, v);
      this.type = "smog";
      this.property = "A collection of carbon emissions and natural gasses that passively warms planet. Missing QTEs reduces the population of creatures and increase smog. " +
            "Activates a minigame when clicked. Failing minigame has same consequences.";
   }
   public void doQTE() {
      game.startRandomMinigame();
   }
   public void doFailedQTE() {
      // reduce population of a random number of creatures
      if (planet.getCreatures().size() > 0) {
         int num = 5 + random.nextInt(planet.getCreatures().size());
         // for num times decrease a random creature's population
         while (num > 0) {
            int index = random.nextInt(planet.getCreatures().size());
            Creature c = planet.getCreatures().get(index);
            if(c.getPopulation()>0){
               c.addPopulation(-c.getPopulation()/3-random.nextInt(c.getPopulation()/2));
            }
            num--;
         }
      }
      planet.addBlock(new SmogBlock("Smog", 50+random.nextInt(30))); // add more smog
   }
}