import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//Window for the game
   // IntelliJ has a GUI maker
public class Window extends JFrame {

   //window components
	private JFrame window;
	private Image icon;
	private MenuPanel m;
	private GamePanel g;
	public JTextArea message;
	public JPanel bgPanel[] = new JPanel[10];
	public JLabel bgLabel[] = new JLabel[10];
	
	Window(){
		m = new MenuPanel(this);
		g = new GamePanel(this);
		icon = new ImageIcon("assets/icon.png").getImage();
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

		window.add(m);
		window.setVisible(true);
	}
	public void startGame() {
		m.setVisible(false);
		window.add(g);
		//window.addKeyListener(g);
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
		window.setVisible(false);
		window.dispose();
	}
   public static void main (String[] args) {
      Window w = new Window(); // run window
   }
}