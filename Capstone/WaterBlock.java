/*
Van N
2024-05-31
Mr Guglielmi
Water block in the planet composition
*/
public class WaterBlock extends Block {
   public WaterBlock(String n, int v) {
      super(n, v);
      this.type = "water";
      this.property = "Clicking Water QTEs produces more water. Will turn into ice if volume > 1000. Max volume = 1600";
   }
   public void doQTE() {
      if (this.volume < 1000) {
         planet.addBlock(new WaterBlock(this.getName(),100+random.nextInt(200)));
      } else if (planet.findBlock("Ice") == -1 || planet.getBlockWithName("Ice").getVolume() <= 800) {
         // make ice if ice is < 800 volume
         int num = 200+random.nextInt(200);
         planet.addBlock(new IceBlock("Ice", num));
         planet.addBlock(new WaterBlock(this.getName(), -num));
      } else if (this.volume < 1600) {
         // up to 1600 water if there's too much ice
         planet.addBlock(new WaterBlock(this.getName(),100+random.nextInt(200)));
      }
   }
   public void doFailedQTE() {
   }
}