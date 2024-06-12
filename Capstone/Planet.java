/*
Van N
2024-05-31
Mr Guglielmi
Planet containing the blocks and has other game information such as creatures, and score
*/
import java.util.ArrayList;
import java.util.Random;

/**
Planet containing the blocks and creatures and has other game information such as temp and score
@author Van N
@version 1.0
*/
public class Planet {
   static Random random = new Random();
   static GamePanel game;
   private ArrayList<Block> blocks;
   private ArrayList<Creature> creatures;
   public static int maxCreatures = 8;
   // The fields below have no setter, only adder
   private int score;
   private int temp;
   private int humans;

   /**
   Constructor
   @param g GamePanel to refer to
   */
   public Planet(GamePanel g) {
      game = g;
      this.score = 0;
      this.temp = 100;
      this.humans = 0;
      Block.setPlanet(this);
      Block.setGame(game);
      this.blocks = new ArrayList<Block>();
      this.blocks.add(new WaterBlock("Clean water", 200));
      this.blocks.add(new RockBlock("Rock", 1000));
      this.creatures = new ArrayList<Creature>();
      Creature.randomizeSpecies();
   }
   
   /**
   Sorts blocks from descending volume and then based on their types
   */
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
      String[] typeOrder = new String[] { "smog","air", "ice", "water", "soil", "rock", "lava" };
      for (String type : typeOrder) {
         for (Block block : this.blocks) {
            if (block.getType().equals(type))
               newBlocks.add(block);
         }
      }
      this.blocks = newBlocks;
   }

   /**
   Swap two blocks in the blocks array
   @param i index of 1st block
   @param j index of 2nd block
   */
   private void swapBlocks(int i, int j) {
      Block temp = this.blocks.get(i);
      this.blocks.set(i, this.blocks.get(j));
      this.blocks.set(j, temp);
   }

   /**
   Search to find a block with the specified name
   @param name name
   @return the index of block, -1 if the block doesn't exist
   */
   public int findBlock(String name) {
      for (int i = 0; i < this.blocks.size(); i++) {
         if (this.blocks.get(i).getName().equals(name))
            return i;
      }
      // System.out.println(name+" is not a block."); // for debugging purposes
      return -1;
   }
   
   /**
   Adds a block or block volume to the planet
   @param block block to be added
   */
   public void addBlock(Block block) {
      int blockIndex = findBlock(block.getName());
      if (blockIndex == -1) { // if planet didn't have block with that name, make a new block
         this.blocks.add(block);
      } else { // otherwise add volume to the block of same name
         this.blocks.get(blockIndex).addVolume(block.getVolume());
         if (this.blocks.get(blockIndex).getVolume() <= 0) {
            // if block has no more volume, remove that block qte and block
            game.getQTEPanel().removeChance(this.blocks.get(blockIndex).getName());
            game.getQTEPanel().clearQTEs(block.getName());
            this.blocks.remove(blockIndex);
         }
      }
      this.sortBlocks();
      game.displayBlockLabels();
   }
   
   /**
   Gets total volume of all blocks combined
   @return total volume
   */
   public int getTotalVolume() {
      int volume = 0;
      for (Block block : this.blocks)
         volume += block.getVolume();
      return volume;
   }
   
   /**
   Add a random creature to creatures array
   */
   public void createCreature() {
      this.creatures.add(new Creature(this.creatures.size(), 1000+random.nextInt(1000)));
   }

   /**
   Get score
   @return score
   */
   public int getScore() {
      return this.score;
   }
   
   /**
   Adds to score
   @param s amount to add
   */
   public void addScore(int s) {
      this.score += s;
   }

   /**
   Get temperature
   @return temp
   */
   public int getTemp() {
      return this.temp;
   }
   
   /**
   Adds to temperature
   @param t amount to add
   */
   public void addTemp(int t) {
      this.temp += t;
      if(temp < 0) temp = 0;
      game.updateLabels();
   }
   
   /**
   Get humans
   @return population
   */
   public int getHumans() {
      return this.humans;
   }
   
   /**
   Get creatures array
   @return planet's creatures
   */
   public ArrayList<Creature> getCreatures() {
      return this.creatures;
   }
   
   /**
   Add to human population
   @param h amount to add
   */
   public void addHumans(int h) {
      this.humans += h;
      game.updateLabels();
   }
   
   /**
   Get blocks array
   @return planet's blocks
   */
   public ArrayList<Block> getBlocks() {
      return this.blocks;
   }
   
   /**
   Get the block at specific index in blocks array
   @param i index
   @return the block
   */
   public Block getBlockAtIndex(int i) {
      return this.blocks.get(i);
   }
   
   /**
   Get the block with a name, block must exist
   @param n name
   @return the block
   */
   public Block getBlockWithName(String n) {
      return this.blocks.get(this.findBlock(n));
   }
}