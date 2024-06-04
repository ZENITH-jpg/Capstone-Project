/*
Tristan Cao
2024-05-31
Mr Guglielmi
The light minigame initiated when smog qte is clicked on, click on lights to turn off
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LightsGame extends Minigame {
   private Image messageBg; // assets
   private Image bg;
   private Image lightOff;
   private Image lightOn;
   private JTextArea context;
   private long dT; // passed time
   private long tS; // time increment from last (start pos)
   private int flag; // choose what to draw
   private int clicks = 0; // controls how high the sign is and success of minigame


   public LightsGame(GamePanel g, Planet p) {
      super(g, p); // standard init
      this.setFocusable(true);
      this.setBackground(Color.black);
      this.addKeyListener(this);
      this.setBounds(0, 0, 800, 600);
      this.setLayout(null);
      flag = 0;
      lightOn = new ImageIcon("assets/on.png").getImage(); // getting images
      lightOff = new ImageIcon("assets/off.png").getImage();
      bg = new ImageIcon("assets/lightroom.png").getImage();
      messageBg = new ImageIcon("assets/background.png").getImage();
      context = Utils.messagePanel("Turn off the lights before going outside! Click on the yellow lights to turn them off." + //instructions
            "\n\nTurning off your lights reduce your energy usage, in turn reducing your carbon footprint", 200, 180, 400, 200);
   }

   public void setUp() {
      tS = System.currentTimeMillis(); //time stuff
      dT = 0;
      flag = 0; // what to display
      clicks = 0; // set sign height to lowest
      this.add(context); // tooltip
      startGame(); // start
   }

   public void startGame() {
      while (dT < 7000) { // show message for 7 secs
         dT += System.currentTimeMillis() - tS; //getting time passed
         tS = System.currentTimeMillis();
         repaint(); // repaint the canvas for animation
      }
      this.requestFocus(); // allow input
      dT = 0; // reset time
      flag ++; // change screen
      this.remove(context); // remove tooltip
      while (dT < 10000 && clicks < 30) { // give 10 sec to spam
         dT += System.currentTimeMillis() - tS; // same as prev
         tS = System.currentTimeMillis();
         repaint();
      }
      flag++; // change screen
      dT = 0;
      while (dT < 5000) { // display win or loss screen for 5 sec
         dT += System.currentTimeMillis() - tS;
         tS = System.currentTimeMillis();
      }
      this.setVisible(false); // set minigame to invisible
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

            break;
         default: // win or lose screen
            if(clicks >= 30){ // win or lose
               g.setFont(Utils.PIXEL.deriveFont(150f));
               g.drawString("you  win",40,190);
            }else{
               g.setFont(Utils.PIXEL.deriveFont(130f));
               g.drawString("you  lose",45,165);
            }
      }
   }

   @Override
   public void keyTyped(KeyEvent e) {

   }

   @Override
   public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_SPACE){
         if(clicks<30){ // if space pressed and clicks is less than 30, then increase the clicks
            clicks++;
         }
      }
   }

   @Override
   public void keyReleased(KeyEvent e) {

   }

}
