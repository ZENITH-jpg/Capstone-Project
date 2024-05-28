import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeGame extends JPanel implements KeyListener {
   private Window window;
   private Planet planet;
   private int x;
   private int y;
   private Image messageBg;
   private Image bg;
   private Image trash;
   private Image p;
   private JTextArea context;
   private long dT; // passed time
   private long tS; // time start
   private int flag;
   private int inv = 0;
   private int collected = 0;

   private int[][] grid;

   public MazeGame(Window w) {
      window = w; // standard init
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
      grid = new int[][]{{0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
            {0, 1, 1, 1, 0, 0, 1, 0, 1, 1},
            {0, 1, 0, 0, 0, 0, 1, 0, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
            {1, 0, 0, 0, 1, 1, 1, 0, 1, 0},
            {1, 1, 1, 0, 0, 0, 1, 0, 0, 0}};
      context = Utils.messagePanel("Use WASD or ARROW KEYS to move around the maze" +
            " collect 6 trash bags and recycle them at the recycling icon" +
            "\n\nRecycling helps with climate change by reducing " +
            "gas emissions need to create new products!", 200, 200, 400, 200);
   }

   public void setUp() {
      x = 8;
      y = 3;
      grid[6][2] = 2;
      grid[6][6] = 2;
      grid[5][8] = 2;
      grid[0][5] = 2;
      grid[1][3] = 2;
      grid[1][9] = 2;
      tS = System.currentTimeMillis();
      dT = 0;
      flag = 0;
      this.add(context);
      startGame();
   }

   public void startGame() {
      while (dT < 7000) {
         dT = System.currentTimeMillis() - tS;
         repaint();
      }
      this.requestFocus();
      dT = 0;
      flag = 1;
      this.remove(context);
      while (dT < 30000 || collected < 6) {
         dT = System.currentTimeMillis() - tS;
         repaint();
      }
      flag++;
   }

   @Override
   public void paintComponent(Graphics g) {
      switch (flag) {
         case 0:
            g.drawImage(messageBg, 0, 0, null);
            int percent = 3 * ((int) dT) / 70;
            g.setColor(Color.black);
            g.fillRect(percent + 95, 475, 610 - 2 * percent, 30);
            g.setColor(Color.white);
            g.fillRect(percent + 100, 480, 600 - 2 * percent, 20);
            break;
         case 1:
            g.drawImage(bg, 0, 0, null);
            g.drawImage(p, 136 + x * 53, 97 + y * 53, null);
            for (int i = 0; i < 7; i++) {
               for (int i1 = 0; i1 < 10; i1++) {
                  if (grid[i][i1] == 2) {
                     g.drawImage(trash, 136 + i1 * 53, 97 + i * 53, null);
                  }
               }
            }
         default:
            g.drawImage(messageBg, 0, 0, null);

      }
      Utils.drawGrid(g);
   }

   @Override
   public void keyTyped(KeyEvent e) {

   }

   @Override
   public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
         if (y + 1 < 7) {
            if (grid[y + 1][x] != 0) {
               y++;
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
      trashHandler();
   }

   @Override
   public void keyReleased(KeyEvent e) {

   }

   public void trashHandler() {
      if (grid[y][x] == 2) {
         inv++;
         grid[y][x]--;
      } else if (grid[y][x] == 3) {
         collected += inv;
         inv = 0;
      }
   }
}
