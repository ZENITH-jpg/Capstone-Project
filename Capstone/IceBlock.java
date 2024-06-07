/*
Van N
2024-05-31
Mr Guglielmi
Ice block in the planet composition
*/
public class IceBlock extends Block {
   /**
    * Constructor for the class, creates block using name and volume, sets the description
    * @param n the name of the block
    * @param v the volume the block holds
    */
   public IceBlock(String n, int v) {
      super(n, v);
      this.type = "ice";
      this.property = "Click Ice QTEs to cool down planet. DANGER: Missing QTEs makes ice disappear. Max volume = 800";
   }
   public void doQTE() {
      planet.addTemp(-5-random.nextInt(20));
   }
   public void doFailedQTE() {
      int num = 50+random.nextInt(100);
      planet.addBlock(new WaterBlock("Clean water", num));
      planet.addBlock(new IceBlock(this.getName(), -num));
   }
}