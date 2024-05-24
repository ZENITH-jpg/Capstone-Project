
public class Objective {
   private String name;
   private String desc;
   public Objective (String n, String d) {
      name = n;
      desc = d;
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