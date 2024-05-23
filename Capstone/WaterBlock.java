public class WaterBlock extends Block {
   public WaterBlock(String n, int v) {
      super(n, v);
      this.type = "water";
      this.property = "Regulates heat and turns into ice.";
   }
   public void doProperty() {
   }
   public void doQTE() {
      if (this.volume < 1000 + random.nextInt(501)) {
         planet.addBlock(new WaterBlock(this.getName(),random.nextInt(200)));
      } else {
         planet.addBlock(new WaterBlock(this.getName(),random.nextInt(200)));
         // planet.addBlock(new IceBlock("Ice", random.nextInt(200)));
      }
   }
   public void doFailedQTE() {
         planet.addBlock(new LavaBlock("Lava", random.nextInt(200)));
   }
}