/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Jason Donald
 */
public class Player extends Snake {
	private int upKey;
	private int downKey;
	private int leftKey;
	private int rightKey;
	private int direction;
	
	public Player(int upKey, int downKey, int leftKey, int rightKey){
		super(AIUtils.getRandomish(Arena.getXBounds()), 
				AIUtils.getRandomish(Arena.getXBounds()),
				Snake.SUGGESTED_SNAKE_LENGTH);

		this.upKey = upKey;
		this.downKey = downKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}

	public Player(int x, int y, int direction, 
			int upKey, int downKey, int leftKey, int rightKey){
		
		super(x, y, Snake.SUGGESTED_SNAKE_LENGTH, direction);

		this.upKey = upKey;
		this.downKey = downKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
	}
	
	//this is where to get them:  KeyEvent.VK_LEFT
	public KeyAdapter setControls(){

		return new KeyAdapter(){
			
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				if (key == upKey) {lastDirection = Snake.UP;} 
				else if (key == downKey) {lastDirection = Snake.DOWN;}
				else if (key == leftKey) {lastDirection = Snake.LEFT;}
				else if (key == rightKey) {lastDirection = Snake.RIGHT;}
				
				System.out.println("keyPressed():  " + key);
			}
		};	
	}
}
