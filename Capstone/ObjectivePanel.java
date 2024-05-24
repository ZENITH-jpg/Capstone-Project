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
      objectives.add(new Objective("Seventy-Seven Seas","Get 600 water. Reward: 300 air") {
         public boolean isComplete() {
            return planet.getBlocks().get(planet.findBlock("Clean water")).getVolume() >= 600;
         }
         public void reward() {
            planet.addBlock(new AirBlock("Clean air", 300));
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
         message = message + objective.getName()+"\n"+objective.getDesc()+"\n";
      }
      objLabel.setText(message);
   }
}