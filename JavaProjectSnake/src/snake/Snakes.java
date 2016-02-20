/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Owner
 */
public class Snakes {
	private Set<Snake> snakes = new HashSet<>();
	private Map<Integer, Snake> coords = new TreeMap<>();
	private Map<Integer, Snake> snakesBityEnds = new HashMap<>(); //look up what will tell me what bity ends colided
	private Set<Snake> deadSnakes = new HashSet<>();
	
	public Iterator<Snake> getSnakes(){
		return snakes.iterator();
	}
	
	public void addSnake(Snake snake){
		snakes.add(snake);
	}
	
	public void addSnake(int x, int y){
		snakes.add(new Snake(x, y));
	}
	
	/**
	 * Makes the snakes move a step.
	 * @return The number of snakes that got removed because they died.
	 */
	public int move(){
		int toReturn = 0;
		Iterator<Snake> iterator = snakes.iterator();
		
		while(iterator.hasNext()){
			Snake snake = iterator.next();

			snake.move();
			
			Snake killedSnake = snakesBityEnds.put(
					snake.getKillingEndCoords(), snake);
			
			//if two snakes killing ends landed on the same square, 
			//knock em both off
			if(killedSnake != null){
				deadSnakes.add(killedSnake);
				deadSnakes.add(snake);
			}
			
			//put bodies in TreeMap
			Iterator<Integer> body = snake.getBodyCoords().iterator();
			
			while(body.hasNext()){
				coords.put(body.next(), snake);
			}

			//XXXXXXXX this was a blank space.  check method

		}
		
		//check if snakes bity ends hit a body 
		iterator = snakes.iterator();
		while(iterator.hasNext()){
			Snake snake = iterator.next();
			Snake killedSnake = coords.put(snake.getKillingEndCoords(), null);
			
			if(killedSnake != null){
				deadSnakes.add(killedSnake);
				snake.grow(10);
			}
		}
		
		//remove the dead snakes
		iterator = deadSnakes.iterator();
		while(iterator.hasNext()){
			snakes.remove(iterator.next());
			toReturn++;
		}
		
		//get ready for next move.
/*		deadSnakes.removeAll(deadSnakes);
		
		Iterator coordsToRemove = coords.keySet().iterator();
		while(coordsToRemove.hasNext()){
			coords.remove(coordsToRemove.next());		
		}
*/
		coords = new TreeMap<>();
		deadSnakes = new HashSet<>();
		snakesBityEnds = new TreeMap<>();

		return toReturn;
	}

	public static void main(String[] aaarg){
		Snakes snakes = new Snakes();
		Arena.setBounds(20, 20);
		
		//add snakes
		for(int index=0; index<20; index++){
			snakes.addSnake(new AI02(6));
		}

		//move snakes
		for(int index=0; index<100; index++){
			drawOnCommandPrompt(snakes.getSnakes());		
			snakes.move();
			System.out.println("Step:  " + index);
		}
	}

	private static void drawOnCommandPrompt(Iterator<Snake> snakesIterator){
		//set blank screen
		char[][] screen = new char[Arena.getYBounds()][Arena.getXBounds()];

		for(int yIndex=0; yIndex<screen.length; yIndex++){
			for(int xIndex=0; xIndex<screen[0].length; xIndex++){
				screen[yIndex][xIndex] = ' ';
			}
		}

		while(snakesIterator.hasNext()){
			Snake snake = snakesIterator.next();
			
			int killingEndCoords = snake.getKillingEndCoords().intValue();
			int killingX = Snake.getX(killingEndCoords);
			int killingY = Snake.getY(killingEndCoords);
			screen[killingY][killingX] = 'X';
			
			/*
			if(snake instanceof AI02){
				AI02 xMarksTheSpot = (AI02)snake;
				int x = Snake.getX(xMarksTheSpot.tempRand());
				int y = Snake.getY(xMarksTheSpot.tempRand());
				screen[y][x] = 'X';
			}
			*/
			
			Iterator<Integer> bodyIterator = snake.getBodyCoords().iterator();
			while(bodyIterator.hasNext()){

				while(bodyIterator.hasNext()){
					int body = bodyIterator.next().intValue();
					screen[Snake.getY(body)][Snake.getX(body)] = 'O';
				}
			}

		}

		//draw
		StringBuilder display = new StringBuilder();

		for(int yIndex=screen.length-1; yIndex>=0; yIndex--){

			if(yIndex < 10){
				display.append(' ');
			}
			display.append(yIndex);

			for(int xIndex=0; xIndex<screen[0].length; xIndex++){
				display.append(screen[yIndex][xIndex]);
			}

			display.append(System.getProperty("line.separator"));
		}

		System.out.println(display);
	}	
	
	/**
	 * 
	 * @param snake The snake you want to look for others
	 * @param range The distance you want to look.
	 * @return Snake you got in your sights.
	 */
	public Snake inRange(Snake killerSnake, int range){
		Iterator<Snake> snakeIterator = getSnakes();
		
		while(snakeIterator.hasNext()){
			Snake killMaybe = snakeIterator.next();
			
			if(!killMaybe.isDead() && killerSnake.inRange(killMaybe, range)){
				return killMaybe;
			}
		}
		
		return null;
	}
}