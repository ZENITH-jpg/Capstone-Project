import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    Image background;
    Image logo;
    MenuPanel(){
        this.setBounds(0,0,800,600);
        logo = new ImageIcon("assets/logo-modified.png").getImage();
        background = new ImageIcon("assets/background.png").getImage(); // get image for background
    }
    @Override
    public void paint(Graphics g){
        g.drawImage(background,0,0, null); // set background to image
        g.drawImage(logo,180,40,null);
        g.drawRect(300,200,200,50);
    }
}
