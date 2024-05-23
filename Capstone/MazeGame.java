import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeGame extends JPanel implements KeyListener {
    private Window window;
    private Planet planet;
    private int x;
    private int y;
    private int[][] grid = new int[7][7];
    public MazeGame(Window w){
        window = w; // standard init
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.addKeyListener(this);
        this.setBounds(0,0,800,600);
        this.setLayout(null);

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
