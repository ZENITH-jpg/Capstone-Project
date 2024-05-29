import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class ObjectivePanel extends JPanel {
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
         }
      });
      objectives.add(new Objective("Unlock Medium Difficulty","Get 3000 total volume.\nReward: Increase score per second.") {
         public boolean isComplete() {
            return planet.getTotalVolume() >= 3000;
         }
         public void reward() {
            GamePanel.difficulty++;
            GamePanel.scorePerTwoSeconds += 20;
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
         objectives.add(new Objective("Tectonic plates", "Have 3000 volume of rock and soil combined.\nReward: No more Rock QTEs. Less lava forms.") {
            public boolean isComplete() {
               return planet.getBlockWithName("Rock").getVolume() + planet.getBlockWithName("Soil").getVolume() >= 3000;
            }
            public void reward() {
               game.getQTEPanel().removeChance("Rock");
            }
         });
      }
   }
}