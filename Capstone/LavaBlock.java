/*
Van N
2024-05-31
Mr Guglielmi
Lava block in the planet composition
*/
/**
 * The Lava block in the planet's composition. Controls what happens when QTEs are clicked or failed
 */
public class LavaBlock extends Block {
   /**
    * Constructor for the class, creates block using name and volume, sets the description
    * @param n the name of the block
    * @param v the volume the block holds
    */
   public LavaBlock(String n, int v) {
      super(n, v);
      this.type = "lava";
      this.property = "Lava QTEs warms up the planet. Missing them is worse for the planet";
   }

   /**
    * On QTE clicked, raise the temperature of the planet a bit
    */
   public void doQTE() {
      if (planet.getTemp() <= 250) {
         planet.addTemp(10+random.nextInt(10));
      }
   }

   /**
    * On QTE fail, raise the temperature of the planet a lot
    */
   public void doFailedQTE() {
      planet.addTemp(25+random.nextInt(40));
   }
}