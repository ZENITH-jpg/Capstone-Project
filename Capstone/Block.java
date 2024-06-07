/*
Van N
2024-05-31
Mr Guglielmi
Block superclass for the block objects in the game
*/
import java.util.Random;
public abstract class Block {
   protected static Random random = new Random();
   protected static Planet planet; // Whenever a planet is made, sets to that planet
   protected static GamePanel game;
   protected String name;
   protected int volume;
   protected String type; // rock, soil, water, air, etc
   protected String property; // every block has a property or ability
   public Block(String n, int v) {
      this.name = n;
      this.volume = v;
   }
   public abstract void doQTE();
   public abstract void doFailedQTE();
   public static void setPlanet(Planet p) { // sets static planet variable
      planet = p;
   }
   public static void setGame(GamePanel g) { // sets static game variable
      game = g;
   }
   public String getName() {
      return this.name;
   }
   public String getType() {
      return this.type;
   }
   public String getProperty() {
      return this.property;
   }
   public int getVolume() {
      return this.volume;
   }
   public void addVolume(int v) {
      this.volume += v;
   }
   public void setVolume(int v) {
      this.volume = v;
   }
}