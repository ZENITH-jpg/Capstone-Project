public class WaterBlock extends Block {
   public WaterBlock(String n, int v) {
      super(n, v);
      this.type = "water";
      this.property = "Clicking Water QTEs turns dirty water clean and produces water. Might produce ice if volume > 500.";
   }
   public void doQTE() {
      if (this.volume < 500 + random.nextInt(501)) {
         planet.addBlock(new WaterBlock(this.getName(),random.nextInt(200)));
      } else {
         int num = 100+random.nextInt(200);
         planet.addBlock(new IceBlock("Ice", random.nextInt(200)));
         planet.addBlock(new WaterBlock(this.getName(), -num));
      }
   }
   public void doFailedQTE() {
   }
}