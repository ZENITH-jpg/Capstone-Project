public class AirBlock extends Block {
   public AirBlock(String n, int v) {
      super(n, v);
      this.type = "air";
      this.property = "Causes clouds and storms to form.";
   }
   public void doProperty() {
   }
}