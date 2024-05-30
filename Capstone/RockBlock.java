public class RockBlock extends Block {
   public RockBlock(String n, int v) {
      super(n, v);
      this.type = "rock";
      this.property = "Clicking Rock QTEs produces more rock. Might produce soil if volume > 1500. Missing QTEs produces lava.";
   }
   public void doQTE() {
      if (this.volume < 1500 + random.nextInt(501)) {
         planet.addBlock(new RockBlock(this.getName(), random.nextInt(200)));
      } else {
         int num = 100+random.nextInt(200);
         planet.addBlock(new SoilBlock("Soil", num));
         planet.addBlock(new RockBlock(this.getName(), -num));
      }
   }
   public void doFailedQTE() {
         int num = 50+random.nextInt(100);
         planet.addBlock(new LavaBlock("Lava", num));
         planet.addBlock(new RockBlock(this.getName(), -num));
   }
}