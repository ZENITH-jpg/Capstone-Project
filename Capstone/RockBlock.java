public class RockBlock extends Block {
   public RockBlock(String n, int v) {
      super(n, v);
      this.type = "rock";
      this.property = "Randomly turns into lava or soil.";
   }
   public void doProperty() {
   }
}