public class WaterBlock extends Block {
   public WaterBlock(String n, int v) {
      super(n, v);
      this.type = "water";
      this.property = "Regulates heat and turns into ice.";
   }
   public void doQTE() {
      if (this.volume < 2000 + random.nextInt(501)) {
         planet.addBlock(new WaterBlock(this.getName(),random.nextInt(300)));
      } else {
         int num = 100+random.nextInt(200);
         planet.addBlock(new IceBlock("Ice", random.nextInt(200)));
         planet.addBlock(new WaterBlock(this.getName(), -num));
      }
   }
   public void doFailedQTE() {
   }
}