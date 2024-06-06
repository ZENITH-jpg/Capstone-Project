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

public class LightsGame extends Minigame implements MouseListener {
   private final Image messageBg; // assets
   private final Image bg;
   private final Image lightOff;
   private final Image lightOn;
   private final JTextArea context;
   private long dT; // passed time
   private long tS; // time increment from last (start pos)
   private int flag; // choose what to draw
   private final boolean[] lights = new boolean[7]; // if light at index i is turned on or off
   final private int[] x = {70,265,470,670,170,565,490}; //x and y pos of each light
   final private int[] y = {130,180,130,130,460,280,435};
   private int turned;


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

   public void setUp() {
      Timer t = new Timer(33, null);
      t.start();
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

   @Override
   protected boolean gameWon() {
      return turned==7;
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
      while (dT < 10000 && turned<7) { // give 10 sec to turn off lights
         dT += System.currentTimeMillis() - tS; // same as prev
         tS = System.currentTimeMillis();
         repaint();
      }
      flag++; // change screen
      dT = 0;
      repaint();
      while (dT < 5000) { // display win or loss screen for 5 sec
         dT += System.currentTimeMillis() - tS;
         tS = System.currentTimeMillis();
      }
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
            g.drawImage(bg,0,0,null); //bg
            for (int i = 0; i < 7; i++) {
               if(lights[i]){
                  g.drawImage(lightOn,x[i],y[i],null); //draw lights
               }else{
                  g.drawImage(lightOff,x[i],y[i],null);
               }
            }
            g.setColor(Color.white);
            g.drawString("TIME:  "+(10000-dT)/1000+" sec",20,30); // draw game info
            g.drawString("TURNED OFF:   "+ turned +"/7",100,30);
            break;
         default: // win or lose screen
            g.drawImage(messageBg,0,0,null);
            if(turned ==7){ // win or lose
               g.setFont(Utils.PIXEL.deriveFont(150f));
               g.setColor(Color.white);
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

   }

   @Override
   public void keyReleased(KeyEvent e) {

   }

   @Override
   public void mouseClicked(MouseEvent e) {

   }

   @Override
   public void mousePressed(MouseEvent e) {
      if(e.getButton() == MouseEvent.BUTTON1){ // if left click
         Point p = e.getPoint(); // get position of click
         for (int i = 0; i < 7; i++) {
            if(x[i] < p.getX() && 22 + x[i] > p.getX() && y[i] < p.getY() && 30 + y[i] > p.getY()){ // check if on a light
               if(lights[i]) turned++; // if light on, turn off and increment amount
               lights[i] = false; // turn light off
            }
         }
      }
   }

   @Override
   public void mouseReleased(MouseEvent e) {

   }

   @Override
   public void mouseEntered(MouseEvent e) {

   }

   @Override
   public void mouseExited(MouseEvent e) {

   }
}
