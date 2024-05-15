public class Block {
   private String type;
   private int volume;
   public Block(String t, int v) {
      this.type = t;
      this.volume = v;
   }
   public String getType() {
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