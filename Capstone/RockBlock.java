/*
Van N
2024-05-31
Mr Guglielmi
Rock block in the planet composition
*/
/**
Rock block in the planet's composition
@author Van N
@version 1.0
*/
public class RockBlock extends Block {
   /**
    * Constructor for the class, creates block using name and volume, sets the description
    * @param n the name of the block
    * @param v the volume the block holds
    */
   public RockBlock(String n, int v) {
      super(n, v);
      this.type = "rock";
      this.property = "Clicking Rock QTEs produces more rock. Will turn into soil if volume > 2000. Missing QTEs produces lava.";
   }

   /**
    * Add more rock when QTE is clicked, if there is enough rock, add soil
    */
   public void doQTE() {
      if (this.volume <= 2000) {
         planet.addBlock(new RockBlock(this.getName(), 150+random.nextInt(200)));
      }
      if (this.volume > 2000) {
         int num = 200+random.nextInt(200);
         planet.addBlock(new SoilBlock("Soil", num));
         planet.addBlock(new RockBlock(this.getName(), -num));
         // remove the chance of rock qtes when there's too much soil
         if (planet.getBlockWithName("Soil").getVolume() > 800)
            game.getQTEPanel().removeChance("Rock");
      }
   }

   /**
    * On fail, create lava and reduce rock
    */
   public void doFailedQTE() {
      if (this.volume > 350) {
         int num = 150+random.nextInt(100);
         planet.addBlock(new LavaBlock("Lava", num));
         planet.addBlock(new RockBlock(this.getName(), -num));
         planet.addTemp(random.nextInt(10));
      }
   }
}