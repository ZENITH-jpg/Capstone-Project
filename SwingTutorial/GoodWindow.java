import javax.swing.*;
import java.awt.event.*;

public class GoodWindow extends JFrame {
   
   //window components
	private JLabel l; 
	private JPanel p;
	private JButton b;
   public int x = 500; // width
   public int y = 500; // length
	
	GoodWindow(){
		//initialize the components
		p = new JPanel();
		this.add(p); //add the panel to the frame
      p.setBounds(x/2-50, 0, 200, 200);
      p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS)); // makes elems fall in a column


		l = new JLabel("menu");
      l.setHorizontalAlignment(JLabel.CENTER);
      l.setVerticalAlignment(JLabel.CENTER);

		b = new JButton ("Click me!");
		
		p.add(l); //add the label to the panel
		p.add(b); //add the button to the panel

		//set frame properties
		setResizable (false);
		setSize(x, y);
      setLayout(null);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
   public static void main (String[] args) {
      GoodWindow w = new GoodWindow();
      w.setVisible(true);
   }
}