/**
 * Handles ball creation, movement, and collisions
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class BallSim extends JPanel 
{
	private static final long serialVersionUID = 1L;

	private class Ball extends Rectangle 
	{		
		private static final long serialVersionUID = 1L;
		
		// Fields - Ball properties
		private double x;
        private double y;
        private double width;
        private double height;
        private Color color;
        private boolean lR;
        private boolean uD;
        private int speedX;
        private int speedY;

        // Ball Constructor
        Ball(Color color, int x, int y, int width, int height) 
        {
            this(color, x, y, width, height, false, false);
        }

        Ball(Color color, int x, int y, int width, int height, boolean lR, boolean uD) 
        {
            this.color = color;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.lR = lR;
            this.lR = uD;
            updateSpeed();
        }

        private void updateSpeed() 
        {
            final int minimumMovement = 5;
            final int maxExtra = 10;
            speedY = minimumMovement + (int) (Math.random() * maxExtra);
            speedX = minimumMovement + (int) (Math.random() * maxExtra);
        }

        /**
         * Handles positioning of the ball at the four corners of the JFrame
         * https://stackoverflow.com/questions/15598247/how-can-i-move-balls-in-random-directions-and-how-can-i-keep-them-bouncing-insid helped in the idea
         */
        public void positionBall() 
        {
            if (lR) 
            {
                x += speedX;
                if (x >= (BallSim.this.getWidth() - width / 2)) 
                {
                    lR = false;
                    updateSpeed();
                }
            } 
            else 
            {
                x += -speedX;
                if (x <= 0) 
                {
                    lR = true;
                    updateSpeed();
                }
            }

            if (uD) 
            {
                y += speedY;

                uD = !(y >= (BallSim.this.getHeight() - height / 2));
                if (y >= (BallSim.this.getHeight() - height / 2)) 
                {
                    uD = false;
                    updateSpeed();
                }
            } 
            else 
            {
                y += -speedY;
                if (y <= 0) 
                {
                    uD = true;
                    updateSpeed();
                }
            }
        }
        
        /**
         * Check whether collision happens between two balls
         * @param ball1
         * @param ball2
         * @return
         */
        public boolean isCollision(Ball ball1, Ball ball2)
        {
        	Rectangle b1 = ball1.bounds();
            Rectangle b2 = ball2.bounds();
        	
        	if (b1.intersects(b2))
        	{
        		return true;
        	}
        	else
        	{
        		return false;
        	}
        }
        
        /**
         * If collision happens, update positions of both the balls involved
         * @param ball1
         * @param ball2
         */
        public void updateCollision(Ball ball1, Ball ball2)
        {
        	if (isCollision(ball1, ball2))
        	{
        		ball1.speedX = -ball1.speedX;
            	ball1.speedY = -ball1.speedY;
            	ball2.speedX = -ball2.speedX;
            	ball2.speedY = -ball2.speedY;
        		updateSpeed();
        	}
        }
        
        /**
         * Get rectangle bounds for the ball to be able to detect collision
         * @return
         */
        public Rectangle bounds()
        {
        	return (new Rectangle((int)x, (int)y, (int)width, (int)height));
        }
        
        /**
         * Getter methods for Ball fields
         * @return
         */
        public Color getColor() 
        {
            return color;
        }

        public double getX() 
        {
            return x;
        }

        public double getY() 
        {
            return y;
        }

        public double getWidth() 
        {
            return width;
        }

        public double getHeight() 
        {
            return height;
        }
	}
	
	// Fields - Ball Simulation
	private int NO_OF_BALLS = 5;
	
	// Initialize ArrayList for storing all the balls
	private ArrayList<Ball> balls = new ArrayList<>(NO_OF_BALLS);
	
	/**
	 * Constructor for Ball Simulation:
	 * 1) Creates the balls
	 * 2) Starts the animation
	 */
	public BallSim() 
	{
	    createBalls();
	    startSim();
	}
	
	/**
	 * Starts the simulation:
	 * 1) Using the Timer Class instead of Threads as it is more efficient for 
	 *    animations/simulations
	 */
	private void startSim() 
	{
	    int UPDATE_RATE = 30; // Frequency of updating the JFrame
	    int waitTime = 1000 / UPDATE_RATE; // Time after which the Timer updates the JFrame using 'repaint()'
	    Timer timer = new Timer(waitTime, new ActionListener() 
	    {
	        @Override
	        public void actionPerformed(ActionEvent e) 
	        {
	            for (Ball ball1: balls) 
	            {
	            	ball1.positionBall();
	            	for (Ball ball2: balls)
	                {          	
	                	ball1.updateCollision(ball1, ball2);
	                }
	            }
	            repaint();
	        }
	    });
	    timer.start();
	}
	
	/**
	 * Initializes / Creates balls to be shown on the JFrame
	 */
	private void createBalls() 
	{
	    // Initializing positions for the ball
		int startX = 0;
	    int startY = 0;
	    
	    /**
	     *  Loop to create balls
	     *  1) Default is all ball's of same size and color
	     */
		for (int i = 0; i < NO_OF_BALLS; i++)
		{
	    	balls.add(new Ball(Color.green, startX, startY, 15, 15));
		}
	}
	
	/**
	 * Overriding the paintComponent method.
	 * Handles painting the balls according to the properties set in 'createBalls()'
	 */
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		// To create good circles using Graphics2D and RenderingHints
	    Graphics2D g2 = (Graphics2D) g.create();
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	
	    // Draw and fill the balls with color on the JFrame
	    for (Ball ball : balls) 
	    {
	        g2.setColor(ball.getColor());
	        g2.fillOval((int)ball.getX(), (int)ball.getY(), (int)ball.getWidth(), (int)ball.getHeight());
	        g2.setColor(ball.getColor().darker());
	        g2.drawOval((int)ball.getX(), (int)ball.getY(), (int)ball.getWidth(), (int)ball.getHeight());
	    }
	    g2.dispose();
	}
}