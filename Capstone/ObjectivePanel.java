/*
Van N
2024-05-31
Mr Guglielmi
Objectives to complete in the game
*/
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

/**
Display and handles objectives to complete in the game
@author Van N
@version 1.0
*/
public class ObjectivePanel extends JPanel {
   static Random random = new Random();
   ArrayList<Objective> objectives = new ArrayList<Objective>();
   GamePanel game;
   Planet planet;
   JTextArea objLabel;
   /**
   Constructor, creates initial objectives
   @param g GamePanel
   @param p Planet
   */
   public ObjectivePanel (GamePanel g, Planet p) {
      game = g;
      planet = p;
      this.setBounds(500, 40, 280, 400);
      this.setOpaque(false); // makes transparent
      this.setBackground(new Color(0,0,0,0)); // makes transparent
      objLabel = Utils.blockTextPanel("", 0, 0, 280, 400);
      this.setLayout(null);
      this.add(objLabel);
      objectives.add(new Objective("Clouds form","Get 600 water. Reward: 300 air") {
         public boolean isComplete() {
            return planet.getBlockWithName("Clean water").getVolume() >= 600;
         }
         public void reward() {
            planet.addBlock(new AirBlock("Clean air", 300));
            // decided to have no air qtes until later
            game.getQTEPanel().removeChance("Clean air");
            objectives.add(new Objective("Diverse blocks","Obtain ice and soil.\nReward: Increase score per second. Creatures can appear.") {
               public boolean isComplete() {
                  return planet.findBlock("Soil") != -1 && planet.findBlock("Ice") != -1;
               }
               public void reward() {
                  GamePanel.scorePerTwoSeconds += 20;
                  planet.addBlock(new IceBlock("Ice", 300)); // also add 300 ice
                  game.getQTEPanel().addChance("Soil"); // also add soil qtes
                  game.setPlanetLabel("assets/earthy_planet.png"); // also change planet appearance 
                  objectives.add(new Objective("Diverse life", "Create 4 or more creatures.\nReward: Increase score per second. Humans appear.") {
                     public boolean isComplete() {
                        return planet.getCreatures().size() >= 4;
                     }
                     public void reward() {
                        GamePanel.scorePerTwoSeconds += 20;
                        planet.addHumans(500+random.nextInt(500));
                     }
                  });
               }
            });
            objectives.add(new Objective("Complete difficulty I","Get 3000 total volume.\nReward: QTEs appear a second sooner.") {
               public boolean isComplete() {
                  return planet.getTotalVolume() >= 3000;
               }
               public void reward() {
                  GamePanel.difficulty++;
                  game.getQTEPanel().setQTETimer(3000);
                  addMoreObjectives();
               }
            });
         }
      });
      this.displayObjectives();
   }
   /**
   Checks the completion statuses of all objectives, and triggers rewards
   */
   public void checkAllObjectives() {
      // Delete the ones that are complete
      Objective completedObjective = null;
      for (Objective objective : objectives) {
         if (objective.isComplete()) {
            completedObjective = objective;
            objectives.remove(objective);
            break;
         }
      }
      if (completedObjective != null) 
         completedObjective.reward(); // reward() must be here to trigger checkAllObjectives() outside for loop
   }
   /**
   Displays the objectives to the game
   */
   public void displayObjectives() {
      String message = "";
      for (Objective objective : objectives) {
         message = message + objective.getName()+"\n"+objective.getDesc()+"\n\n";
      }
      objLabel.setText(message);
   }
   /**
   Adds more objectives based on current difficulty
   */
   public void addMoreObjectives() {
      if (game.difficulty == 2) {
         objectives.add(new Objective("Tectonic plates", "Have 600 ice and 600 soil.\nReward: No more Rock and Water QTEs, but you must start clicking Ice and Lava QTEs.") {
            public boolean isComplete() {
               return planet.findBlock("Soil") != -1 && planet.findBlock("Ice") != -1 && planet.getBlockWithName("Ice").getVolume() >= 600 && planet.getBlockWithName("Soil").getVolume() >= 600;
            }
            public void reward() {
               game.getQTEPanel().removeChance("Rock");
               game.getQTEPanel().removeChance("Clean water");
               planet.addBlock(new LavaBlock("Lava", 300 + random.nextInt(201)));
               game.getQTEPanel().addChance("Lava");
               game.getQTEPanel().addChance("Ice");
               game.getQTEPanel().addChance("Soil"); // Also increase chance of soil qtes
               
               // Also set volumes of soil and ice to 800
               planet.getBlockWithName("Soil").setVolume(800);
               planet.getBlockWithName("Ice").setVolume(800);
               game.displayBlockLabels();
               objectives.add(new Objective("Complete difficulty II","Have 10,000 humans.\nReward: No more soil QTEs. Smog appears. +1 QTE will be on screen") {
                  public boolean isComplete() {
                     return planet.getHumans() >= 10000;
                  }
                  public void reward() {
                     GamePanel.difficulty++;
                     planet.addBlock(new SmogBlock("Smog", 500));
                     game.getQTEPanel().addChance("Smog");
                     QTEPanel.maxQTEs++;
                     planet.addBlock(new AirBlock("Clean air", 300)); // also add 300 air
                     game.displayBlockLabels();
                     addMoreObjectives();
                  }
               });

            }
         });
      }
   }
}