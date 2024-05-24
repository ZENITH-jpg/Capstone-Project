public class RockBlock extends Block {
   public RockBlock(String n, int v) {
      super(n, v);
      this.type = "rock";
      this.property = "Clicking Rock QTEs produces more rock or soil. Missing QTEs produces lava.";
   }
   public void doProperty() {
   }
   public void doQTE() {
      if (this.volume < 1000 + random.nextInt(501)) {
         planet.addBlock(new WaterBlock("Clean water", random.nextInt(200)));
      } else {
         int num = random.nextInt(200);
         planet.addBlock(new SoilBlock("Soil", num));
         planet.addBlock(new RockBlock(this.getName(), -num));
      }
   }
   public void doFailedQTE() {
         int num = random.nextInt(100);
         planet.addBlock(new LavaBlock("Lava", num));
         planet.addBlock(new RockBlock(this.getName(), -num));
   }
}