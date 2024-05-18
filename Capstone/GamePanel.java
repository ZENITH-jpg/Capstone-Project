
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class GamePanel extends JPanel implements MouseListener {
    Window window;
    JLabel label;

    GamePanel(Window w) {
        window = w;
        label = new JLabel("this has text");
        label.setBounds(0, 0, 200, 150);
        this.add(label);
        label.addMouseListener(this);
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Set a border when the mouse enters the label
        label.setBorder(new LineBorder(Color.RED, 2));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Remove the border when the mouse exits the label
        label.setBorder(null);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
