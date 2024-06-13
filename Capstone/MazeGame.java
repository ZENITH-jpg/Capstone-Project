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
import java.util.Random;

/**
 * The maze minigame which requires you to recycle trash
 *
 * @author Tristan C
 * @version 1.0
 */
public class MazeGame extends Minigame {
   /**
    * x location in 2d array
    */
   private int x;
   /**
    * y location in 2d array
    */
   private int y;
   /**
    * Background for loading screen
    */
   private final Image messageBg;
   /**
    * Background for the game
    */
   private final Image bg;
   /**
    * Trash icon in game
    */
   private final Image trash;
   /**
    * Player icon in game
    */
   private final Image p;
   /**
    * Context box which tells player what to do
    */
   private final JTextArea context;
   /**
    * time passed since some point
    */
   private long dT;
   /**
    * The time the last increment was at
    */
   private long tS;
   /**
    * The flag that choose what to draw (loading screen, game, end screen)
    */
   private int flag; // choose what to draw
   /**
    * How much trash is in the player's inventory
    */
   private int inv = 0; // how much trash is being held
   /**
    * How much trash has been recycled by the player
    */
   private int collected = 0; // how much trash has been recycled
   /**
    * The grid containing information about the maze (walls, trash, recycle tile)
    */
   private final int[][] grid;

   /**
    * Constructor of the maze game, sets up assets
    *
    * @param w the window the game is played on
    */
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

   /**
    * Sets up the maze game to play
    */
   @Override
   public void setUp() {
      planet = game.getPlanet();
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

   /**
    * Check if the game was won
    *
    * @return if the game was won
    */
   @Override
   protected boolean gameWon() {
      return (collected == 6);
   }

   /**
    * Stars the minigame and cleans it up when over
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
      while (dT < 30000 - game.miniComplete * 3500L && collected < 6) { // give 30 sec to solve maze
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
    * The graphics of the Maze Game and choosing what to draw when
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
            g.drawString("TIME:  " + (30000 - dT - game.miniComplete * 3500L) / 1000 + " sec", 20, 40); // draw maze game info
            g.drawString("COLLECTED:   " + collected + " trash", 20, 80);
            break;
         default: // win or lose screen
            g.drawImage(messageBg, 0, 0, null); //bg
            g.setColor(Color.white);
            if (collected == 6) { // if 6 trash collected, then it is a win, else, lose
               g.setFont(Utils.PIXEL.deriveFont(150f));
               g.drawString("you  win", 40, 190);
            } else {
               g.setFont(Utils.PIXEL.deriveFont(130f));
               g.drawString("you  lose", 45, 165);
            }
      }
   }

   /**
    * On a key typed
    *
    * @param e the event to be processed
    */
   @Override
   public void keyTyped(KeyEvent e) {

   }

   /**
    * On a key press, check if it is a movement key and try to move in that direction
    *
    * @param e the event to be processed
    */
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

   /**
    * When key is released
    *
    * @param e the event to be processed
    */
   @Override
   public void keyReleased(KeyEvent e) {

   }

   /**
    * Handler for how much trash is picked up in inv and how much trash has been recycled
    */
   private void trashHandler() {
      if (grid[y][x] == 2) { // if land on trash tile, collect trash into inv
         inv++;
         grid[y][x]--;
      } else if (grid[y][x] == 3) {
         collected += inv; // dispose trash to recycled if on recycling tile
         inv = 0;
      }
   }
}
