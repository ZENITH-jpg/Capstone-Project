public class WaterBlock extends Block {
   public WaterBlock(String n, int v) {
      super(n, v);
      this.type = "water";
      this.property = "Clicking Water QTEs produces more water. Will turn into ice if volume > 1000.";
   }
   public void doQTE() {
      if (this.volume < 1000) {
         planet.addBlock(new WaterBlock(this.getName(),200+random.nextInt(200)));
      } else {
         int num = 200+random.nextInt(200);
         planet.addBlock(new IceBlock("Ice", num));
         planet.addBlock(new WaterBlock(this.getName(), -num));
      }
   }
   public void doFailedQTE() {
   }
}