public class WaterBlock extends Block {
   public WaterBlock(String n, int v) {
      super(n, v);
      this.type = "water";
      this.property = "Clicking Water QTEs produces more water. Might turn into ice if volume > 1000.";
   }
   public void doQTE() {
      if (this.volume < 1000 + random.nextInt(501)) {
         planet.addBlock(new WaterBlock(this.getName(),100+random.nextInt(200)));
      } else {
         int num = 200+random.nextInt(200);
         planet.addBlock(new IceBlock("Ice", random.nextInt(200)));
         planet.addBlock(new WaterBlock(this.getName(), -num));
      }
   }
   public void doFailedQTE() {
   }
}