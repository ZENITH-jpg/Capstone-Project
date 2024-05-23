import java.util.Random;

public class RockBlock extends Block {
   static Random random  = new Random();
   public RockBlock(String n, int v) {
      super(n, v);
      this.type = "rock";
      this.property = "Randomly turns into lava or soil.";
   }
   public void doProperty() {
   }
   public void doQTE() {
      if (this.volume < 1000 + random.nextInt(501)) {
         planet.addBlock(new RockBlock(this.getName(),random.nextInt(200)));
      } else {
         planet.addBlock(new LavaBlock("Soil", random.nextInt(200)));
      }
   }
   public void doFailedQTE() {
         planet.addBlock(new LavaBlock("Lava", random.nextInt(200)));
   }
}