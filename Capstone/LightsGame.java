/*
Tristan Cao
2024-05-31
Mr Guglielmi
The light minigame initiated when smog qte is clicked on, click on lights to turn off
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Lights minigame that requires you to click on lights to turn them off
 *
 * @author Tristan C
 * @version 1.0
 */
public class LightsGame extends Minigame implements MouseListener {
   /**
    * The background of the loading screen
    */
   private final Image messageBg; // assets
   /**
    * The background of the game
    */
   private final Image bg;
   /**
    * Light icon when the light is off
    */
   private final Image lightOff;
   /**
    * Light icon when the light is on
    */
   private final Image lightOn;
   /**
    * Context menu telling player what to do
    */
   private final JTextArea context;
   /**
    * The time passed since some point
    */
   private long dT; // passed time
   /**
    * The time of the last time increment
    */
   private long tS; // time increment from last (start pos)
   /**
    * The flag that choose what to draw (loading screen, game, end screen)
    */
   private int flag; // choose what to draw
   /**
    * light array that states the state of each light (on/off)
    */
   private final boolean[] lights = new boolean[7]; // if light at index i is turned on or off
   /**
    * The x position of each light
    */
   final private int[] x = {70, 265, 470, 670, 170, 565, 490}; //x and y pos of each light
   /**
    * The y position of each light
    */
   final private int[] y = {130, 180, 130, 130, 460, 280, 435};
   /**
    * The amount of lights turned off
    */
   private int turned;

   /**
    * Constructor of game, sets up assests and game info
    *
    * @param w The window the game is in
    */
   public LightsGame(Window w) {
      super(w); // standard init
      this.setFocusable(true);
      this.setBackground(Color.black);
      this.addMouseListener(this);
      this.setBounds(0, 0, 800, 600);
      this.setLayout(null);
      flag = 0;
      turned = 0;
      lightOn = new ImageIcon("assets/on.png").getImage(); // getting images
      lightOff = new ImageIcon("assets/off.png").getImage();
      bg = new ImageIcon("assets/lightroom.png").getImage();
      messageBg = new ImageIcon("assets/background.png").getImage();
      context = Utils.messagePanel("Turn off the lights before going outside! Click on the yellow lights to turn them off." + //instructions
            "\n\nTurning off your lights reduce your energy usage, in turn reducing your carbon footprint, fighting climate change", 200, 180, 400, 200);
   }

   /**
    * Sets up the game to play
    */
   @Override
   public void setUp() {
      planet = game.getPlanet();
      tS = System.currentTimeMillis(); //time stuff
      dT = 0;
      flag = 0; // what to display
      turned = 0; // set the amount of light turned off to 0
      for (int i = 0; i < 7; i++) {
         lights[i] = true; // set lights to on
      }
      this.add(context); // tooltip
      startGame(); // start
   }

   /**
    * Check if the game was won
    *
    * @return if the game was won
    */
   @Override
   protected boolean gameWon() {
      return turned == 7;
   }

   /**
    * Start the minigame off
    */
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
      while (dT < 10000 - game.miniComplete * 1000L && turned < 7) { // give time to turn off light, scales with minigame complete
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

   /**
    * The graphics of the game and what to draw when
    *
    * @param g the <code>Graphics</code> object to protect
    */
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
            g.drawImage(bg, 0, 0, null); //bg
            for (int i = 0; i < 7; i++) {
               if (lights[i]) {
                  g.drawImage(lightOn, x[i], y[i], null); //draw lights
               } else {
                  g.drawImage(lightOff, x[i], y[i], null);
               }
            }
            g.setColor(Color.white);
            g.drawString("TIME:  " + (10000 - dT - game.miniComplete * 1000L) / 1000 + " sec", 20, 30); // draw game info
            g.drawString("TURNED OFF:   " + turned + "/7", 100, 30);
            break;
         default: // win or lose screen
            g.setColor(Color.white);
            g.drawImage(messageBg, 0, 0, null);
            if (turned == 7) { // win or lose
               g.setFont(Utils.PIXEL.deriveFont(150f));
               g.drawString("you  win", 40, 190);
            } else {
               g.setFont(Utils.PIXEL.deriveFont(130f));
               g.drawString("you  lose", 45, 165);
            }
      }
   }

   /**
    * Called on a key typed
    *
    * @param e the event to be processed
    */
   @Override
   public void keyTyped(KeyEvent e) {

   }

   /**
    * Called on a key pressed
    *
    * @param e the event to be processed
    */
   @Override
   public void keyPressed(KeyEvent e) {

   }

   /**
    * Called on key released
    *
    * @param e the event to be processed
    */
   @Override
   public void keyReleased(KeyEvent e) {

   }

   /**
    * Called on mouse click
    *
    * @param e the event to be processed
    */

   @Override
   public void mouseClicked(MouseEvent e) {

   }

   /**
    * Called on mouse pressed, if the left click was pressed and the mouse was over a light, try to turn it off
    *
    * @param e the event to be processed
    */
   @Override
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1) { // if left click
         Point p = e.getPoint(); // get position of click
         for (int i = 0; i < 7; i++) {
            if (x[i] < p.getX() && 22 + x[i] > p.getX() && y[i] < p.getY() && 30 + y[i] > p.getY()) { // check if on a light
               if (lights[i]) turned++; // if light on, turn off and increment amount
               lights[i] = false; // turn light off
            }
         }
      }
   }

   /**
    * Called on mouse released
    *
    * @param e the event to be processed
    */
   @Override
   public void mouseReleased(MouseEvent e) {

   }

   /**
    * Called on mouse entered
    *
    * @param e the event to be processed
    */
   @Override
   public void mouseEntered(MouseEvent e) {

   }

   /**
    * Called on mouse exit
    *
    * @param e the event to be processed
    */
   @Override
   public void mouseExited(MouseEvent e) {

   }
}
