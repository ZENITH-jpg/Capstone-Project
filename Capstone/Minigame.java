/*
Van Nguyen
2024-06-04
Mr Guglielmi
The superclass for minigames that are added to game panel
*/

import javax.swing.*;
import java.awt.*;
public class Minigame extends JPanel {
   GamePanel game;
   Planet planet;
   public Minigame (GamePanel g, Planet p) {
      game = g;
      planet = p;
   }
}
