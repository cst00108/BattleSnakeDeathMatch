/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.util.Random;

/**
 *
 * @author Owner
 */
public class AI01 extends Snake{
	private static Random random = new Random();
	private int direction = 0;
	private int numOfSteps = 0;

	public AI01(int x, int y){
		super(x, y, 3);
	}
	
	public AI01(int x, int y, int length){
		super(x, y, length);
	}

	//AI can easily step into self.
	//I wrote all of these files, but I was pretending I didn't.
	//I made this not to give the computer to much overhead.
	public void move(){
		if(numOfSteps == 0){
			numOfSteps = random.nextInt(10) + 1;
		
			int randomTempDirection =   //getting direction to move
					random.nextInt(3);
						
			//if following while loop didnt speed up running time, 
			//I wouldnt do it this way. 
			int index = 0;
			while(true){//get direction withonce though loop
			
				if(numOfSteps != index && direction == Snake.DOWN){
					index++;
				} else if(direction == Snake.DOWN 
						&& randomTempDirection !=0){ //if not.... %#$@

					direction = Snake.DOWN;
					break;
				}

				if(randomTempDirection == index){
					direction = Snake.LEFT;
				}
				if(randomTempDirection == 2){
					direction = Snake.RIGHT;
				} else {
					direction = Snake.UP;
				}
			}
		}
		
		move(direction);
	}
}
