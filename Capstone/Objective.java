/*
Van N
2024-05-31
Mr Guglielmi
Objective object in the game with a prompt and description
*/
/**
Objective in the game with a prompt and description
@author Van N
@version 1.0
*/
public class Objective {
   private String name;
   private String desc;
   /**
   Constructor
   @param n name
   @param d description
   */
   public Objective (String n, String d) {
      name = n;
      desc = d;
   }
   /**
   Checks the complete condition
   @return the complete status as a boolean
   */
   public boolean isComplete() {
      return false;
   }
   /**
   Reward for completing the objective
   */
   public void reward() {
   }
   /**
   Get name
   @return name
   */
   public String getName() {
      return this.name;
   }
   /**
   Get desc
   @return description
   */
   public String getDesc() {
      return this.desc;
   }
}