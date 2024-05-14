import javax.swing.*;
import java.awt.event.*;

   // IntelliJ has a GUI maker
public class Window extends JFrame {
   
   //window components
	private JLabel l; 
	private JPanel p;
	private JButton b;
	
	Window(){
		//initialize the components
		p = new JPanel();
		l = new JLabel("Hello world");
		b = new JButton ("Click me!");
		
		p.add (l); //add the label to the panel
		//b.addActionListener (new TheButton (this)); //add the event listener to the button to make it work
		p.add(b); //add the button to the panel
		this.add(p); //add the panel to the frame
		
		//set frame properties
		setResizable (false);
		setBounds (100, 100, 800, 500);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
   public static void main (String[] args) {
      Window w = new Window();
      w.setVisible(true);
   }
}