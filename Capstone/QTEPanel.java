import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QTEPanel extends JPanel implements MouseListener {
    static Random random = new Random();
    GamePanel game;
    Planet planet;

    // GUI handling
    int qteSize = 50;
    ArrayList<JLabel> qteLabels = new ArrayList<JLabel>();

    // Score handling
    ArrayList<Timer> timers = new ArrayList<Timer>();

    QTEPanel(GamePanel g, Planet p) {
         game = g;
         planet = p;
        this.setLayout(null);
        this.setBounds(0, 0, 800, 600);
        initTimers();
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

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
               QTEPanel.this.revalidate();
               QTEPanel.this.repaint();
            }
        }
    }

    private void initTimers() {
        timers.add(new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // Make a QTE of a random block every 4 sec
                if (game.timerOn) {
                     // Randomness is skewed by block volume
                     Block block = planet.randomWeightedBlock();
                     Image qteImg = new ImageIcon("assets/"+block.getType()+".png").getImage().getScaledInstance(qteSize, qteSize, Image.SCALE_DEFAULT);
                     ImageIcon qteIcon = new ImageIcon(qteImg);
                     JLabel qteLabel = new JLabel(qteIcon);
                     qteLabel.setBounds(50+random.nextInt(280),50+random.nextInt(280),qteSize,qteSize);
                     qteLabel.setName("block_name="+block.getName());
                     qteLabels.add(qteLabel);
                     QTEPanel.this.add(qteLabel);
                     qteLabel.addMouseListener(QTEPanel.this);
                     QTEPanel.this.setComponentZOrder(qteLabel, 0); // move qte above planet label
                     QTEPanel.this.revalidate();
                     QTEPanel.this.repaint();
                }
               if (qteLabels.size() > 2) {
                  // removes a QTE when at least 1 other QTE shows up
                  JLabel qteLabel = qteLabels.get(0);
                  qteLabel.removeMouseListener(QTEPanel.this);
                  QTEPanel.this.remove(qteLabel);
                  qteLabels.remove(0); 
                  QTEPanel.this.revalidate();
                  QTEPanel.this.repaint();
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
