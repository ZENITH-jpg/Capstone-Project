public class SoilBlock extends Block {
   public SoilBlock(String n, int v) {
      super(n, v);
      this.type = "soil";
      this.property = "WIP";
   }
   public void doProperty() {
   }
   public void doQTE() {
      // create a random creature, max of 5 creatures
      // if humans unlocked from objective, increase human population
   }
   public void doFailedQTE() {
      // just produces soil
   }
}