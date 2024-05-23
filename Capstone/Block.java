
public abstract class Block {
   protected static Planet planet; // Whenever a planet is made, sets to that planet
   protected String name;
   protected int volume; // no setter only getter and adder
   protected String type; // rock, soil, water, air, etc
   protected String property; // every block has a property or ability
   public Block(String n, int v) {
      this.name = n;
      this.volume = v;
   }
   public abstract void doProperty();
   public abstract void doQTE();
   public static void setPlanet(Planet p) { // sets static planet variable
      planet = p;
   }
   public String getName() {
      return this.name;
   }
   public String getType() {
      return this.type;
   }
   public String getProperty() {
      return this.property;
   }
   public int getVolume() {
      return this.volume;
   }
   public void setType(String t) {
      this.type = t;
   }
   public void addVolume(int v) {
      this.volume += v;
   }
}