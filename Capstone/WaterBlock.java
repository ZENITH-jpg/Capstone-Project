public class WaterBlock extends Block {
   public WaterBlock(String n, int v) {
      super(n, v);
      this.type = "water";
      this.property = "Regulates heat and turns into ice.";
   }
   public void doProperty(Planet p) {
   }
}