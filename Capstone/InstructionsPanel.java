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
   private Image[] images;
   public InstructionsPanel(Window w){ //constructor
      window = w; // set up
      logo = new ImageIcon("assets/instructions.png").getImage();
      background = new ImageIcon("assets/lBackground.png").getImage();
      this.setFocusable(true); // default panel init
      this.setBackground(Color.black);
      this.addKeyListener(this);
      this.setBounds(0,0,800,600);
      this.setLayout(null);
      messages = new JTextArea[5];
      messages[0] = Utils.messagePanel("In this game, you get to simulate a planet like out Earth! \nThe game ends when the " +
            "thermometer reaches the top, resulting in a planet to hot for any animal to live in, or when all the creatures die out. \nClicking on the " +
            "popup QTEs grant special effects that will help you in your journey. Failing to click on them can lead to unwanted effects. Hover over the QTE block to see more properties",300,150,400,300);
      messages[1] = Utils.messagePanel("Rock - Gives rock on click and soil if there is enough rock. Turns into lava if you ignore.\n" +
            "Water - Gives water and ice if there is enough water.\n\n" +
            "Ice - Cools down the planet if clicked, melts into water if missed\n\n" +
            "Lava - Heats up the planet a bit if clicked, heats the planet up a lot if missed. Passively heats up planet",300,150,400,300);
      messages[2] = Utils.messagePanel("Soil - Increases the population of a species, missing makes one of your species extinct. WARNING: Missing can end the game!\n\n" +
            "Smog - Activates a climate related minigame on click, On minigame fail or miss, your species die and can go extinct, passively heats up planet",300,150,400,300);
      messages[3] = Utils.messagePanel("Objectives are found on the side of the screen. Complete for rewards and new game difficulties.\n\n"+
            "Blocks also give score modifiers. Rock and water have a base modifier of 1, Ice and soil have a modifier of 2, Lava and " +
            "smog have a modifier of -1. Each new species of creature give you an additional score modifier as well.",300,150,400,300);
      messages[4] = Utils.messagePanel("Minigames have instructions on how to play them. Complete the tasks within the " +
            "required time limit. As you complete more minigames, the time limit decreases.",300,150,400,300);
      images = new Image[20];
      images[0] = new ImageIcon("assets/thermometer.png").getImage();
      images[1] = new ImageIcon("assets/QTE.png").getImage();
      images[2] = new ImageIcon("assets/rock.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
      images[3] = new ImageIcon("assets/ice.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
      images[4] = new ImageIcon("assets/lava.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
      images[5] = new ImageIcon("assets/smog.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
      images[6] = new ImageIcon("assets/soil.png").getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
      images[7] = new ImageIcon("assets/objective.png").getImage();
      images[8] = new ImageIcon("assets/creature.png").getImage();
      images[9] = new ImageIcon("assets/playerico.png").getImage();
      images[10] = new ImageIcon("assets/on.png").getImage();
      images[11] = new ImageIcon("assets/sign.png").getImage();
      page = -1;
      repaint();
      page = 0;
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
      for (int i = 0; i < images.length; i++) {
         g2d.drawImage(images[i],0,0,null); // ignore this, needed to load scaled images for some reason
      }
      g2d.drawImage(background,0,0,null); //bg
      g2d.drawImage(logo,108,40,null); //top text
      switch (page){
         case 0:
            g2d.drawImage(images[0],110,130,null);
            g2d.drawImage(images[1],75,290,null);
            break;
         case 1:
            g2d.drawImage(images[2],125,175,null);
            g2d.drawImage(images[3],125,275,null);
            g2d.drawImage(images[4],125,375,null);
            break;
         case 2:
            g2d.drawImage(images[6],125,175,null);
            g2d.drawImage(images[5],125,275,null);
            break;
         case 3:
            g2d.drawImage(images[7],50,175,null);
            g2d.drawImage(images[8],75,275,null);
            break;
         default:
            g2d.drawImage(images[9],125,175,null);
            g2d.drawImage(images[10],140,235,null);
            g2d.drawImage(images[11],50,275,null);
      }
      g2d.setFont(Utils.MESSAGE_FONT);
      g2d.setColor(Color.black);
      g2d.drawString((page+1)+"/5",720,450);
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
