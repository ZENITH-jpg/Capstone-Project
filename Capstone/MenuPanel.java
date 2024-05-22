
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuPanel extends JPanel implements KeyListener {
    Window window;
    Image background;  // assets
    Image logo;
    int index;
    final String[] options = {"start", "instructions", "leaderboard"}; //menu selection
    Image[][] menuItems = new Image[3][2]; //image array for menu items [item choice][is item active]
    final String guide = "Use arrow keys or WASD to navigate\nSPACE or ENTER to select, ESC to exit game at any time.";

    MenuPanel(Window w){
        window = w; // default init
        index = 0;
        this.setLayout(null);
        this.setFocusable(true);
        this.setBounds(0,0,800,600);
        this.addKeyListener(this);
        logo = new ImageIcon("assets/logo-modified.png").getImage(); // text logo for game
        background = new ImageIcon("assets/background.png").getImage(); // get image for background
        for (int i = 0; i < 3; i++) {
            menuItems[i][0] = new ImageIcon("assets/"+options[i]+".png").getImage(); //load menu images
            menuItems[i][1] = new ImageIcon("assets/"+options[i]+"active.png").getImage();
        }
        JTextArea message = Utils.messagePanel(guide,50,490,700,70);
        this.add(message);
    }
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponents(g2d);
        g2d.drawImage(background,0,0, null); // set background to image
        g2d.drawImage(logo,125,40,null); // draw logo
        for (int i = 0; i < 3; i++) {
            if(index == i){
                g2d.drawImage(menuItems[i][1],50, 200+80*i,null); // draw selected option
            }else{
                g2d.drawImage(menuItems[i][0],50, 200+80*i,null); // draw unselected option
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) { // if down or s key pressed, go down in options
        if(e.getKeyCode() == 83 || e.getKeyCode() == 40){
            index=(index+1)%3;
        }
        else if(e.getKeyCode() == 87 || e.getKeyCode() == 38){ // if up or w key is pressed, go up
            index=(index+2)%3; // +2 because -1+3 = +2 (congruent for modulo)
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (index){
                case 0:
                    window.startGame();
                    break;
                case 1:
                    break;
                default:
                    window.showLeaderboard();
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            window.close();
        }
        this.repaint(); // repaint changes
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
