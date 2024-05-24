import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class ObjectivePanel extends JPanel {
   ArrayList<Objective> objectives = new ArrayList<Objective>();
   GamePanel game;
   Planet planet;
   public ObjectivePanel (GamePanel g, Planet p) {
      game = g;
      planet = p;
      objectives.add(new Objective("Seventy-Seven Seas","Get 1000 water. Reward: 300 air") {
         public boolean isComplete() {
            return planet.getBlocks().get(planet.findBlock("Clean water")).getVolume() >= 1000;
         }
         public void reward() {
            planet.addBlock(new AirBlock("Clean air", 300));
         }
      });
   }
   public void checkAllObjectives() {
      // Delete the ones that are complete
      for (Objective objective : objectives) {
         if (objective.isComplete()) {
            objective.reward();
            objectives.remove(objective);
         }
      }
   }
}