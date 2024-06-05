/*
Van Nguyen
2024-06-04
Mr Guglielmi
The superclass for minigames that are added to game panel
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Minigame extends JPanel implements KeyListener {
   protected Window window;
   protected GamePanel game;
   protected Planet planet;
   public Minigame (Window w) {
      window = w;
      game = w.getGame();
      planet = game.getPlanet();
   }
   public void setUp() {}
   protected void returnToGame(){

   }
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
