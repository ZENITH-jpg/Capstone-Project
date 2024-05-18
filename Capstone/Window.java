import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//Window for the game
   // IntelliJ has a GUI maker
public class Window extends JFrame {

   //window components
	private JFrame window;
	private Image icon;
	public JTextArea message;
	public JPanel bgPanel[] = new JPanel[10];
	public JLabel bgLabel[] = new JLabel[10];
	
	Window(){
		MenuPanel m = new MenuPanel(); //new menu item
		icon = new ImageIcon("assets/icon.png").getImage(); //app icon
		window = new JFrame("PlanetSim"); // title
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
		window.addKeyListener(m); // set keylistener
		window.add(m); // add menu to window
		window.setVisible(true); // display
	}
   public static void main (String[] args) {
      Window w = new Window(); // run window
   }
}