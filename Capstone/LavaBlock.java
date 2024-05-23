public class LavaBlock extends Block {
   public LavaBlock(String n, int v) {
      super(n, v);
      this.type = "lava";
      this.property = "Should warm up planet but actually doesn't";
   }
   public void doProperty() {
   }
   public void doQTE() {
   }
   public void doFailedQTE() {
   }
}