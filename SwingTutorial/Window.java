import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

   // IntelliJ has a GUI maker
public class Window extends JFrame {
   
   //window components
	private JFrame window;
	public JTextArea message;
	public JPanel bgPanel[] = new JPanel[10];
	public JLabel bgLabel[] = new JLabel[10];
	
	Window(){
		window = new JFrame();
		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.BLACK);
		window.setLayout(null);

		message = new JTextArea("HAHAHAHAHAHHAHAHA");
		message.setBounds(50,410,700,150);
		message.setBackground(Color.black);
		message.setForeground(Color.white);
		message.setEditable(false);
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setFont(new Font("Book Antiqua", Font.PLAIN, 26));
		window.add(message);
		createBackground();
		window.setVisible(true);
	}
	public void createBackground(){
		bgPanel[1] = new JPanel();
		bgPanel[1].setBounds(50,50,700,350);
		bgPanel[1].setBackground(Color.blue);
		bgPanel[1].setLayout(null);
		window.add(bgPanel[1]);

		bgLabel[1] = new JLabel();
		bgLabel[1].setBounds(0,0,700,350);
		bgLabel[1].setBackground(Color.red);
		bgPanel[1].add(bgLabel[1]);
	}
   public static void main (String[] args) {
      Window w = new Window();
   }
}