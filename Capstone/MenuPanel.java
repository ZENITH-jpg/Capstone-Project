
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuPanel extends JPanel implements KeyListener {
    Window window;
    Image background;
    Image logo;
    int index;
    final String[] options = {"start", "instructions", "leaderboard"};
    Image[][] menuItems = new Image[3][2];
    MenuPanel(Window w){
        window = w;
        index = 0;
        this.setLayout(null);
        this.setBounds(0,0,800,600);
        logo = new ImageIcon("assets/logo-modified.png").getImage(); // text logo for game
        background = new ImageIcon("assets/background.png").getImage(); // get image for background
        for (int i = 0; i < 3; i++) {
            menuItems[i][0] = new ImageIcon("assets/"+options[i]+".png").getImage(); //load menu images
            menuItems[i][1] = new ImageIcon("assets/"+options[i]+"active.png").getImage();
        }
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponents(g);
        g.drawImage(background,0,0, null); // set background to image
        g.drawImage(logo,125,40,null); // draw logo
        for (int i = 0; i < 3; i++) {
            if(index == i){
                g.drawImage(menuItems[i][1],50, 200+80*i,null); // draw selected option
            }else{
                g.drawImage(menuItems[i][0],50, 200+80*i,null); // draw unselected option
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
        else if(e.getKeyCode() == 87 || e.getKeyCode() == 38){
            index=(index+2)%3;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            window.startGame();
        }
        this.repaint(); // repaint changes
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
