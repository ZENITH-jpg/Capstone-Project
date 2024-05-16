public abstract class Block {
   protected String name;
   protected int volume; // no setter only getter and adder
   protected String type; // rock, soil, water, air, etc
   protected String property;
   public Block(String n, int v) {
      this.name = n;
      this.volume = v;
   }
   public abstract void doProperty(Planet p); // gets overriden
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