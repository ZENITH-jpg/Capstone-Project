import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public void paint(Graphics g){
        Graphics2D rect = (Graphics2D) g;
        menu(rect);
    }
    public void menu(Graphics2D g){
        Rectangle rect = new Rectangle();
        rect.setBounds(300,200,200,50);
        g.setColor(Color.white);
        g.fill(rect);
    }
}
