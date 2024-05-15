public abstract class Block {
   protected String type;
   protected int volume; // there are no setters for volume and cleanliness, only getters and ADDERS
   protected int cleanliness; // <0 means dirty, >0 means clean
   protected String property;
   public Block(String t, int v, int c) {
      this.type = t;
      this.volume = v;
      this.cleanliness = c;
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
   public int getCleanliness() {
      return this.cleanliness;
   }
   public void addCleanliness(int c) {
      this.cleanliness += c;
   }
   public void setType(String t) {
      this.type = t;
   }
   public void addVolume(int v) {
      this.volume += v;
   }
}