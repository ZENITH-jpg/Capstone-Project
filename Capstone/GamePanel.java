/*
Van N
2024-05-24
Mr Guglielmi
Manage the window when the game has started.
*/
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements MouseListener{
    static boolean windowBuildingMode = false;
    static Random random = new Random();
    Window window;
    Planet planet;

    // Graphics and Panels
    Image background;
    QTEPanel qtePanel;
    ObjectivePanel objPanel;
    
    // GUI handling
    JLabel planetLabel = new JLabel();
    int planetLabelSize = 300;
    JLabel[] blockRectLabels = new JLabel[0];
    JTextArea[] blockTextLabels = new JTextArea[0];
    JTextArea[] creatureTextLabels = new JTextArea[Planet.maxCreatures];
    JTextArea humanLabel;
    JTextField[][] addBlockFields;
    JTextArea scoreLabel;
    JTextArea tempLabel;

    // Static variables
    public static boolean timerOn = true;
    public static int difficulty = 0; // 0 is easy, 1 is medium, and so on
    public static int scorePerTwoSeconds = 20;
    public static boolean creaturesCanAppear = false; // updated by completing objectives

    GamePanel(Window w) {
        window = w;
        planet = new Planet(this);
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
        // Add QTEPanel to this panel
        qtePanel = new QTEPanel(this, planet);
        qtePanel.setBounds(0, 0, planetLabelSize+100, planetLabelSize+100);
        qtePanel.setOpaque(false); // makes transparent
        qtePanel.setBackground(new Color(0,0,0,0)); // makes transparent
        this.add(qtePanel);
        // Add ObjectivePanel to this panel
        objPanel = new ObjectivePanel(this, planet);
        objPanel.setBounds(500, 40, 280, 400);
        objPanel.setOpaque(false); // makes transparent
        objPanel.setBackground(new Color(0,0,0,0)); // makes transparent
        this.add(objPanel);
        // Add background
        background = new ImageIcon("assets/space_bg.png").getImage();
        // Add JLabels
        this.setPlanetLabel("assets/rocky_planet.png");
        ArrayList<JTextArea> permanentLabels = new ArrayList<JTextArea>();
        permanentLabels.add(Utils.gameHeadingPanel("Constitution of Planet", 40, 360, 300, 20));
        permanentLabels.add(Utils.gameHeadingPanel("Existing Creatures", 340, 360, 300, 20));
        permanentLabels.add(Utils.gameHeadingPanel("Objectives", 500, 20, 300, 20));
        for (JTextArea label : permanentLabels) {
            this.add(label);
        }
        scoreLabel = Utils.gameHeadingPanel("", 320, 20, 150, 20);
        tempLabel = Utils.gameHeadingPanel("", 320, 45, 150, 20);
        humanLabel = Utils.blockTextPanel("", 340, 390, 300, 20);
        this.add(scoreLabel);
        this.add(tempLabel);
        this.add(humanLabel);
        for (int i = 0; i < creatureTextLabels.length; i++) {
         creatureTextLabels[i] = Utils.blockTextPanel("", 340, 410 + 18*i, 300, 20);
         this.add(creatureTextLabels[i]);
        }
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
        int panelX = 40; // x, y coords of whole constitution display
        int panelY = 380;
        int totalWidth = 150; // width, height of constitution display
        int totalHeight = 180;
        int currentHeight = 0;
        for (int i = 0; i < blockTextLabels.length; i++) {
            Block currentBlock = planet.getBlockAtIndex(i);
            int percentOfPlanet = (int) (100 * (double) currentBlock.getVolume() / planet.getTotalVolume());
            JLabel blockLabel1 = new JLabel(); // coloured rectangle
            JTextArea blockLabel2 = new JTextArea(); // text with block's information
            // set height of rectangle to block's percent of planet
            blockLabel1.setBounds(panelX, panelY + currentHeight, totalWidth, percentOfPlanet * totalHeight / 100);
            blockLabel1.setOpaque(true);
            blockLabel1.setBackground(Utils.colorOfBlockType(currentBlock.getType(), currentBlock.getName()));
            blockLabel1.setName("i=" + i);

            blockLabel2 = Utils.blockTextPanel(percentOfPlanet + "% " + currentBlock.getName() + " (" + currentBlock.getVolume() + ")", panelX + totalWidth + 5,
                    panelY + currentHeight, 140, 140);
            currentHeight += percentOfPlanet * totalHeight / 100;
            // Add block labels
            blockRectLabels[i] = blockLabel1;
            blockTextLabels[i] = blockLabel2;
            blockTextLabels[i].setEditable(false);
            blockTextLabels[i].setLineWrap(true);
            this.add(blockRectLabels[i]);
            this.add(blockTextLabels[i]);
            blockRectLabels[i].addMouseListener(this);
        }
    }
    
    public void displayCreatureLabels() {
      for (int i = 0; i < planet.getCreatures().size(); i++) {
         creatureTextLabels[i].setText(planet.getCreatures().get(i).getPopulation()+" "+planet.getCreatures().get(i).getSpecies());
      }
    }
    
    public void updateLabels() {
      scoreLabel.setText(formatScore(planet.getScore()));
      tempLabel.setText(planet.getTemp() +" Celsius");
      humanLabel.setText(planet.getHumans() + " Humans");
      displayCreatureLabels();
      objPanel.checkAllObjectives(); // Update objectives
      objPanel.displayObjectives();
    }
    
    
    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
            JLabel source = (JLabel) e.getSource();
            // rectangles have a name "i=?"
             int i = Integer.parseInt(source.getName().substring(2));
             // Set a border when the mouse enters the label
             source.setBorder(new LineBorder(Color.RED, 2));
             // APPENDS the block property to the visible text beside the rectangle
             blockTextLabels[i]
                     .setText(blockTextLabels[i].getText() + "\n" + planet.getBlockAtIndex(i).getProperty());
             blockTextLabels[i].setOpaque(true);
             blockTextLabels[i].setBackground(Color.BLACK);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {
             JLabel source = (JLabel) e.getSource();
             // rectangles have a name "i=?"
             int i = Integer.parseInt(source.getName().substring(2));
             // Remove the border when the mouse exits the label
             source.setBorder(null);
             // REMOVES the block property to the visible text beside the rectangle
             int propertyLength = planet.getBlockAtIndex(i).getProperty().length();
             blockTextLabels[i].setText(blockTextLabels[i].getText()
                     .substring(0, blockTextLabels[i].getText().length() - propertyLength).replace("\n", ""));
             blockTextLabels[i].setOpaque(false);
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
      if (windowBuildingMode) { // prints the mouse coords if needed
           int x = e.getX();
           int y = e.getY();
           System.out.println(x + ", " + y);
      }
    }

    @Override
    public void paintComponent(Graphics g) { // screen
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(background,0,0,null); //bg
    }
    
    public void setPlanetLabel(String file) {
        this.remove(planetLabel);
        Image planetImg = new ImageIcon(file).getImage().getScaledInstance(planetLabelSize,
                planetLabelSize, Image.SCALE_DEFAULT);
        ImageIcon planetIcon = new ImageIcon(planetImg);
        planetLabel = new JLabel(planetIcon);
        planetLabel.setBounds(40, 40, planetLabelSize, planetLabelSize);
        this.add(planetLabel);
    } 
    
    private void initTimers() {
        // this timer adds score every 2 sec
        new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timerOn) {
                    planet.addScore(scorePerTwoSeconds);
                    updateLabels();
                }
            }
        }).start();
    }

    private static String formatScore(int score) {
      // adds nine leading zeros to the score
      return String.format("SCORE: %09d", score);
    }
    
    public Planet getPlanet() {
      return planet;
   }
    
    public QTEPanel getQTEPanel() {
      return qtePanel;
   }
   
   public ObjectivePanel getObjectivePanel() {
      return objPanel;
   }
   
   public void startMinigame(Minigame minigame) {
      window.startMinigame(minigame);
   }

    public static void main(String[] args) {
        new Window().startGame();
    }
}
