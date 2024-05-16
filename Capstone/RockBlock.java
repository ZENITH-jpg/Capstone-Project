public class RockBlock extends Block {
   public RockBlock(String t, int v) {
      super(t, v);
      this.property = "Randomly turns into lava or soil.";
   }
   public void doProperty(Planet p) {
   }
}