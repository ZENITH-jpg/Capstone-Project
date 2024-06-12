/*
Van N
2024-05-31
Mr Guglielmi
Water block in the planet composition
*/
/**
Water block in the planet's composition
@author Van N
@version 1.0
*/
public class WaterBlock extends Block {
   /**
    * Constructor for the class, creates block using name and volume, sets the description
    * @param n the name of the block
    * @param v the volume the block holds
    */
   public WaterBlock(String n, int v) {
      super(n, v);
      this.type = "water";
      this.property = "Clicking Water QTEs produces more water. Will turn into ice if volume > 1000.";
   }

   /**
    * Add more water to the planet, if too much water exists, make ice
    */
   public void doQTE() {
      if (this.volume <= 1000) {
         planet.addBlock(new WaterBlock(this.getName(),100+random.nextInt(200)));
      }
      if (this.volume > 1000) {
         int num = 200+random.nextInt(200);
         planet.addBlock(new IceBlock("Ice", num));
         this.addVolume(-num);
         // remove the chance of water qtes if there's too much ice
         if (planet.getBlockWithName("Ice").getVolume() > 800)
            game.getQTEPanel().removeChance("Clean water");
      }
   }
   public void doFailedQTE() {

   }
}