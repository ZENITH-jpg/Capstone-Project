import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow frame = new GameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameWindow() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel planetDisplay = new JPanel();
		contentPane.add(planetDisplay);
		
		JLabel lblNewLabel = new JLabel("New label");
		BufferedImage image;
		try {                
	          image = ImageIO.read(new File("planet.jpg"));
	  		lblNewLabel.setIcon(new ImageIcon(image));
	       } catch (IOException ex) {
	            // handle exception...
	       }
		planetDisplay.add(lblNewLabel);
		
		JPanel planetControls = new JPanel();
		contentPane.add(planetControls);
	}

}
