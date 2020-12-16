/**
 * Handles overall layout and simulation properties
 * Contains the Main method to run the animation
 */
import java.awt.CardLayout;

import javax.swing.WindowConstants;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Simulation 
{
	// Initialize components of the animation
	JFrame frame = new JFrame("Simulation");
	JPanel container = new JPanel();
	JPanel intro = new JPanel();
	BallSim ballSim = new BallSim();
	
	// Add buttons and initialize text to display on the JFrame
	JButton button = new JButton("Start Simulation");
	JButton button1 = new JButton("Home");
	JLabel label = new JLabel("Label");
	
	/**
	 * Create a CardLayout instead of creating multiple JFrames for better user experience
	 * Updates single JFrame according to the buttons clicked.
	 */
	CardLayout cl = new CardLayout();
	
	// Dimensions of the JFrame
	private static int frameWidth = 600;
	private static int frameHeight = 500;
	
	private int fontSize = 25; // Font size for displaying text on the JFrame
	
	/**
	 * Simulation Constructor:
	 * Handles setting up and combining all the components required
	 */
	Simulation()
	{
		ballSim.setOpaque(false);
		
		label.setForeground(Color.BLUE); // Text Color
		label.setText("<html><br><br>Welcome to the Colliding Balls Animation!</html>"); // Welcome Text
		label.setFont(new Font("SansSerif", Font.BOLD, fontSize)); // Font size for Text
		
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		container.setLayout(cl);
		
		// Add buttons and labels to the homepage
		intro.add(button);
		intro.add(label);
		
		intro.setBackground(Color.CYAN); // Homepage background color
		
		ballSim.add(button1); // Add 'Go to Home' button on the animation page
				
		/**
		 * Add both the homepage and the simulation page to the container JPanel
		 */
		container.add(intro, "Home");
		container.add(ballSim, "Start Simulation");
		
		// The page to be seen initially is the HomePage
		cl.show(container, "Home");
		
		/*
		 * Add action listener to respond to button clicks and navigate between both pages
		 */
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(container, "Start Simulation");
			}
		});
		
		button1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(container, "Home");
			}
		});
		
		frame.add(container); // Add panel to the JFrame
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/*
	 * Getter methods for size of JFrame
	 */
	public static int getFrameWidth()
	{
		return frameWidth;
	}
	
	public static int getFrameHeight()
	{
		return frameHeight;
	}
	
	/**
	 * The Main Class:
	 * Handles running of the whole simulation
	 * @param args
	 */
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Simulation();
			}
		});
	}
}
