public class LavaBlock extends Block {
   public LavaBlock(String n, int v) {
      super(n, v);
      this.type = "lava";
      this.property = "Failing Lava QTEs warms up the planet";
   }
   public void doQTE() {
   }
   public void doFailedQTE() {
      planet.addTemp(15+random.nextInt(10));
   }
}