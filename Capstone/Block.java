public abstract class Block {
   protected String type;
   protected int volume; // no setter only getter and adder
   protected String property;
   public Block(String t, int v) {
      this.type = t;
      this.volume = v;
   }
   public abstract void doProperty(Planet p); // gets overriden
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