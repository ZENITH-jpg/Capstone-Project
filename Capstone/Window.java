/*
Van N, Tristan C
2024-05-31
Mr Guglielmi
Window for the game
*/
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * The window containing the game
 * @author Tristan C
 * @author Van N
 */
public class Window extends JFrame {
	/**
	 * Instance of random to be used around the game
	 */
   private static Random random = new Random();
   //window components
	/**
	 * The JFrame everything takes place on
	 */
	private JFrame window;
	/**
	 * The icon for the window
	 */
	private Image icon;
	/**
	 * Menu panel for game
	 */
	private MenuPanel m; // panels that can be switched to
	/**
	 * The game panel
	 */
	private GamePanel g;
	/**
	 * The leaderboard for the game
	 */
	private LeaderboardPanel l;
	/**
	 * The instructions for the game
	 */
	private InstructionsPanel i;
	/**
	 * Minigames
	 */
   private Minigame[] mg;

	/**
	 * Constructor for the Windows, sets up window and assets
	 */
	Window(){
		m = new MenuPanel(this); //create window panels
		g = new GamePanel(this);
      Utils.init(); // initialize fonts
      GamePanel.timerOn = false;
		l = new LeaderboardPanel(this);
		i = new InstructionsPanel(this);
		icon = new ImageIcon("assets/icon.png").getImage(); // set window data
		window = new JFrame("PlanetSim");
		window.setIconImage(icon);
		window.setSize(800,600); //size (not resizable)
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //standard init
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		window.add(i);
		window.add(l);
		window.add(m);
      window.add(g);
		mg = new Minigame[]{new ProtestGame(this), new MazeGame(this), new LightsGame(this)};
      g.setVisible(false);
		l.setVisible(false);
		i.setVisible(false);
		window.setVisible(true);
	}
	/**
	 * Start the main game and hide other components
	 */
	public void startGame() {
		m.setVisible(false);
		g.setVisible(true);
		g.reset();
      GamePanel.timerOn = true;
		g.requestFocus();
      window.repaint();
	}

	/**
	 * Start a random minigame and pause others
	 */
   public void startRandomMinigame() {
      int i = random.nextInt(mg.length);
      GamePanel.timerOn = false;
      g.setVisible(false);
      window.add(mg[i]);
      mg[i].setVisible(true);
		new MinigameHandler (mg[i]).start(); // required to sync minigame to window
   }
	/**
	 * Display the instructions of the game
	 */
	public void showInstructions() {
		m.setVisible(false);
		i.setVisible(true);
		i.requestFocus();
		window.repaint();
	}
	/**
	 * Display the leaderboard of the game
	 */
	public void showLeaderboard() {
		m.setVisible(false);
		l.setVisible(true);
		l.requestFocus();
		window.repaint();
	}
   
   public void addToLeaderboard(String name, int score) {
      l.addPlayer(name, score);
      g.setVisible(false);
      l.setVisible(true);
		l.requestFocus();
   }

	/**
	 * Return to the menu of the game
	 */
	public void returnMenu(){
		l.setVisible(false);
		g.setVisible(false);
		i.setVisible(false);
		m.setVisible(true);
		m.requestFocus();
		window.repaint();
	}

	/**
	 * Close the window
	 */
	public void close(){
		window.setVisible(false); //set visibility to false, then clean up panel
		window.dispose();
	}

	/**
	 * Get the game instance
	 * @return the game panel
	 */
   public GamePanel getGame() {
      return g;
   }

	public static Random getRandom() {
		return random;
	}
}