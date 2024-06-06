/*
Tristan Cao
2024-05-31
Mr Guglielmi
The maze minigame initiated when smog qte is clicked on
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeGame extends Minigame {
   private int x; // location in 2d array
   private int y;
   private Image messageBg; // assets
   private Image bg;
   private Image trash;
   private Image p;
   private JTextArea context;
   private long dT; // passed time
   private long tS; // time increment from last (start pos)
   private int flag; // choose what to draw
   private int inv = 0; // how much trash is being held
   private int collected = 0; // how much trash has been recycled

   private int[][] grid;

   public MazeGame(Window w) {
      super(w); // standard init
      this.setFocusable(true);
      this.setBackground(Color.black);
      this.addKeyListener(this);
      this.setBounds(0, 0, 800, 600);
      this.setLayout(null);
      flag = 0;
      p = new ImageIcon("assets/playerico.png").getImage(); // getting images
      bg = new ImageIcon("assets/maze.png").getImage();
      trash = new ImageIcon("assets/trash.png").getImage();
      messageBg = new ImageIcon("assets/background.png").getImage();
      grid = new int[][]{{0, 0, 0, 0, 0, 1, 1, 0, 0, 0}, // make the map
            {0, 1, 1, 1, 0, 0, 1, 0, 1, 1},
            {0, 1, 0, 0, 0, 0, 1, 0, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
            {1, 0, 0, 0, 1, 1, 1, 0, 1, 0},
            {1, 1, 1, 0, 0, 0, 1, 0, 0, 0}};
      context = Utils.messagePanel("Use WASD or ARROW KEYS to move around the maze" + //instructions
            " collect 6 trash bags and recycle them at the recycling icon" +
            "\n\nRecycling helps with climate change by reducing " +
            "gas emissions need to create new products!", 200, 180, 400, 200);
   }

   public void setUp() {
      x = 8; // set up start pos
      y = 3;
      grid[6][2] = 2; // set up trash
      grid[6][6] = 2;
      grid[5][8] = 2;
      grid[0][5] = 2;
      grid[1][3] = 2;
      grid[1][9] = 2;
      tS = System.currentTimeMillis(); //time stuff
      dT = 0;
      flag = 0; // what to display
      collected = 0; // trash info
      inv = 0;
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
      while (dT < 30000 && collected < 6) { // give 30 sec to solve maze
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
            g.drawImage(p, 136 + x * 53, 97 + y * 53, null); // player
            for (int i = 0; i < 7; i++) {
               for (int i1 = 0; i1 < 10; i1++) {
                  if (grid[i][i1] == 2) {
                     g.drawImage(trash, 136 + i1 * 53, 97 + i * 53, null); // draw trash if is trash tile
                  }
               }
            }
            g.setColor(Color.white);
            g.setFont(Utils.MESSAGE_FONT);
            g.drawString("TIME:  "+(30000-dT)/1000+" sec",20,40); // draw maze game info
            g.drawString("COLLECTED:   "+collected+" trash",20,80);
            break;
         default: // win or lose screen
            g.drawImage(messageBg, 0, 0, null); //bg
            g.setColor(Color.white);
            if(collected == 6){ // if 6 trash collected, then it is a win, else, lose
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
      if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) { // get movement key
         if (y + 1 < 7) { // check if position to move to is valid
            if (grid[y + 1][x] != 0) {
               y++; // move if valid
            }
         }
      }
      if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
         if (y - 1 >= 0) {
            if (grid[y - 1][x] != 0) {
               y--;
            }
         }
      }
      if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
         if (x - 1 >= 0) {
            if (grid[y][x - 1] != 0) {
               x--;
            }
         }
      }
      if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
         if (x + 1 < 10) {
            if (grid[y][x + 1] != 0) {
               x++;
            }
         }
      }
      trashHandler(); // handle trash pickup/recycle
   }

   @Override
   public void keyReleased(KeyEvent e) {

   }

   public void trashHandler() {
      if (grid[y][x] == 2) { // if land on trash tile, collect trash into inv
         inv++;
         grid[y][x]--;
      } else if (grid[y][x] == 3) {
         collected += inv; // dispose trash to recycled if on recycling tile
         inv = 0;
      }
   }
}
