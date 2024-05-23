import java.util.ArrayList;
import java.util.Random;

public class Planet {
   static Random random = new Random();
   static GamePanel game;
   private ArrayList<Block> blocks;
   private int score; // no setter only adder
   private int temp; // no setter only adder

   public Planet(GamePanel g) {
      game = g;
      this.score = 0;
      Block.setPlanet(this);
      this.blocks = new ArrayList<Block>();
      this.blocks.add(new WaterBlock("Clean water", 200));
      this.blocks.add(new RockBlock("Rock", 1000));
   }
   
   // Used in GamePanel, chooses a random block that is skewed by every block's volume
   public Block randomWeightedBlock() {
      int volume = random.nextInt(getTotalVolume());
      for (int i = 0; i < this.blocks.size(); i++) {
         if (this.blocks.get(i).getVolume() >= volume)
            return this.blocks.get(i);
         else 
            volume -= this.blocks.get(i).getVolume();
      }
      return null;
   }

   public void sortBlocks() {
      ArrayList<Block> newBlocks = new ArrayList<Block>();
      int i = 0;
      // sort by descending order
      while (i < this.blocks.size()) {
         if (i == 0 || this.blocks.get(i).getVolume() >= this.blocks.get(i - 1).getVolume())
            i++;
         else {
            this.swapBlocks(i, i - 1);
            i--;
         }
      }
      // sort by block type
      String[] typeOrder = new String[] { "air", "ice", "water", "garbage", "soil", "rock", "lava" };
      for (String type : typeOrder) {
         for (Block block : this.blocks) {
            if (block.getType().equals(type))
               newBlocks.add(block);
         }
      }
      this.blocks = newBlocks;
   }

   private void swapBlocks(int i, int j) {
      Block temp = this.blocks.get(i);
      this.blocks.set(i, this.blocks.get(j));
      this.blocks.set(j, temp);
   }

   public int findBlock(String name) {
      for (int i = 0; i < this.blocks.size(); i++) {
         if (this.blocks.get(i).getName().equals(name))
            return i;
      }
      return -1;
   }

   public void addBlock(Block block) {
      int blockIndex = findBlock(block.getName());
      if (blockIndex == -1) { // if planet didn't have block with that name, make a new block
         this.blocks.add(block);
      } else { // otherwise add volume to the block of same name
         this.blocks.get(blockIndex).addVolume(block.getVolume());
         if (this.blocks.get(blockIndex).getVolume() <= 0) {
            // if block has no more volume, remove that block
            this.blocks.remove(blockIndex);
         }
      }
      this.sortBlocks();
      game.displayBlockLabels();
   }

   public int getTotalVolume() {
      int volume = 0;
      for (Block block : this.blocks)
         volume += block.getVolume();
      return volume;
   }

   public void displayConstitution() {
      int volume = getTotalVolume();
      System.out.println("total volume: " + volume);
      for (Block block : this.blocks) {
         double percentOfPlanet = (double) (block.getVolume()) / volume;
         System.out.println((int) (percentOfPlanet * 100) + "% " + block.getName());
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

   public ArrayList<Block> getBlocks() {
      return this.blocks;
   }
}