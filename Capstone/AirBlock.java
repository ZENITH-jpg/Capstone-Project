public class AirBlock extends Block {
   public AirBlock(String n, int v) {
      super(n, v);
      this.type = "air";
      this.property = "Clicking Air QTEs turns dirty blocks clean";
   }
   public void doQTE() {
      // clean air or water
   }
   public void doFailedQTE() {
   }
}