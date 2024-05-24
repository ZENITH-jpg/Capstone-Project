import javax.swing.*;
import java.awt.*;
//Window for the game
public class Window extends JFrame {

   //window components
	private JFrame window;
	private Image icon;
	private MenuPanel m; // panels that can be switched to
	private GamePanel g;
	private LeaderboardPanel l;
	public JTextArea message;
	public JPanel bgPanel[] = new JPanel[10];
	public JLabel bgLabel[] = new JLabel[10];
	
	Window(){
		m = new MenuPanel(this); //create window panels
		g = new GamePanel(this);
		l = new LeaderboardPanel(this);
		icon = new ImageIcon("assets/icon.png").getImage(); // set window data
		window = new JFrame("PlanetSim");
		window.setIconImage(icon);
		window.setSize(800,600); //size (not resizable)
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //standard init
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);

		/*message = new JTextArea("HAHAHAHAHAHHAHAHA");
		message.setBounds(50,410,700,150);
		message.setBackground(Color.black);
		message.setForeground(Color.white);
		message.setEditable(false);
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setFont(new Font("Book Antiqua", Font.PLAIN, 26));
		window.add(message);
		 */
		window.add(l);
		window.add(g);
		window.add(m);
		l.setVisible(false);
		g.setVisible(false);
		window.setVisible(true);
	}
	public void startGame() {
		m.setVisible(false);
		g.setVisible(true);
		g.requestFocus();
		window.repaint();
		//window.addKeyListener(g);
	}
	public void showLeaderboard() {
		m.setVisible(false);
		l.setVisible(true);
		l.requestFocus();
		window.repaint();
		//window.addKeyListener(g);
	}
	public void returnMenu(){
		l.setVisible(false);
		g.setVisible(false);
		//i.setVisible(false);
		m.setVisible(true);
		m.requestFocus();
		window.repaint();
	}
	public void createBackground(){
		/*
		bgPanel[1] = new JPanel();// add a background (jpanel)
		bgPanel[1].setBounds(0,0,800,600);
		bgPanel[1].setLayout(null);
		window.add(bgPanel[1]); // add background to window
		bgLabel[1] = new JLabel();
		bgLabel[1].setBounds(0,0,800,600);
		ImageIcon bgIcon = new ImageIcon(getClass().getClassLoader().getResource("assets/background.png")); // get image for background
		bgLabel[1].setIcon(bgIcon);
		bgPanel[1].add(bgLabel[1]); // set background to image
		 */

	}
	public void close(){
		window.setVisible(false); //set visibility to false, then clean up panel
		window.dispose();
	}

	public void testMaze(){
		m.setVisible(false);
		MazeGame maze = new MazeGame(this);
		window.add(maze);
		maze.requestFocus();
		maze.setUp();

	}
   public static void main (String[] args) {
      Utils.init();
      Window w = new Window(); // run window
   }
}