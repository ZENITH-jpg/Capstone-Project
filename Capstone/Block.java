public class Block {
   private String type;
   private int volume;
   private String property;
   public Block(String t, int v) {
      this.type = t;
      this.volume = v;
      this.property = "";
   }
   public Block(String t, int v, String p) {
      this.type = t;
      this.volume = v;
      this.property = p;
   }
   public String getType() {
      return this.type;
   }
   public String getProperty() {
      return this.type;
   }
   public int getVolume() {
      return this.volume;
   }
   public void setType(String t) {
      this.type = t;
   }
   public void setVolume(int v) {
      this.volume = v;
   }
   public void addVolume(int v) {
      this.volume += v;
   }
}