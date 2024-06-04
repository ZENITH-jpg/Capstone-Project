/*
Van N
2024-05-31
Mr Guglielmi
Objectives to complete in the game
*/
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class ObjectivePanel extends JPanel {
   static Random random = new Random();
   ArrayList<Objective> objectives = new ArrayList<Objective>();
   GamePanel game;
   Planet planet;
   JTextArea objLabel;
   public ObjectivePanel (GamePanel g, Planet p) {
      game = g;
      planet = p;
      Objective.setObjPanel(this);
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
            objectives.add(new Objective("Diverse blocks","Obtain soil and ice.\nReward: Increase score per second. Creatures can appear.") {
               public boolean isComplete() {
                  return planet.findBlock("Soil") != -1 && planet.findBlock("Ice") != -1;
               }
               public void reward() {
                  GamePanel.scorePerTwoSeconds += 20;
                  planet.addBlock(new IceBlock("Ice", 300)); // also add 300 ice
                  game.getQTEPanel().addChance("Soil"); // also increase chance of soil qtes
                  game.setPlanetLabel("assets/earthy_planet.png"); // also change planet appearance 
                  GamePanel.creaturesCanAppear = true;
                  objectives.add(new Objective("Diverse life", "Create 4 or more creatures.\nReward: No more Water QTEs. Increase score per second. Humans appear.") {
                     public boolean isComplete() {
                        return planet.getCreatures().size() >= 4;
                     }
                     public void reward() {
                        GamePanel.scorePerTwoSeconds += 20;
                        game.getQTEPanel().removeChance("Clean water");
                        planet.addHumans(500+random.nextInt(500));
                     }
                  });
               }
            });
         }
      });
      objectives.add(new Objective("Easy mode","Get 3000 total volume.\nReward: QTEs appear a second sooner.") {
         public boolean isComplete() {
            return planet.getTotalVolume() >= 3000;
         }
         public void reward() {
            GamePanel.difficulty++;
            game.getQTEPanel().setQTETimer(3000);
            addMoreObjectives();
         }
      });
      this.displayObjectives();
   }
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
   public void displayObjectives() {
      String message = "";
      for (Objective objective : objectives) {
         message = message + objective.getName()+"\n"+objective.getDesc()+"\n\n";
      }
      objLabel.setText(message);
   }
   public void addMoreObjectives() {
      if (game.difficulty == 1) {
         objectives.add(new Objective("Tectonic plates", "Have 1000 rock and 600 soil.\nReward: No more Rock QTEs, but more Lava and Lava QTEs.") {
            public boolean isComplete() {
               return planet.findBlock("Soil") != -1 && planet.getBlockWithName("Rock").getVolume() >= 1000 && planet.getBlockWithName("Soil").getVolume() >= 600;
            }
            public void reward() {
               game.getQTEPanel().removeChance("Rock");
               planet.addBlock(new LavaBlock("Lava", 200 + random.nextInt(501)));
               game.getQTEPanel().addChance("Lava");
            }
         });
         objectives.add(new Objective("Medium mode","Have 50,000 humans.\nReward: Garbage blocks appear. +1 QTE will be on screen") {
            public boolean isComplete() {
               return planet.getHumans() >= 50000;
            }
            public void reward() {
               GamePanel.difficulty++;
               planet.addBlock(new SmogBlock("Smog", 500));
               QTEPanel.maxQTEs++;
               planet.removeBlockWithName("Rock"); // Also remove rock from view
               planet.addBlock(new AirBlock("Clean air", 300)); // also add 300 air
               game.getQTEPanel().clearQTEs();
               game.displayBlockLabels();
               addMoreObjectives();
            }
         });
      } else if (game.difficulty == 2) {
         objectives.add(new Objective("Ozone layer","Get 800 air by converting smog.") {
         public boolean isComplete() {
               return planet.getBlockWithName("Clean air").getVolume() >= 800;
            }
            public void reward() {
               game.startMinigame(new MazeGame(game, planet));
            }
         });
      }
   }
}