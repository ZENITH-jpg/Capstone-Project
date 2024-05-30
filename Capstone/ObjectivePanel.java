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
            // decided to have no air qtes
            game.getQTEPanel().removeChance("Clean air");
            objectives.add(new Objective("Diverse blocks","Obtain soil and ice. Reward: Increase score per second.") {
               public boolean isComplete() {
                  return planet.findBlock("Soil") != -1 && planet.findBlock("Ice") != -1;
               }
               public void reward() {
                  GamePanel.scorePerTwoSeconds += 20;
               }
            });
         }
      });
      objectives.add(new Objective("Unlock medium difficulty","Get 3000 total volume.\nReward: QTEs appear a second sooner.") {
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
         objectives.add(new Objective("Tectonic plates", "Have 1000 rock and 1000 soil.\nReward: No more Rock QTEs, but more Lava and Lava QTEs.") {
            public boolean isComplete() {
               return planet.findBlock("Soil") != -1 && planet.getBlockWithName("Rock").getVolume() >= 1000 && planet.getBlockWithName("Soil").getVolume() >= 1000;
            }
            public void reward() {
               game.getQTEPanel().removeChance("Rock");
               planet.addBlock(new LavaBlock("Lava", 200 + random.nextInt(501)));
               game.getQTEPanel().addChance("Lava");
            }
         });
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
   }
}