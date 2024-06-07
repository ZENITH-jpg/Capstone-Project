/*
Van N
2024-05-31
Mr Guglielmi
Air block in the planet composition
*/

/**
 * Air block in the planet's composition, has information regarding the block and controls what happens on QTE events
 * @author Van N
 * @version 1.0
 */
public class AirBlock extends Block {
   /**
    * Constructor for the class, creates block using name and volume, sets the description
    * @param n the name of the block
    * @param v the volume the block holds
    */
   public AirBlock(String n, int v) {
      super(n, v);
      this.type = "air";
      this.property = "Without air no creatures can form";
   }

   /**
    * Chooses what happens when the QTE is clicked
    */
   public void doQTE() {
   }

   /**
    * Chooses what happens when the QTE is ignored
    */
   public void doFailedQTE() {
   }
}