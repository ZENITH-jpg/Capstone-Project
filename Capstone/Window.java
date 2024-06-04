/*
Van N, Tristan C
2024-05-31
Mr Guglielmi
Window for the game
*/
import javax.swing.*;
import java.awt.*;
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
      GamePanel.timerOn = false;
		l = new LeaderboardPanel(this);
		icon = new ImageIcon("assets/icon.png").getImage(); // set window data
		window = new JFrame("PlanetSim");
		window.setIconImage(icon);
		window.setSize(800,600); //size (not resizable)
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //standard init
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		window.add(l);
		window.add(m);
      window.add(g);
      g.setVisible(false);
		l.setVisible(false);
		window.setVisible(true);
	}
	public void startGame() {
		m.setVisible(false);
		g.setVisible(true);
      GamePanel.timerOn = true;
		g.requestFocus();
		window.repaint();
	}
	public void showLeaderboard() {
		m.setVisible(false);
		l.setVisible(true);
		l.requestFocus();
		window.repaint();
	}
	public void returnMenu(){
		l.setVisible(false);
		g.setVisible(false);
		m.setVisible(true);
		m.requestFocus();
		window.repaint();
	}
	public void close(){
		window.setVisible(false); //set visibility to false, then clean up panel
		window.dispose();
	}

	public void testProtest(){
		m.setVisible(false);
		ProtestGame p = new ProtestGame(this);
		window.add(p);
		p.setUp();

	}
   public static void main (String[] args) {
      Utils.init();
      Window w = new Window(); // run window
   }
}