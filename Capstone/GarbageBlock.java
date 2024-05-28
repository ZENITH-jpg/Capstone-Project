public class GarbageBlock extends Block {
   public GarbageBlock(String n, int v) {
      super(n, v);
      this.type = "garbage";
      this.property = "Can reduce the population of creatures";
   }
   public void doProperty() {
   }
   public void doQTE() {
      // reduce population of a random number of creatures
   }
   public void doFailedQTE() {
   }
}