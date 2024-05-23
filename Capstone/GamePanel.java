import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements MouseListener {
    static boolean windowBuildingMode = false;
    static Random random = new Random();
    Window window;
    Planet planet;
    QTEPanel qtePanel;

    // GUI handling
    JLabel planetLabel;
    int planetLabelSize = 300;
    JLabel[] blockRectLabels = new JLabel[0];
    JTextArea[] blockTextLabels = new JTextArea[0];
    JTextField[][] addBlockFields;
    JTextArea scoreLabel;
    JTextArea tempLabel;

    // Score handling
    ArrayList<Timer> timers = new ArrayList<Timer>();
    boolean timerOn = true;
    int scorePerTwoSeconds = 20;

    GamePanel(Window w) {
        window = w;
        planet = new Planet(this);
        qtePanel = new QTEPanel(this, planet);
        qtePanel.setBounds(0, 0, planetLabelSize+100, planetLabelSize+100);
        qtePanel.setOpaque(false); // makes transparent
        qtePanel.setBackground(new Color(0,0,0,0)); // makes transparent
        this.add(qtePanel);
        Image planetImg = new ImageIcon("assets/rocky_planet.png").getImage().getScaledInstance(planetLabelSize,
                planetLabelSize, Image.SCALE_DEFAULT);
        ImageIcon planetIcon = new ImageIcon(planetImg);
        planetLabel = new JLabel(planetIcon);
        planetLabel.setBounds(40, 40, planetLabelSize, planetLabelSize);
        this.add(planetLabel);
        ArrayList<JTextArea> permanentLabels = new ArrayList<JTextArea>();
        permanentLabels.add(Utils.gameHeadingPanel("Constitution of Planet", 40, 360, 300, 20));
        permanentLabels.add(Utils.gameHeadingPanel("Existing Creatures", 340, 360, 300, 20));
        permanentLabels.add(Utils.gameHeadingPanel("Objectives", 500, 20, 300, 20));
        for (JTextArea label : permanentLabels) {
            this.add(label);
        }
        scoreLabel = Utils.gameHeadingPanel("", 320, 20, 150, 20);
        tempLabel = Utils.gameHeadingPanel("", 320, 45, 150, 20);
        this.add(scoreLabel);
        this.add(tempLabel);
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
        if (windowBuildingMode)
            this.addMouseListener(this);
        initTimers();
        displayBlockLabels();
    }

    // creates coloured rectangles with sizes depending on the block volumes
    // precondition: there are no blocks with volumes <= 0
    public void displayBlockLabels() {
        // Remove previous block labels
        for (JLabel label : blockRectLabels) {
            label.setVisible(false);
            this.remove(label);
            label.removeMouseListener(this);
        }
        for (JTextArea label : blockTextLabels) {
            label.setVisible(false);
            this.remove(label);
        }
        blockRectLabels = new JLabel[planet.getBlocks().size()];
        blockTextLabels = new JTextArea[planet.getBlocks().size()];
        int panelX = 40;
        int panelY = 380;
        int totalWidth = 150;
        int totalHeight = 180;
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
            blockLabel1.setName("i=" + i);

            blockLabel2 = Utils.blockTextPanel(percentOfPlanet + "% " + currentBlock.getName(), panelX + totalWidth + 5,
                    panelY + currentHeight, 120, 70);
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
    
    public void updateLabels() {
      scoreLabel.setText(formatScore(planet.getScore()));
      tempLabel.setText(planet.getTemp() +" Celsius");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
            JLabel source = (JLabel) e.getSource();
            // rectangles have a name "i=?"
            if (source.getName().startsWith("i=")) {
                int i = Integer.parseInt(source.getName().substring(2));
                // Set a border when the mouse enters the label
                source.setBorder(new LineBorder(Color.RED, 2));
                // APPENDS the block property to the visible text beside the rectangle
                blockTextLabels[i]
                        .setText(blockTextLabels[i].getText() + "\n" + planet.getBlocks().get(i).getProperty());
                blockTextLabels[i].setOpaque(true);
                blockTextLabels[i].setBackground(Color.WHITE);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
            JLabel source = (JLabel) e.getSource();
            // rectangles have a name "i=?"
            if (source.getName().startsWith("i=")) {
                int i = Integer.parseInt(source.getName().substring(2));
                // Remove the border when the mouse exits the label
                source.setBorder(null);
                // REMOVES the block property to the visible text beside the rectangle
                int propertyLength = planet.getBlocks().get(i).getProperty().length();
                blockTextLabels[i].setText(blockTextLabels[i].getText()
                        .substring(0, blockTextLabels[i].getText().length() - propertyLength).replace("\n", ""));
                blockTextLabels[i].setOpaque(false);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      if (windowBuildingMode) {
           int x = e.getX();
           int y = e.getY();
           System.out.println(x + ", " + y);
        }
    }

    private void initTimers() {
        timers.add(new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timerOn) {
                    planet.addScore(scorePerTwoSeconds);
                    updateLabels();
                }
            }
        }));
        for (Timer t : timers) {
            t.start();
        }
    }

    private static String formatScore(int score) {
        return String.format("SCORE: %09d", score);
    }

    public static void main(String[] args) {
        new Window().startGame();
    }
}
