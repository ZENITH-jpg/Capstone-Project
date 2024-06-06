/*
Van N
2024-05-31
Mr Guglielmi
Planet containing the blocks and has other game information such as creatures, and score
*/
import java.util.ArrayList;
import java.util.Random;

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
      System.out.println(name+" is not a block.");
      return -1;
   }

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
   
   public void createCreature() {
      // add a random creature to this.creatures
      this.creatures.add(new Creature(this.creatures.size(), 1000+random.nextInt(1000)));
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
      game.updateLabels();
   }
   
   public int getHumans() {
      return this.humans;
   }
   
   public ArrayList<Creature> getCreatures() {
      return this.creatures;
   }

   public void addHumans(int h) {
      this.humans += h;
      game.updateLabels();
   }
   
   public ArrayList<Block> getBlocks() {
      return this.blocks;
   }
   
   public Block getBlockAtIndex(int i) {
      return this.blocks.get(i);
   }
   
   public Block getBlockWithName(String n) {
      return this.blocks.get(this.findBlock(n));
   }
   
   public void removeBlockWithName(String n) {
      Block block = blocks.get(findBlock(n));
      this.blocks.remove(block);
      game.getQTEPanel().clearQTEs(block.getName());
      game.displayBlockLabels();
   }
}