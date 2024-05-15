import java.util.ArrayList;
public class Planet {
   private ArrayList<Block> blocks;
   private int score;
   public Planet () {
      this.score = 0;
      this.blocks = new ArrayList<Block>();
      this.blocks.add(new Block("Rock", 1000));
   }
}