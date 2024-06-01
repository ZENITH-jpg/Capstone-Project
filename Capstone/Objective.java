/*
Van N
2024-05-31
Mr Guglielmi
Objective object in the game with a prompt and description
*/
public class Objective {
   private static ObjectivePanel objPanel;
   private String name;
   private String desc;
   public Objective (String n, String d) {
      name = n;
      desc = d;
   }
   public static void setObjPanel(ObjectivePanel obj) {
      objPanel = obj;
   }
   // this method get overridden
   public boolean isComplete() {
      return false;
   }
   public void reward() {
   }
   public String getName() {
      return this.name;
   }
   public String getDesc() {
      return this.desc;
   }
}