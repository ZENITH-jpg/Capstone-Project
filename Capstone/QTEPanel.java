/*
Van N
2024-05-31
Mr Guglielmi
Class that controls the QTE popups
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

/**
JPanel that controls the QTE popups
@author Van N
@version 1.0
*/
public class QTEPanel extends JPanel implements MouseListener {
    static Random random = new Random();
    GamePanel game;
    Planet planet;
    ArrayList<String> chances; // determines the type of block the qtes are
    Timer qteTimer;
    public static int maxQTEs = 2; // max qtes that can appear on screen

    // GUI handling
    int qteSize = 50;
    ArrayList<JLabel> qteLabels = new ArrayList<JLabel>();

   /**
   Constructor
   @param g GamePanel
   @param p planet
   */
    QTEPanel(GamePanel g, Planet p) {
         game = g;
         planet = p;
         this.setOpaque(false); // makes transparent
         this.setBackground(new Color(0,0,0,0)); // makes transparent
        
         chances = new ArrayList<String>();
         for (Block b : p.getBlocks()) {
            chances.add(b.getName());
         }
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
        setQTETimer(4000);
    }
    
    /**
    Add a chance for a block qte to appear
    @param blockName name of block qte
    */
    public void addChance (String blockName) {
      chances.add(blockName);
    }
    
    /**
    Remove every chance of a specified block qte
    @param blockName name of block qte
    */
    public void removeChance(String blockName) {
      while (chances.contains(blockName)) {
         chances.remove(blockName);
      }
    }
       
       @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

   /**
   When a QTE is clicked, trigger the QTE effect
   */
    @Override
    public void mousePressed(MouseEvent e) {
       if (e.getSource() instanceof JLabel) {
          JLabel source = (JLabel) e.getSource();
          // qtes have a name "block_name=?"
          if (source.getName().startsWith("block_name=")) {
             source.removeMouseListener(this);
             this.remove(source);
             source.setVisible(false);
             String blockName = source.getName().substring(11);
             planet.getBlockWithName(blockName).doQTE();
             // Change name to clicked but don't delete
             source.setName("clicked");
             QTEPanel.this.revalidate();
             QTEPanel.this.repaint();
             game.updateLabels();
          }
       }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    /**
    Create and start a timer that spawns qtes
    @param cooldown spawnrate
    */
    public void setQTETimer(int cooldown) {
      if (qteTimer != null)
         qteTimer.stop();
      qteTimer = new Timer(cooldown, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // Delete a QTE when at maxQTEs
               if (qteLabels.size() > maxQTEs -1) {
                  int index = -1; // find the index of the first QTE not yet clicked
                  for (int i = 0; i < qteLabels.size(); i++) {
                      if (!qteLabels.get(i).getName().equals("clicked"))
                        index = i;
                  }
                  if (index != -1) {
                      JLabel qteLabel = qteLabels.get(index);
                      qteLabel.removeMouseListener(QTEPanel.this);
                      QTEPanel.this.remove(qteLabel);
                      qteLabels.remove(index); 
                      QTEPanel.this.revalidate();
                      QTEPanel.this.repaint();
                      // triggers fail QTE of corresponding block
                      String blockName = qteLabel.getName().substring(11);
                      planet.getBlockWithName(blockName).doFailedQTE();
                      game.updateLabels();
                  }
               }
               // Delete QTEs that are clicked
               for (int i = qteLabels.size()-1; i >= 0; i--) {
                  JLabel qteLabel = qteLabels.get(i);
                  if (qteLabel.getName().equals("clicked"))
                    qteLabels.remove(i);
               }
               // Make a QTE of a random block after every cooldown
                if (game.timerOn) {
                     // Get a random name from chances, then the corresponding block in planet
                     String blockName = chances.get(random.nextInt(chances.size()));
                     Block block = planet.getBlockWithName(blockName);
                     Image qteImg = new ImageIcon("assets/"+block.getType()+".png").getImage().getScaledInstance(qteSize, qteSize, Image.SCALE_DEFAULT);
                     ImageIcon qteIcon = new ImageIcon(qteImg);
                     JLabel qteLabel = new JLabel(qteIcon);
                     qteLabel.setBounds(50+random.nextInt(280),50+random.nextInt(280),qteSize,qteSize);
                     qteLabel.setName("block_name="+block.getName());
                     qteLabels.add(qteLabel);
                     QTEPanel.this.add(qteLabel);
                     qteLabel.addMouseListener(QTEPanel.this);
                     // QTEPanel.this.setComponentZOrder(qteLabel, 0); // move qte above planet label
                     QTEPanel.this.revalidate();
                     QTEPanel.this.repaint();
                }
            }
        });
        qteTimer.start();
    }
    
    /**
    Clear QTEs to prevent the error of deleting a block with qtes on screen
    @param blockName name of QTE to clear
    */
    public void clearQTEs (String blockName) {
      for (JLabel qteLabel : qteLabels) {
         if (qteLabel.getName().equals("block_name="+blockName)) {
            qteLabel.removeMouseListener(this);
            this.remove(qteLabel);
            qteLabels.remove(qteLabel); 
         }
      }
      this.revalidate();
      this.repaint();
    }
}
