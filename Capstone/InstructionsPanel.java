/*
Tristan C
2024-05-24
Mr Guglielmi
Loads scores from leaderboard.txt and creates a leaderboard panel
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controls the data and the graphics of the leaderboard page
 * @author Tristan C
 * @version 1.0
 */
public class InstructionsPanel extends JPanel implements KeyListener {
   private Window window; //fields
   private Image logo;
   private Image background; // images
   private int page;
   private JTextArea[] messages;
   public InstructionsPanel(Window w){ //constructor
      window = w; // set up
      logo = new ImageIcon("assets/instructions.png").getImage();
      background = new ImageIcon("assets/lBackground.png").getImage();
      page = 0;
      this.setFocusable(true); // default panel init
      this.setBackground(Color.black);
      this.addKeyListener(this);
      this.setBounds(0,0,800,600);
      this.setLayout(null);
      messages = new JTextArea[5];
      messages[0] = Utils.messagePanel("In this game, you get to simulate a planet like out Earth! The game ends when the" +
            "thermometer reaches the top, resulting in a planet to hot for any animal to live in, or when all the creatures die out. Clicking on the " +
            "popup QTEs grant special effects that will help you in your journey. Failing to click on them can lead to unwanted effects",300,150,400,300);
      messages[1] = Utils.messagePanel("Rock - Gives rock on click and soil if there is enough rock. Turns into lava if you ignore\n" +
            "Water - Gives water and ice if there is enough water.\n" +
            "Ice - Cools down the planet if clicked, melts into water if missed\n" +
            "Lava - Heats up the planet a bit if clicked, heats the planet up a lot if missed. Passively heats up planet",300,150,400,300);
      messages[2] = Utils.messagePanel("Soil - Increases the population of a species, missing makes one of your species extinct\n" +
            "Smog - Activates a climate related minigame on click, On minigame fail or miss, your species die and can go extinct, passively heats up planet",300,150,400,300);
      messages[3] = Utils.messagePanel("Objectives are found on the side of the screen. Complete for rewards and new game difficulties.\n\n"+
            "Blocks also give score modifiers. Rock and water have a base modifier of 1, Ice and soil have a modifier of 2, Lava and " +
            "smog have a modifier of -1.",300,150,400,300);
      messages[4] = Utils.messagePanel("Minigames have instructions on how to play them. Complete the tasks within the" +
            "required time limit",300,150,400,300);
      for (int i = 0; i < messages.length; i++) {
         this.add(messages[i]);
         messages[i].setVisible(false);
      }
      messages[0].setVisible(true);
      JTextArea message = Utils.messagePanel("Press 'B' to return to menu, left, right arrow and A D keys to flip through instructions",50,490,700,70); // and context box
      this.add(message);
   }

   @Override
   public void paintComponent(Graphics g){ //screen
      Graphics2D g2d = (Graphics2D)g;
      g2d.drawImage(background,0,0,null); //bg
      g2d.drawImage(logo,108,40,null); //top text
   }

   /**
    * Key listener when the key is typed
    * @param e the event to be processed
    */
   @Override
   public void keyTyped(KeyEvent e) {

   }

   /**
    * Key listener when the key is pressed
    * @param e the event to be processed
    */
   @Override
   public void keyPressed(KeyEvent e) { //go back to main menu on b pressed
      if(e.getKeyCode() == KeyEvent.VK_B){
         window.returnMenu();
      }
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT|| e.getKeyCode() == KeyEvent.VK_D){
         if(page+1<5){
            page++;
            messages[page-1].setVisible(false);
            messages[page].setVisible(true);
            repaint();
         }
      }
      else if(e.getKeyCode() == KeyEvent.VK_LEFT|| e.getKeyCode() == KeyEvent.VK_A){
         if(page>0){
            page--;
            messages[page+1].setVisible(false);
            messages[page].setVisible(true);
            repaint();
         }
      }
   }

   /**
    * Key listener when the key is released
    * @param e the event to be processed
    */
   @Override
   public void keyReleased(KeyEvent e) {

   }
}
