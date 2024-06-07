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

public abstract class Minigame extends JPanel implements KeyListener {
   protected Window window;
   protected GamePanel game;
   protected Planet planet;
   public Minigame (Window w) {
      window = w;
      game = w.getGame();
      planet = game.getPlanet();
   }
   public abstract void setUp();
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
   protected abstract boolean gameWon();
   @Override
   public void keyTyped(KeyEvent e) {
   }

   @Override
   public void keyPressed(KeyEvent e) {   
   }

   @Override
   public void keyReleased(KeyEvent e) {
   }

}
