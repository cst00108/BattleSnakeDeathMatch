/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Owner
 */
public class GUI extends JPanel implements ActionListener{
	private Timer timer;
	private Snakes snakes;
	
	public GUI() {
		//addKeyListener(setPacmanControls());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		timer = new Timer(100, this);
		timer.start();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		GUI gui = new GUI();
		gui.setMainComponents();
		JFrame frame = getFrame(gui);
	}


	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D)g;
		
		Iterator<Snake> snakesIterator = snakes.getSnakes();
		Snake snake;
		Integer coords;
		
		while(snakesIterator.hasNext()){
			snake = snakesIterator.next();

			//draw bity end
			coords = snake.getKillingEndCoords();

			g2d.drawImage(snake.getKillingEndImage(), 
					Snake.getX(coords)*10, 
					Snake.getY(coords)*10, 
					this);

			//draw body
			Iterator<Integer> bodyCoordsIterator =  
					snake.getBodyCoords().iterator();
			
			while(bodyCoordsIterator.hasNext()){
				coords = bodyCoordsIterator.next();
				
				g2d.drawImage(snake.getBodyImage(), 
						Snake.getX(coords)*10, 
						Snake.getY(coords)*10, 
						this);
			}
		}
		
		snakes.move();
	}

	
	public void actionPerformed(ActionEvent e) {
		repaint();  
	}

	public void setMainComponents(){
		int height = 50;
		int width = 50;		
		this.setPreferredSize(new Dimension(height*10, width*10));
		
		Arena.setBounds(height, width);
		snakes = new Snakes();
		
		//I screwed up someplace on the controls.  Took fast fix.  
		//Reversed up and down in constructor superJay and otherHuman.
		Player superJay = new Player(2, 2, Snake.RIGHT,
				KeyEvent.VK_DOWN, KeyEvent.VK_UP, 
				KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		superJay.setImageFiles(new String[]{
				"images/yellowish_bity.png", "images/yellowish.png"});
		superJay.setImages();
		addKeyListener(superJay.setControls());
		snakes.addSnake(superJay);
		
		Player otherHuman = new Player(
				Arena.getXBounds()-2, Arena.getYBounds()-2, Snake.LEFT,
				KeyEvent.VK_S, KeyEvent.VK_W, 
				KeyEvent.VK_A, KeyEvent.VK_D);
		otherHuman.setImageFiles(new String[]{
				"images/reddish_head.png", "images/reddish.png"});
		otherHuman.setImages();
		addKeyListener(otherHuman.setControls());
		snakes.addSnake(otherHuman);

		//add computer snakes
		for(int index=0; index<10; index++){
			snakes.addSnake(new AI02(Snake.SUGGESTED_SNAKE_LENGTH));
		}

		//set up images
		Iterator<Snake> snakesIterator = snakes.getSnakes();
		
		while(snakesIterator.hasNext()){
			snakesIterator.next().setImages();
		}
	}
	
	
	private static JFrame getFrame(GUI gui){
		JFrame toReturn = new JFrame("Snake");

		toReturn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		toReturn.getContentPane().add(gui);

		toReturn.setResizable(false);
		toReturn.pack();
		toReturn.setLocationRelativeTo(null);
		toReturn.setVisible(true);
//		toReturn.pack();
		
		return toReturn;
	}
}
