/*
Van N
2024-05-31
Mr Guglielmi
Water block in the planet composition
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
   public void doQTE() {
      if (this.volume <= 1000) {
         planet.addBlock(new WaterBlock(this.getName(),100+random.nextInt(200)));
      }
      if (this.volume > 1000 && (planet.findBlock("Ice") == -1 || planet.getBlockWithName("Ice").getVolume() <= 800)) {
         // make ice if ice is < 800 volume
         int num = 200+random.nextInt(200);
         planet.addBlock(new IceBlock("Ice", num));
         this.addVolume(-num);
      }
   }
   public void doFailedQTE() {

   }
}