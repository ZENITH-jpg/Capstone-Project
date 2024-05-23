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
    static boolean windowBuildingMode = true;
    static Random random = new Random();
    Window window;
    Planet planet;

    // GUI handling
    JLabel planetLabel;
    int planetLabelSize = 300;
    int qteSize = 50;
    JLabel[] blockRectLabels = new JLabel[0];
    JTextArea[] blockTextLabels = new JTextArea[0];
    JTextField[][] addBlockFields;
    ArrayList<JLabel> qteLabels = new ArrayList<JLabel>();
    JTextArea scoreLabel;

    // Score handling
    ArrayList<Timer> timers = new ArrayList<Timer>();
    boolean timerOn = true;
    int scorePerTwoSeconds = 20;

    GamePanel(Window w) {
        window = w;
        planet = new Planet(this);
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
        scoreLabel = Utils.gameHeadingPanel("SCORE:", 320, 20, 150, 20);
        this.add(scoreLabel);
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
        if (windowBuildingMode)
            this.addMouseListener(this);
        initTimers();
        displayBlockLabels();
    }

    // @Override
    // public void paintComponent(Graphics g){
    // Graphics2D g2d = (Graphics2D)g;
    // super.paintComponents(g2d);
    // g2d.setColor(Color.BLACK);
    // g2d.fillRect(0,0, 800, 600);
    // g2d.drawImage(planetIcon,0,0, null); // draw planet
    // }

    // creates coloured rectangles with sizes depending on the proportion of block
    // volumes
    // precondition: there are no blocks with volumes <= 0
    public void displayBlockLabels() {
        // Remove previous ones
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
        if (e.getSource() instanceof JLabel) {
            JLabel source = (JLabel) e.getSource();
            // qtes have a name "block_name=?"
            if (source.getName().startsWith("block_name=")) {
               source.removeMouseListener(this);
               qteLabels.remove(source);
               this.remove(source);
               source.setVisible(false);
               String blockName = source.getName().substring(11);
               planet.getBlocks().get(planet.findBlock(blockName)).doQTE();
               GamePanel.this.revalidate();
               GamePanel.this.repaint();
            }
        }

    }

    private void initTimers() {
        timers.add(new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timerOn) {
                    planet.addScore(scorePerTwoSeconds);
                    scoreLabel.setText(formatScore(planet.getScore()));
                }
            }
        }));
        timers.add(new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timerOn && random.nextInt(3) < 2) {
                     // 66% chance to make a QTE of random block every 3 sec
                     int index = random.nextInt(planet.getBlocks().size());
                     Block block = planet.getBlocks().get(index);
                     Image qteImg = new ImageIcon("assets/"+block.getType()+".png").getImage().getScaledInstance(qteSize, qteSize, Image.SCALE_DEFAULT);
                     ImageIcon qteIcon = new ImageIcon(qteImg);
                     JLabel qteLabel = new JLabel(qteIcon);
                     qteLabel.setBounds(50+random.nextInt(280),50+random.nextInt(280),qteSize,qteSize);
                     qteLabel.setName("block_name="+block.getName());
                     qteLabels.add(qteLabel);
                     GamePanel.this.add(qteLabel);
                     qteLabel.addMouseListener(GamePanel.this);
                     GamePanel.this.setComponentZOrder(qteLabel, 0); // move qte above planet label
                     GamePanel.this.revalidate();
                     GamePanel.this.repaint();
                }
               if (qteLabels.size() > 2) {
                  // removes a QTE when at least 1 other QTE shows up
                  JLabel qteLabel = qteLabels.get(0);
                  qteLabel.removeMouseListener(GamePanel.this);
                  GamePanel.this.remove(qteLabel);
                  qteLabels.remove(0); 
                  GamePanel.this.revalidate();
                  GamePanel.this.repaint();
                  // triggers fail QTE of corresponding block
                  String blockName = qteLabel.getName().substring(11);
                  planet.getBlocks().get(planet.findBlock(blockName)).doFailedQTE();
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
