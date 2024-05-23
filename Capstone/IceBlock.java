public class IceBlock extends Block {
   public IceBlock(String n, int v) {
      super(n, v);
      this.type = "ice";
      this.property = "Click Ice QTEs to cool down planet";
   }
   public void doProperty() {
   }
   public void doQTE() {
      planet.addTemp(-random.nextInt(10));
   }
   public void doFailedQTE() {
   }
}