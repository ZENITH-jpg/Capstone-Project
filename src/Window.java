import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
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
	public Window() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel menuLabel = new JLabel("Menu");
		menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		menuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(menuLabel);
		
		JButton instructButton = new JButton("Instructions");
		instructButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(instructButton);
		
		JButton leaderButton = new JButton("Leaderboard");
		leaderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(leaderButton);
		
		JButton startButton = new JButton("Start Game");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menuLabel.setText("Press A and D to switch planets.");
				instructButton.setVisible(false);
				leaderButton.setVisible(false);
				startButton.setText("Create planet");
			}
		});
		startButton.setForeground(new Color(0, 255, 0));
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(startButton);
	}

}
