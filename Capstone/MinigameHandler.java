/**
 * A minigame handler that starts a new thread to play the minigame
 *
 * @author Tristan C
 * @version 1.0
 */
public class MinigameHandler extends Thread {
   /**
    * The minigame to be run
    */
   private final Minigame m;

   /**
    * Constructor that sets up the minigame
    *
    * @param m The minigame selected to play
    */
   public MinigameHandler(Minigame m) {
      this.m = m;
   }

   /**
    * Runs the thread setting up and playing the game
    */
   @Override
   public void run() {
      synchronized (m) {
         m.setUp();
      }
   }
}
