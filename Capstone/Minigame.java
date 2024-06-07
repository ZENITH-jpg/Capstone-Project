/*
Van Nguyen
2024-06-04
Mr Guglielmi
The superclass for minigames that are added to game panel
*/

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Minigame superclass that launches when smog QTEs are clicked
 * @author Van N
 * @version 1.0
 */
public abstract class Minigame extends JPanel implements KeyListener {
   /**
    * The window of the game
    */
   protected Window window;
   /**
    * the GamePanel instance of the game
    */
   protected GamePanel game;
   /**
    * The planet simulated in the game
    */
   protected Planet planet;

   /**
    * Constructor, sets game info for the minigame to communicate to
    * @param w The window of the game
    */
   public Minigame (Window w) {
      window = w;
      game = w.getGame();
      planet = game.getPlanet();
   }

   /**
    * Set up the game to play
    */
   public abstract void setUp();

   /**
    * Minigame over, return to main game
    */
   protected void returnToGame(){
      if(!gameWon()){
         // reduce population of a random number of creatures
         Random random = new Random();
         if (planet.getCreatures().size() > 0) {
            int num = 1 + random.nextInt(planet.getCreatures().size());
            // for num times decrease a random creature's population
            while (num > 0) {
               int index = random.nextInt(planet.getCreatures().size());
               Creature c = planet.getCreatures().get(index);
               c.addPopulation(-random.nextInt(c.getPopulation()/2));
               num--;
            }
         }
         //planet.addBlock(new SmogBlock("Smog",500)); // add more smog
      }
      this.setVisible(false); // set minigame to invisible
      game.setVisible(true); // set main game visible
      GamePanel.timerOn = true; // turn back on timers
      game.requestFocus(); // put listener in action

   }

   /**
    * Check if the game was won
    * @return if the game was won or not
    */
   protected abstract boolean gameWon();

   /**
    * On key type
    * @param e the event to be processed
    */
   @Override
   public void keyTyped(KeyEvent e) {
   }

   /**
    * On key pressed
    * @param e the event to be processed
    */
   @Override
   public void keyPressed(KeyEvent e) {   
   }

   /**
    * On key raised
    * @param e the event to be processed
    */
   @Override
   public void keyReleased(KeyEvent e) {
   }

}
