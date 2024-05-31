public class IceBlock extends Block {
   public IceBlock(String n, int v) {
      super(n, v);
      this.type = "ice";
      this.property = "Click Ice QTEs to turn water into ice. Miss the QTEs to turn ice into clean water.";
   }
   public void doQTE() {
      planet.addTemp(-random.nextInt(10));
      if (planet.getBlockWithName("Clean water").getVolume() > 300) {
         int num = 50+random.nextInt(100);
         planet.addBlock(new IceBlock(this.getName(), num));
         planet.addBlock(new WaterBlock("Clean water", -num));
      }
   }
   public void doFailedQTE() {
      int num = 50+random.nextInt(100);
      planet.addBlock(new WaterBlock("Clean water", num));
      if (planet.getBlockWithName(this.getName()).getVolume() > 150)
         planet.addBlock(new IceBlock(this.getName(), -num));
   }
}