/*
Van N
2024-05-31
Mr Guglielmi
Ice block in the planet composition
*/
public class IceBlock extends Block {
   public IceBlock(String n, int v) {
      super(n, v);
      this.type = "ice";
      this.property = "Click Ice QTEs to cool down planet, but only when >800 volume.";
   }
   public void doQTE() {
      if (planet.getBlockWithName("Ice").getVolume() < 800) {
         int num = 150+random.nextInt(100);
         planet.addTemp(-random.nextInt(10));
         planet.addBlock(new IceBlock(this.getName(), num));
      }
   }
   public void doFailedQTE() {
      int num = 50+random.nextInt(100);
      planet.addBlock(new WaterBlock("Clean water", num));
      if (planet.getBlockWithName(this.getName()).getVolume() > 150)
         planet.addBlock(new IceBlock(this.getName(), -num));
   }
}