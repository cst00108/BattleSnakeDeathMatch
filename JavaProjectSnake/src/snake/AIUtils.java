/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author jay
 */
public class AIUtils {
	private static Random random = new Random();
	
	public static int getRandomish(int bounds){
		return random.nextInt(bounds);
	}
	
	/*
	//was going to get merged coords, but private.  Will weigh out conciquences
	//when have more sleep.
	public static int getRandomish(){
		return 
				random.nextInt(bounds);
	}
	*/
	
	//returns random direction that wont bite himself
	public static int getRandomDirection(Snake snake){
		int[] directions = {Snake.DOWN, Snake.LEFT, Snake.RIGHT, Snake.UP};
		
		//randomize the directions
		for(int index=0; index<directions.length; index++){
			int randomDirection = random.nextInt(4);
			
			int temp = directions[index];
			
			directions[index] = directions[randomDirection];
			directions[randomDirection] = temp;
		}
		
		return getDirection(snake, directions);
	}
	
	private static int getDirection(Snake snake, int[] directions){
		//check if snake can move in that direction
		for(int index=0; index<directions.length; index++){
			int newBityEnd = snake.getCoordsForBityEnd(directions[index]);
			
			Iterator<Integer> snakesBody = snake.getBodyCoords().iterator();
			
			//Since i dont want to think right now, I'll say the snakes one 
			//body part bigger than he actually is.
			boolean ranIntoSelf = false;
			
			while(snakesBody.hasNext()){
				if(snakesBody.next() == newBityEnd){
					//not that way.  Ran into self.
					ranIntoSelf = true;
					break;
				}
			}
			
			if(!ranIntoSelf){
				return directions[index];
			}
		}

		//He's %#$@ed!!  Every way has got the snake. :(
		return directions[0];
	}
	
	public static int getDirection(Snake snake, int hopefullDirection){
		int[] hopefullDirections = new int[4];
		hopefullDirections[0] = hopefullDirection;

		if(hopefullDirection == Snake.DOWN){
			hopefullDirections[1] = Snake.LEFT;
			hopefullDirections[2] = Snake.RIGHT;
			hopefullDirections[3] = Snake.UP;
		} else if(hopefullDirection == Snake.UP){
			hopefullDirections[1] = Snake.LEFT;
			hopefullDirections[2] = Snake.RIGHT;
			hopefullDirections[3] = Snake.DOWN;
		} else if(hopefullDirection == Snake.LEFT){
			hopefullDirections[1] = Snake.DOWN;
			hopefullDirections[2] = Snake.UP;
			hopefullDirections[3] = Snake.RIGHT;
		} else {
			hopefullDirections[1] = Snake.DOWN;
			hopefullDirections[2] = Snake.UP;
			hopefullDirections[3] = Snake.LEFT;			
		}

		return getDirection(snake, hopefullDirections);
	}


	public static int getDirection(int killerCoords, int mealCoords){
			/*int killerCoordsX = Snake.getX(killerCoords);
			int killerCoordsY = Snake.getY(killerCoords);

			int enemyX = Snake.getX(mealCoords);
			int enemyY = Snake.getY(mealCoords);
			*/
		
		//figure out the longest distance getting to the snakes destination
		int position = this.getKillingEndCoords().intValue();

		int x = Snake.getX(position)
				- Snake.getX(randomLocation);
		int y = Snake.getY(position)
				- Snake.getY(randomLocation);
		
		int hopefullDirection = -1;
		
		//take farthest of the two directions
		if(Math.abs(x) >= Math.abs(y)){
			hopefullDirection = (x > 0) ? Snake.LEFT : Snake.RIGHT;
		} else {
			hopefullDirection = (y < 0) ? Snake.UP : Snake.DOWN;		
		}

		return hopefullDirection; //maybe not:  AIUtils.getDirection(this, hopefullDirection);
	}

	
	//get middle of snake
	public static int getMiddle(Snake snake){
		LinkedList<Integer> body = snake.getBodyCoords();

		return body.get(body.size()/2);
	}
}
