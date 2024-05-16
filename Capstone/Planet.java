import java.util.ArrayList;
// change addBlock() to add the right kind of block
public class Planet {
   private ArrayList<Block> blocks;
   private int score; // no setter only adder
   private int temp; // no setter only adder
   public Planet () {
      this.score = 0;
      this.blocks = new ArrayList<Block>();
      this.blocks.add(new RockBlock("Rock", 1000));
   }
   public int findBlock(String type) {
      for (int i = 0; i < this.blocks.size(); i++) {
         if (this.blocks.get(i).getType().equals(type))
            return i;
      }
      return -1;
   }
   public void addBlock(Block block) {
      if (findBlock(block.getType()) == -1) { // if planet didn't have block of that type, make a new block
         this.blocks.add(block);
      } else { // otherwise add volume to the block of same type
         this.blocks.get(findBlock(block.getType())).addVolume(block.getVolume());
      }
   }
   public int getTotalVolume() {
      int volume = 0;
      for (Block block : this.blocks)
         volume += block.getVolume();
      return volume;
   }
   public void displayConstitution() {
      int volume = getTotalVolume();
      System.out.println("total volume: "+volume);
      for (Block block : this.blocks) {
         double percentOfPlanet = (double)(block.getVolume())/volume;
         System.out.println((int)(percentOfPlanet*100)+"% "+block.getType());
      }
   }
   public int getScore() {
      return this.score;
   }
   public void addScore(int s) {
      this.score += s;
   }
   public int getTemp() {
      return this.temp;
   }
   public void addTemp(int t) {
      this.temp += t;
   }
}