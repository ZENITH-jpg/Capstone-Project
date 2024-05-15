import java.util.ArrayList;
// change addBlock() to add the right kind of block
public class Planet {
   private ArrayList<Block> blocks;
   private int score;
   private int temp;
   public Planet () {
      this.score = 0;
      this.blocks = new ArrayList<Block>();
      this.blocks.add(new RockBlock("Rock", 1000, 0));
   }
   public int findBlock(String type) {
      for (int i = 0; i < this.blocks.size(); i++) {
         if (this.blocks.get(i).getType().equals(type))
            return i;
      }
      return -1;
   }
   public void addBlock(String type, int volume, int cleanliness) {
      if (findBlock(type) == -1) { // if planet didn't have block of that type, make a new block
         // this.blocks.add(new Block(type, volume, cleanliness));
         this.blocks.add(new RockBlock(type, volume, cleanliness));
      } else { // otherwise add volume to the block of same type
         this.blocks.get(findBlock(type)).addVolume(volume);
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
   public int getTemp() {
      return this.temp;
   }
   public void addTemp(int t) {
      this.temp += t;
   }
}