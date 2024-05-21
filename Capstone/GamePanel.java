
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements MouseListener {
    Window window;
    JLabel label;
    Planet planet;
    JLabel[] blockRectLabels = new JLabel[0];
    JTextArea[] blockTextLabels = new JTextArea[0];
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
        for (JLabel label : blockRectLabels) {
               this.remove(label);
               label.removeMouseListener(this);
        }
        for (JTextArea label : blockTextLabels) {
               this.remove(label);
        }
        blockRectLabels = new JLabel[planet.getBlocks().size()];
        blockTextLabels = new JTextArea[planet.getBlocks().size()];
        int panelX = 50;
        int panelY = 50;
        int totalWidth = 200;
        int totalHeight = 300;
        int currentHeight = 0;
        for (int i = 0; i < blockTextLabels.length; i++) {
            Block currentBlock = planet.getBlocks().get(i);
            int percentOfPlanet = (int) (100 * (double) currentBlock.getVolume() / planet.getTotalVolume());
            JLabel blockLabel1 = new JLabel(); // coloured rectangle
            JTextArea blockLabel2 = new JTextArea(); // text with block's information
            blockLabel1.setBounds(panelX, panelY + currentHeight, totalWidth, percentOfPlanet * totalHeight / 100);
            blockLabel1.setOpaque(true);
            blockLabel1.setBackground(Utils.colorOfBlockType(currentBlock.getType(), currentBlock.getName()));
            blockLabel1.setForeground(Utils.colorOfBlockType(currentBlock.getType(), currentBlock.getName()));
            blockLabel1.setText("i=" + i); // an invisible text

            blockLabel2 = Utils.blockTextPanel(percentOfPlanet + "% " + currentBlock.getName(), panelX + totalWidth, panelY + currentHeight, totalWidth, 20);
            //blockLabel2.setBounds();
            //blockLabel2.setText();
            currentHeight += percentOfPlanet * totalHeight / 100;
            blockRectLabels[i] = blockLabel1;
            blockTextLabels[i] = blockLabel2;
            
            blockTextLabels[i].setEditable(false);
            blockTextLabels[i].setLineWrap(true);

            this.add(blockRectLabels[i]);
            this.add(blockTextLabels[i]);
            blockRectLabels[i].addMouseListener(this);
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Set a border when the mouse enters the label
        JLabel source = (JLabel) e.getSource();
        source.setBorder(new LineBorder(Color.RED, 2));
        // rectangles have a hidden string "i=?"
        int i = Integer.parseInt(source.getText().substring(2)); 
        // APPENDS the block property to the visible text beside the rectangle
        blockTextLabels[i].setText(blockTextLabels[i].getText()+"\n"+planet.getBlocks().get(i).getProperty());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        // Remove the border when the mouse exits the label
        source.setBorder(null);
        // rectangles have a hidden string "i=?"
        int i = Integer.parseInt(source.getText().substring(2)); 
        // REMOVES the block property to the visible text beside the rectangle
        int propertyLength = planet.getBlocks().get(i).getProperty().length();
        blockTextLabels[i].setText(blockTextLabels[i].getText().substring(0, blockTextLabels[i].getText().length() - propertyLength).replace("\n",""));
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
