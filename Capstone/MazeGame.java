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

    private int[][] grid;
    public MazeGame(Window w){
        window = w; // standard init
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.addKeyListener(this);
        this.setBounds(0,0,800,600);
        this.setLayout(null);
        flag = 0;
        p = new ImageIcon("assets/playerico.png").getImage(); // getting images
        bg = new ImageIcon("assets/maze.png").getImage();
        trash = new ImageIcon("assets/trash.png").getImage();
        messageBg = new ImageIcon("assets/background.png").getImage();
        grid = new int[][]{{0, 0, 0, 0, 0, 1, 1, 0, 0, 0},
                           {0, 1, 1, 1, 0, 0, 1, 0, 1, 1},
                           {0, 1, 0, 0, 0, 0, 1, 0, 1, 0},
                           {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                           {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                           {1, 0, 0, 0, 1, 1, 1, 0, 1, 0},
                           {1, 1, 1, 0, 0, 0, 1, 0, 0, 0}};
        context = Utils.messagePanel("Use WASD or ARROW KEYS to move around the maze" +
                " collect 6 trash bags and recycle them at the recycling icon" +
                "\n\n",200,200,400,200);
    }
    public void setUp(){
        x = 3;
        y= 8;
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
    public void startGame(){
        repaint();
        while(dT<5000){
            dT = System.currentTimeMillis()-tS;
        }
        System.out.println("passed");
        dT = 0;
        flag = 1;
        this.remove(context);
        while (dT< 30000){
            dT = System.currentTimeMillis() - tS;
            repaint();
        }
    }
    @Override
    public void paintComponent(Graphics g){
        switch (flag){
            case 0:
                g.drawImage(messageBg,0,0,null);
                break;
            case 1:
                g.drawImage(bg,0,0,null);

            default:
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
}
