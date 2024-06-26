/*
Tristan Cao
2024-05-31
Mr Guglielmi
The protest minigame initiated when smog qte is clicked on, space spammer
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The protest minigame initiated when smog qte is clicked on, space spammer
 *
 * @author Tristan C
 * @version 1.0
 */
public class ProtestGame extends Minigame {
   private final Image messageBg; // assets
   private final Image bg;
   private final Image crowd;
   private final Image sign;
   private final JTextArea context;
   private long dT; // passed time
   private long tS; // time increment from last (start pos)
   private int flag; // choose what to draw
   private int clicks = 0; // controls how high the sign is and success of minigame


   public ProtestGame(Window w) {
      super(w); // standard init
      this.setFocusable(true);
      this.setBackground(Color.black);
      this.addKeyListener(this);
      this.setBounds(0, 0, 800, 600);
      this.setLayout(null);
      flag = 0;
      sign = new ImageIcon("assets/sign.png").getImage(); // getting images
      bg = new ImageIcon("assets/protestbg.png").getImage();
      crowd = new ImageIcon("assets/crowd.png").getImage();
      messageBg = new ImageIcon("assets/background.png").getImage();
      context = Utils.messagePanel("Spam the spacebar enough times to raise the protest sign before time runs out!" + //instructions
            "\n\nProtesting for climate laws ensure that our government takes action in the fight against climate change", 200, 180, 400, 200);
   }

   public void setUp() {
      planet = game.getPlanet();
      tS = System.currentTimeMillis(); //time stuff
      dT = 0;
      flag = 0; // what to display
      clicks = 0; // set sign height to lowest
      this.add(context); // tooltip
      startGame(); // start
   }

   @Override
   protected boolean gameWon() {
      return clicks >= 30;
   }

   @Override
   public void startGame() {
      while (dT < 7000) { // show message for 7 secs
         dT += System.currentTimeMillis() - tS; //getting time passed
         tS = System.currentTimeMillis();
         repaint(); // repaint the canvas for animation
      }
      this.requestFocus(); // allow input
      dT = 0; // reset time
      flag++; // change screen
      this.remove(context); // remove tooltip
      while (dT < 10000 - game.getMiniComplete() * 1000L && clicks < 30) { // give time to spam, scales with minigame complete
         dT += System.currentTimeMillis() - tS; // same as prev
         tS = System.currentTimeMillis();
         repaint();
      }
      flag++; // change screen
      dT = 0;
      JTextArea end;
      if (gameWon()) {
         end = Utils.messagePanel("Prevented climate catastrophe!\n\n" + Utils.climateTips[Window.getRandom().nextInt(4)], 200, 300, 400, 200); // text box describing what happens based of win/loss
      } else {
         end = Utils.messagePanel("Smog grows and your creatures die out! Planet heats up faster.\n\n" + Utils.climateTips[Window.getRandom().nextInt(4)], 200, 300, 400, 200);
      }
      this.add(end);
      repaint();
      while (dT < 2000) { // display win or loss screen for 5 sec
         dT += System.currentTimeMillis() - tS;
         tS = System.currentTimeMillis();
      }
      this.remove(end);
      returnToGame();
      repaint(); // gone
   }

   @Override
   public void paintComponent(Graphics g) {
      switch (flag) {
         case 0: // intro
            g.drawImage(messageBg, 0, 0, null); //bg
            int percent = 3 * ((int) dT) / 70;
            g.setColor(Color.black); // "loading" bar
            g.fillRect(percent + 95, 475, 610 - 2 * percent, 30);
            g.setColor(Color.white);
            g.fillRect(percent + 100, 480, 600 - 2 * percent, 20);
            break;
         case 1: // game
            g.drawImage(bg, 0, 0, null); // bg
            g.drawImage(sign, 300, 300 - clicks * 3, null); // the sign (raised depending on clicks)
            g.drawImage(crowd, 0, 0, null); // cover sign and fill the rest of the crowd
            g.setColor(Color.white);
            g.setFont(Utils.MESSAGE_FONT);
            g.drawString("TIME:  " + (10000 - dT - game.getMiniComplete() * 1000L) / 1000 + " sec", 20, 40); // draw game info
            g.drawString("PRESSES:   " + clicks + "/30", 20, 80);
            break;
         default: // win or lose screen
            g.drawImage(bg, 0, 0, null); //draw crowd
            g.drawImage(sign, 300, 300 - clicks * 3, null);
            g.drawImage(crowd, 0, 0, null);
            g.setColor(Color.white);
            if (clicks >= 30) { // win or lose
               g.setFont(Utils.PIXEL.deriveFont(150f));
               g.drawString("you  win", 40, 190);
            } else {
               g.setFont(Utils.PIXEL.deriveFont(130f));
               g.drawString("you  lose", 45, 165);
            }
      }
   }

   @Override
   public void keyTyped(KeyEvent e) {

   }

   @Override
   public void keyPressed(KeyEvent e) {

   }

   @Override
   public void keyReleased(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
         if (clicks < 30) { // if space pressed and clicks is less than 30, then increase the clicks
            clicks++;
         }
      }
   }

}
