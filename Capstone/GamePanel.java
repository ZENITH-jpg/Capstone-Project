/*
Van N
2024-05-24
Mr Guglielmi
Manage the window when the game has started.
*/

import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This panel is visible when the game has started
 *
 * @author Van N
 * @version 1.0
 */
public class GamePanel extends JPanel implements MouseListener {
   static boolean windowBuildingMode = false;
   static Random random = new Random();
   Window window;
   Planet planet;
   Timer scoreTimer;

   // Graphics and Panels
   Image background;
   Image gameOverBackground;
   QTEPanel qtePanel;
   ObjectivePanel objPanel;

   // GUI handling
   JLabel planetLabel = new JLabel();
   int planetLabelSize = 300;
   int tempScale = 2;
   JLabel[] blockRectLabels = new JLabel[0];
   JTextArea[] blockTextLabels = new JTextArea[0];
   JTextArea blockPropertyLabel;
   JTextArea[] creatureTextLabels = new JTextArea[Planet.maxCreatures];
   JTextArea humanLabel;
   JTextField[][] addBlockFields;
   JTextArea scoreLabel;
   JLabel tempLabel;
   JTextArea tempRectLabel;
   JTextArea nameInputLabel;

   // Static variables
   public static boolean timerOn;
   public static int difficulty; // 0 is easy, 1 is medium, and so on
   public static int scorePerTwoSeconds;

   /**
    * Constructor of GamePanel
    *
    * @param w the window that's running
    */
   GamePanel(Window w) {
      window = w;
      planet = new Planet(this);
      this.setLayout(null);
      this.setBounds(0, 0, 800, 600);
      // Reset static variables
      timerOn = true;
      difficulty = 1;
      scorePerTwoSeconds = 20;
      // Add QTEPanel, ObjectivePanel, temperature, and background
      qtePanel = new QTEPanel(this, planet);
      qtePanel.setBounds(0, 0, planetLabelSize + 100, planetLabelSize + 100);
      objPanel = new ObjectivePanel(this, planet);
      this.add(qtePanel);
      this.add(objPanel);
      Image tempImg = new ImageIcon("assets/thermometer.png").getImage().getScaledInstance(80 / tempScale, 150 / tempScale, Image.SCALE_DEFAULT);
      ImageIcon tempIcon = new ImageIcon(tempImg);
      tempLabel = new JLabel(tempIcon);
      tempLabel.setBounds(340, 40, 80 / tempScale, 150 / tempScale);
      this.add(tempLabel);
      tempRectLabel = new JTextArea();
      tempRectLabel.setEditable(false);
      tempRectLabel.setBackground(new Color(244,67,54));
      this.add(tempRectLabel);
      this.setComponentZOrder(tempRectLabel, 0);
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
      humanLabel = Utils.blockTextPanel("", 340, 390, 300, 20);
      this.add(scoreLabel);
      this.add(humanLabel);
      // Init specific to blocks, creatures, and timer
      for (int i = 0; i < creatureTextLabels.length; i++) {
         creatureTextLabels[i] = Utils.blockTextPanel("", 340, 410 + 18 * i, 300, 20);
         this.add(creatureTextLabels[i]);
      }
      if (windowBuildingMode)
         this.addMouseListener(this);
      initTimers();
      blockPropertyLabel = Utils.blockTextPanel("", 195, 400, 140, 140);
      blockPropertyLabel.setOpaque(true);
      blockPropertyLabel.setBackground(Color.BLACK);
      blockPropertyLabel.setVisible(false);
      this.add(blockPropertyLabel);
      displayBlockLabels();
   }

   /**
    * Creates coloured rectangles with sizes depending on the block volumes. There shouldn't be any blocks with volumes less than 0
    */
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
      int totalHeight = 150;
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
         blockLabel2.setEditable(false);
         // Add block labels
         blockRectLabels[i] = blockLabel1;
         blockTextLabels[i] = blockLabel2;
         this.add(blockRectLabels[i]);
         this.add(blockTextLabels[i]);
         blockRectLabels[i].addMouseListener(this);
      }
   }

   /**
    * Displays the populations of creatures, displaying "EXTINCT" if a creature is extinct
    */
   public void displayCreatureLabels() {
      for (int i = 0; i < planet.getCreatures().size(); i++) {
         int population = planet.getCreatures().get(i).getPopulation();
         if (population != 0)
            creatureTextLabels[i].setText(population + " " + planet.getCreatures().get(i).getSpecies());
         else
            creatureTextLabels[i].setText("EXTINCT");
      }
   }

   /**
    * Updates the score, temperature, population label displays. Also checks if objectives are complete
    */
   public void updateLabels() {
      // update labels unless it's game over
      if (!checkGameOver()) {
         scoreLabel.setText(formatScore(planet.getScore()));
         humanLabel.setText(planet.getHumans() + " Humans");

         // update thermometer
         int tempHeight = 146 / tempScale - 30;
         double tempLabelHeight = tempHeight / 300.0 * planet.getTemp();
         // make sure height isnt negative
         if (planet.getTemp() <= 0)
            tempLabelHeight = 0;
         double tempLabelY = 45 + tempHeight - tempLabelHeight;
         tempRectLabel.setBounds(355, (int) tempLabelY, 70 / tempScale - 24, (int) tempLabelHeight);

         displayCreatureLabels();
         objPanel.checkAllObjectives(); // Update objectives
         objPanel.displayObjectives();
         this.revalidate();
      } else {
         // Show game over screen for a few seconds
         scoreTimer.stop();
         qtePanel.stopQTETimer();
         JPanel gameOverPanel = new JPanel();
         gameOverPanel.setBounds(0, 0, 800, 600);
         gameOverPanel.setLayout(null);
         gameOverPanel.setOpaque(false); // transparent to see bg
         gameOverPanel.setBackground(new Color(0, 0, 0, 0)); // transparent to see bg
         JTextArea nameInstructionsLabel = Utils.messagePanel("Input your name below so we can save your score! Name must be 6 characters or less.", 150, 290, 500, 100);
         nameInputLabel = Utils.messagePanel("", 300, 440, 200, 60);
         nameInputLabel.setEditable(true);
         nameInputLabel.setForeground(Color.black);
         nameInputLabel.setBackground(Color.white);
         nameInputLabel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
         clearGamePanel();
         gameOverPanel.add(nameInstructionsLabel);
         gameOverPanel.add(nameInputLabel);
         nameInputLabel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
               if (e.getKeyCode() == KeyEvent.VK_ENTER && checkGameOver() && nameInputLabel.getText().length() <= 6 && nameInputLabel.getText().strip().length() > 0) {
                  window.addToLeaderboard(nameInputLabel.getText().strip(), planet.getScore());
               }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
         });
         gameOverPanel.requestFocusInWindow();
         this.add(gameOverPanel);
         this.setComponentZOrder(gameOverPanel, 0);
      }
   }

   private void clearGamePanel() {
      Component[] components = getComponents();
      for (Component component : components) {
         remove(component);
         //if (component instanceof JLabel) {
         //    remove(component); // Remove the JLabel
         //}
         //if (component instanceof JTextArea) {
         //    remove(component); // Remove the JTextArea
         //}
      }
      this.revalidate(); // Refresh the panel layout
      this.repaint(); // Repaint the panel to reflect the changes
   }

   /**
    * Checks if the game is over; max temperature reached or no creatures left
    *
    * @return the game over status as a boolean
    */
   public boolean checkGameOver() {
      // if temperature is over 300 degrees
      if (planet.getTemp() >= 300)
         return true;
      // if there are no creatures except humans
      if (planet.getHumans() > 0) {
         for (Creature creature : planet.getCreatures()) {
            if (creature.getPopulation() > 0)
               return false;
         }
         return true;
      } else
         return false;
   }

   /**
    * When the mouse hovers over a block rectangle label, change the properties of the label
    */
   @Override
   public void mouseEntered(MouseEvent e) {
      if (e.getSource() instanceof JLabel) {
         JLabel source = (JLabel) e.getSource();
         // rectangles have a name "i=?"
         int i = Integer.parseInt(source.getName().substring(2));
         // Set a border when the mouse enters the label
         source.setBorder(new LineBorder(Color.RED, 2));
         // APPENDS the block property to the block property label
         blockPropertyLabel.setText(blockTextLabels[i].getText() + "\n" + planet.getBlockAtIndex(i).getProperty());
         blockPropertyLabel.setVisible(true);
      }
   }

   /**
    * When the mouse exits a block rectangle label, change the properties of the label
    */
   @Override
   public void mouseExited(MouseEvent e) {
      if (e.getSource() instanceof JLabel) {
         JLabel source = (JLabel) e.getSource();
         // rectangles have a name "i=?"
         int i = Integer.parseInt(source.getName().substring(2));
         // Remove the border when the mouse exits the label
         source.setBorder(null);
         // Disables block property label
         blockPropertyLabel.setVisible(false);
      }
   }

   @Override
   public void mouseReleased(MouseEvent e) {
   }

   @Override
   public void mousePressed(MouseEvent e) {
   }

   /**
    * When the mouse is clicked and program is in windowBuildingMode, print the mouse coordinates
    */
   @Override
   public void mouseClicked(MouseEvent e) {
      if (windowBuildingMode) { // prints the mouse coords if needed
         int x = e.getX();
         int y = e.getY();
         System.out.println(x + ", " + y);
      }
   }

   /**
    * Paints the background of the game
    */
   @Override
   public void paintComponent(Graphics g) { // screen
      Graphics2D g2d = (Graphics2D) g;
      Color originalColor = g2d.getColor();
      Stroke originalStroke = g2d.getStroke();
      RenderingHints originalHints = g2d.getRenderingHints();
      if (!checkGameOver())
         g2d.drawImage(background, 0, 0, null); //bg
      else {
         if (planet.getTemp() >= 300) {
            gameOverBackground = new ImageIcon("assets/temp_gameover.png").getImage();
            g2d.drawImage(gameOverBackground, 0, 0, null); // game over bg
            GlyphVector glyphVector = Utils.PIXEL.deriveFont(80f).createGlyphVector(g2d.getFontRenderContext(), "GAME OVER");
            drawText(g2d, glyphVector, 150, 100, new Color(255, 132, 0));
            glyphVector = Utils.PIXEL.deriveFont(80f).createGlyphVector(g2d.getFontRenderContext(), "LAVA ENDING");
            drawText(g2d, glyphVector, 105, 190, new Color(255, 132, 0));
         } else {
            gameOverBackground = new ImageIcon("assets/extinct_gameover.png").getImage();
            g2d.drawImage(gameOverBackground, 0, 0, null); // game over bg
            GlyphVector glyphVector = Utils.PIXEL.deriveFont(78f).createGlyphVector(g2d.getFontRenderContext(), "GAME OVER");
            drawText(g2d, glyphVector, 150, 100, new Color(71, 40, 224));
            glyphVector = Utils.PIXEL.deriveFont(78f).createGlyphVector(g2d.getFontRenderContext(), "EXTINCT ENDING");
            drawText(g2d, glyphVector, 8, 190, new Color(71, 40, 224));
         }
      }
      g2d.setColor(originalColor);
      g2d.setStroke(originalStroke);
      g2d.setRenderingHints(originalHints);
   }

   private void drawText(Graphics2D g2d, GlyphVector glyphVector, int x, int y, Color c) {
      AffineTransform at = new AffineTransform();
      at.translate(x, y);
      Shape textShape = at.createTransformedShape(glyphVector.getOutline());
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);

      g2d.setColor(Color.black);
      g2d.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
      g2d.draw(textShape); // draw outline

      g2d.setColor(c);
      g2d.fill(textShape);
   }

   /**
    * Set the image of planet icon
    *
    * @param file path to image file
    */
   public void setPlanetLabel(String file) {
      this.remove(planetLabel);
      Image planetImg = new ImageIcon(file).getImage().getScaledInstance(planetLabelSize,
            planetLabelSize, Image.SCALE_DEFAULT);
      ImageIcon planetIcon = new ImageIcon(planetImg);
      planetLabel = new JLabel(planetIcon);
      planetLabel.setBounds(40, 40, planetLabelSize, planetLabelSize);
      this.add(planetLabel);
   }

   /**
    * start the timer to add score
    */
   private void initTimers() {
      // this timer adds score every 2 sec
      scoreTimer = new Timer(2000, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            if (timerOn) {
               int inc = 0;
               int tempinc = 0;
               ArrayList<Block> b = planet.getBlocks();
               for (int i = 0; i < b.size(); i++) {
                  if(b.get(i).getName().equalsIgnoreCase("rock") || b.get(i).getName().equalsIgnoreCase("water"))
                     inc += b.get(i).getVolume()/100;
                  else if(b.get(i).getName().equalsIgnoreCase("ice") || b.get(i).getName().equalsIgnoreCase("soil")){
                     inc += b.get(i).getVolume()/50;
                  }else if(b.get(i).getName().equalsIgnoreCase("lava") || b.get(i).getName().equalsIgnoreCase("smog")) {
                     inc -= b.get(i).getVolume() / 100;
                     tempinc += b.get(i).getVolume()/300;
                  }
               }
               ArrayList<Creature> c = planet.getCreatures();
               for (int i = 0; i < c.size(); i++) {
                  if(c.get(i).getPopulation()>0){
                     inc+=10;
                  }
               }
               planet.addScore(Math.max(scorePerTwoSeconds+inc,0));
               updateLabels();
               tempinc*=difficulty;
               planet.addTemp(tempinc);
            }
         }
      });
      scoreTimer.start();
   }

   /**
    * Add six leading zeros to the current score
    *
    * @return formatted score
    */
   private static String formatScore(int score) {
      // adds six leading zeros to the score
      return String.format("SCORE: %06d", score);
   }

   /**
    * Get planet
    *
    * @return planet
    */
   public Planet getPlanet() {
      return planet;
   }

   /**
    * Get qte panel
    *
    * @return QTEPanel
    */
   public QTEPanel getQTEPanel() {
      return qtePanel;
   }

   /**
    * Get objective panel
    *
    * @return ObjectivePanel
    */
   public ObjectivePanel getObjectivePanel() {
      return objPanel;
   }

   /**
    * Tell the window to start a random minigame
    */
   public void startRandomMinigame() {
      window.startRandomMinigame();
   }

   /**
    * Get window
    *
    * @return Window
    */
   public Window getWindow() {
      return window;
   }

   public static void main(String[] args) {
      new Window().startGame();
   }
}
