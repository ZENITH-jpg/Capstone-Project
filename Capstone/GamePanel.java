
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements MouseListener {
    Window window;
    JLabel label;
    Planet planet;
    JLabel[][] blockLabels = new JLabel[0][0];
    JTextField[][] addBlockFields;

    GamePanel(Window w) {
        window = w;
        planet = new Planet();
        // label = new JLabel("this has text");
        // label.setBounds(0, 0, 200, 150);
        // this.add(label);
        // label.addMouseListener(this);
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
        displayBlockLabels();
    }

    // creates coloured rectangles with sizes depending on the proportion of block
    // volumes
    // precondition: there are no blocks with volumes <= 0
    public void displayBlockLabels() {
        // Remove previous ones
        for (JLabel[] labels : blockLabels) {
            for (JLabel label : labels) {
                this.remove(label);
                label.removeMouseListener(this);
            }
        }
        blockLabels = new JLabel[planet.getBlocks().size()][2];
        int panelX = 50;
        int panelY = 50;
        int totalWidth = 200;
        int totalHeight = 300;
        int currentHeight = 0;
        for (int i = 0; i < blockLabels.length; i++) {
            Block currentBlock = planet.getBlocks().get(i);
            int percentOfPlanet = (int) (100 * (double) currentBlock.getVolume() / planet.getTotalVolume());
            JLabel blockLabel1 = new JLabel(); // coloured rectangle
            JLabel blockLabel2 = new JLabel(); // text with block's information
            blockLabel1.setBounds(panelX, panelY + currentHeight, totalWidth, percentOfPlanet * totalHeight / 100);
            blockLabel1.setOpaque(true);
            blockLabel1.setBackground(Utils.colorOfBlockType(currentBlock.getType(), currentBlock.getName()));
            blockLabel1.setForeground(Utils.colorOfBlockType(currentBlock.getType(), currentBlock.getName()));
            blockLabel1.setText("i=" + i); // an invisible text

            blockLabel2.setBounds(panelX + totalWidth, panelY + currentHeight, totalWidth, 20);
            blockLabel2.setText(percentOfPlanet + "% " + currentBlock.getName());
            currentHeight += percentOfPlanet * totalHeight / 100;
            blockLabels[i][0] = blockLabel1;
            blockLabels[i][1] = blockLabel2;
            this.add(blockLabels[i][0]);
            this.add(blockLabels[i][1]);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Set a border when the mouse enters the label
        JLabel source = (JLabel) e.getSource();
        source.setBorder(new LineBorder(Color.RED, 2));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        // Remove the border when the mouse exits the label
        source.setBorder(null);
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
