public class MinigameHandler extends Thread{
   private final Minigame m;
   public MinigameHandler(Minigame m){
      this.m = m;
   }
   public void run(){
      synchronized (m){
         m.setUp();
      }
   }
}
