/*
Van N
2024-05-31
Mr Guglielmi
Air block in the planet composition
*/
public class AirBlock extends Block {
   public AirBlock(String n, int v) {
      super(n, v);
      this.type = "air";
      this.property = "Without air no creatures can form";
   }
   public void doQTE() {
   }
   public void doFailedQTE() {
   }
}