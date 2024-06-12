/*
Van N
2024-05-31
Mr Guglielmi
Block superclass for the block objects in the game
*/
import java.util.Random;

/**
 * The block superclass that makes the composition of the planet in the bottom left of the game
 * @author Van N
 * @version 1.0
 */
public abstract class Block {
   /**
    * Random instance for blocks
    */
   protected static Random random = new Random();
   /**
    * The planet that exists in the game
    */
   protected static Planet planet; // Whenever a planet is made, sets to that planet
   /**
    * The game panel which runs and displays the game
    */
   protected static GamePanel game;
   /**
    * The name of the block
    */
   protected String name;
   /**
    * The size of the block
    */
   protected int volume;
   /**
    * The type of block (rock, soil, water, air, etc)
    */
   protected String type; // rock, soil, water, air, etc
   /**
    * String descriptor of the properties blocks have
    */
   protected String property; // every block has a property or ability
   /**
    * Constructor for block
    * @param n The name of the block
    * @param v The volume of the block
    */
   public Block(String n, int v) {
      this.name = n;
      this.volume = v;
   }
   /**
    * Chooses what happens when the QTE is clicked
    */
   public abstract void doQTE();
   /**
    * Chooses what happens when the QTE is ignored
    */
   public abstract void doFailedQTE();

   /**
    * Sets planet of blocks
    * @param p planet to refer to
    */
   public static void setPlanet(Planet p) { // sets static planet variable
      planet = p;
   }

   /**
    * Sets the game panel for blocks
    * @param g the instance of the game panel
    */
   public static void setGame(GamePanel g) { // sets static game variable
      game = g;
   }

   /**
    * Gets the name of the block
    * @return the name of the block
    */
   public String getName() {
      return this.name;
   }

   /**
    * Get the type of the block
    * @return the type of the block (smog, water, rock)
    */
   public String getType() {
      return this.type;
   }

   /**
    * Get the properties of the block
    * @return the special properties of the block
    */
   public String getProperty() {
      return this.property;
   }

   /**
    * Get the volume of the block
    * @return the volume of the block
    */
   public int getVolume() {
      return this.volume;
   }

   /**
    * Increase the amount of volume of the block
    * @param v the volume to be added to the block
    */
   public void addVolume(int v) {
      this.volume += v;
   }

   /**
    * Set the volume of the block
    * @param v the volume to set the block to
    */
   public void setVolume(int v) {
      this.volume = v;
   }
}