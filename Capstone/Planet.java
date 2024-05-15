import java.util.ArrayList;
public class Planet {
   private ArrayList<Block> blocks;
   private int score;
   public Planet () {
      this.score = 0;
      this.blocks = new ArrayList<Block>();
      this.blocks.add(new Block("Rock", 1000));
   }
   public int findBlock(String type) {
      for (int i = 0; i < this.blocks.size(); i++) {
         if (this.blocks.get(i).getType().equals(type))
            return i;
      }
      return -1;
   }
   public void addBlock(String type, int volume) {
      if (findBlock(type) == -1) {
         this.blocks.add(new Block(type, volume));
      } else {
         this.blocks.get(findBlock(type)).addVolume(volume);
      }
   }
   public int getScore() {
      return this.score;
   }
}