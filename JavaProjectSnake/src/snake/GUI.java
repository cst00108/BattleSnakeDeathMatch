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

	public void setMainComponents(){  //put this public to use for applet
		int height = 50;
		int width = 50;		
		Arena.setBounds(height, width);
		snakes = new Snakes();

		this.setPreferredSize(new Dimension(height*10, width*10));
		
		//add snakes
		for(int index=0; index<60; index++){
			snakes.addSnake(new AI02(6));
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
	
	
	private KeyAdapter setPacmanControls(){

		return new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				if (key == KeyEvent.VK_LEFT) {
				} else if (key == KeyEvent.VK_RIGHT) {
				}else if (key == KeyEvent.VK_UP) {
				} else if (key == KeyEvent.VK_DOWN) {
				} else if(key == KeyEvent.VK_J){
				}
			}
		};	
	}
}
