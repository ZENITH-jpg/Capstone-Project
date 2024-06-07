/*
Van N
2024-05-31
Mr Guglielmi
Rock block in the planet composition
*/
public class RockBlock extends Block {
   public RockBlock(String n, int v) {
      super(n, v);
      this.type = "rock";
      this.property = "Clicking Rock QTEs produces more rock. Will turn into soil if volume > 2000. Missing QTEs produces lava.";
   }
   public void doQTE() {
      if (this.volume <= 2000) {
         planet.addBlock(new RockBlock(this.getName(), 150+random.nextInt(200)));
      }
      if (this.volume > 2000) {
         int num = 200+random.nextInt(200);
         planet.addBlock(new SoilBlock("Soil", num));
         planet.addBlock(new RockBlock(this.getName(), -num));
      }
   }
   public void doFailedQTE() {
      int num = 150+random.nextInt(100);
      planet.addBlock(new LavaBlock("Lava", num));
      if (planet.getBlockWithName(this.getName()).getVolume() > num)
         planet.addBlock(new RockBlock(this.getName(), -num));
   }
}