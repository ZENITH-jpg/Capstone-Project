public class RockBlock extends Block {
   public RockBlock(String t, int v, int c) {
      super(t, v, c);
      this.property = "Randomly turns into lava or soil.";
   }
   public void doProperty(Planet p) {
   }
}