/*
Van Nguyen
2024-06-04
Mr Guglielmi
The superclass for minigames that are added to game panel
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Minigame extends JPanel implements KeyListener {
   GamePanel game;
   Planet planet;
   public Minigame (GamePanel g, Planet p) {
      game = g;
      planet = p;
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
