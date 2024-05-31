public class LavaBlock extends Block {
   public LavaBlock(String n, int v) {
      super(n, v);
      this.type = "lava";
      this.property = "Missing Lava QTEs warms up the planet";
   }
   public void doQTE() {
   }
   public void doFailedQTE() {
      planet.addTemp(25+random.nextInt(40));
   }
}