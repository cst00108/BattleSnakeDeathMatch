/*
 * Forget about 2 snakes dying at once for now
 * Only goes up to 60,000ish x or y before it skrews up.
 */
package snake;

import java.awt.Image;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ImageIcon;

/**
 *
 * @author Jason Donald
 */
public class Snake {
	//the head of the list is the tail of the snake
	private LinkedList<Integer> snakeCoords = new LinkedList<>();
	protected int lastDirection = LEFT;
	private String[] snakeImageFiles = {
		"images/blueish_head.png", //bity end
		"images/blueish.png"	   //body
	}; 
	private boolean dead = false;
	private Image[] snakeImages;
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int NONE = -1;	
	private static final int X_EXTRACTION = getXExtractor();
	private static final int Y_EXTRACTION = getYExtractor();
	public static final int SUGGESTED_SNAKE_LENGTH = 5;
	
	public Snake(int x, int y){
		this(x, y, SUGGESTED_SNAKE_LENGTH);
	}
	
	public Snake(int x, int y, int length, int direction){
		this(x, y, length);
		this.lastDirection = direction;
	}
	
	//merging coords was the stupidest idea
	public Snake(int x, int y, int length){
		int mergeSnakeCoords = getSnakeCoords(x, y);  //put snake coordinates 
													  //togther
		
		for(int index=0; index<length; index++){
			snakeCoords.add(mergeSnakeCoords);
		}
	}
	
	//make the snake deceased
	public void setDead(){
		dead = true;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void setImages() {
		snakeImages = new Image[snakeImageFiles.length];
		
		for(int index=0; index<snakeImageFiles.length; index++){
			URL imgURL = Snake.class.getResource(snakeImageFiles[index]);
			if (imgURL != null) {
				snakeImages[index] = new ImageIcon(imgURL).getImage();
			} else {
				System.err.println("Couldn't find file: " + snakeImageFiles);
					snakeImages[index] = null;
			}
		}
	}

	public Image getKillingEndImage(){
		return snakeImages[0];
	}

	public Image getBodyImage(){
		return snakeImages[1];
	}
	
	public void setImageFiles(String[] images){
		snakeImageFiles[0] = images[0]; //bity end
		snakeImageFiles[1] = images[1]; //body
		
		setImages();
	}
	
	/**
	 * Get coords if snake chose  
	 * @param direction One of the four directions the snake can move.
	 * @return The new coordinates of the snakes bity end if we actually 
	 * did move that way.  Used for computer to guess where to move next.
	 */
	public int getCoordsForBityEnd(int direction){
		int oldBityEnd = getKillingEndCoords();
		
		if(direction == UP){
			return oldBityEnd + 1;
		} 
		
		if(direction == DOWN){
			return oldBityEnd - 1;
		}
		
		if(direction == LEFT){
			int x = getX(oldBityEnd) - 1;
			int y = getY(oldBityEnd);
			
			return getSnakeCoords(x, y);
		}
		
		if(direction == RIGHT){
			int x = getX(oldBityEnd) + 1;
			int y = getY(oldBityEnd);

			return getSnakeCoords(x, y);
		}
		
		return -1; //didnt choose right direction
	}
		
	//merges the x and y
	protected static int getSnakeCoords(int x, int y){
		int toReturn = x;
		toReturn = toReturn << 16;
		toReturn = toReturn | y;

		return toReturn;
	}
	
	//getXExtraction() is the same as getYExtraction() but...
	//Made a stupid.  No reason for this method.
	private static int getXExtractor(){
		int toReturn = getYExtractor();
		return toReturn << 16;  //returns 0b11111111111111110000000000000000
	}

	private static int getYExtractor(){
		int toReturn = 1;
		
		for(int index=0; index<15; index++){
			toReturn = toReturn << 1 | 1;
		}

		return toReturn;  //returns 0b00000000000000001111111111111111
	}

	public static int getX(int snakeCoords){
		return snakeCoords >>> 16;
	}
	
	public static int getY(int snakeCoords){
		return snakeCoords & Y_EXTRACTION;
	}
	
	public void grow(int segments){
		for(int index=0; index<segments; index++){
			snakeCoords.addLast(snakeCoords.getLast());
		}
	}
	
	public int[] getBityXY(){
		return getXY(snakeCoords.getFirst());
	}
	
	public static int[] getXY(int coords){
		return new int[]{getX(coords), getY(coords)};
	}
	
	public Integer getKillingEndCoords(){
		return snakeCoords.getFirst();
	}
	
	//get all coords exept the bity end of the snake
	public LinkedList<Integer> getBodyCoords(){
		LinkedList<Integer> toReturn = (LinkedList)snakeCoords.clone();

		toReturn.removeFirst();

		return toReturn;
	}
	
	//checks if bity end comes into other snakes body
	public boolean inRange(Snake other, int range){
		int thisX = Snake.getX(this.getKillingEndCoords());
		int thisY = Snake.getY(this.getKillingEndCoords());

		Iterator<Integer> otherCoordsIterator = other.getBodyCoords().iterator();
		
		while(otherCoordsIterator.hasNext()){
			Integer otherCoords = otherCoordsIterator.next();

			int otherX = Snake.getX(otherCoords);
			int otherY = Snake.getY(otherCoords);

			int total = Math.abs(thisX - otherX);
			total += Math.abs(thisY - otherY);

			if(total <= range){
				return true;
			}	
		}
	
		return false;
	}
	
	public void move(){
		move(lastDirection);
	}
	
	public void move(int direction){
		snakeCoords.removeLast();  

		int oldBityEnd =  //get the old position of the snakes head
				snakeCoords.getFirst();

		if(direction == UP){
			snakeCoords.addFirst(oldBityEnd + 1);
			lastDirection = UP;
		} else if(direction == DOWN){
			snakeCoords.addFirst(oldBityEnd - 1);
			lastDirection = DOWN;
		} else if(direction == LEFT){
			int x = getX(oldBityEnd) - 1;
			int y = getY(oldBityEnd);
			snakeCoords.addFirst(getSnakeCoords(x, y));
			lastDirection = LEFT;
		} else if(direction == RIGHT){
			int x = getX(oldBityEnd) + 1;
			int y = getY(oldBityEnd);
			snakeCoords.addFirst(getSnakeCoords(x, y));
			lastDirection = RIGHT;
		}
	}
	
	public static void main(String[] aaarg){
		Snake snake = new Snake(1, 3, 10);
		
		Iterator<Integer> iterator = snake.snakeCoords.iterator();
		
		while(iterator.hasNext()){
			int num = iterator.next().intValue();
			System.out.println(Integer.toBinaryString(num));
			System.out.println(Integer.toBinaryString(getX(num)));
			System.out.println(Integer.toBinaryString(getY(num)));
		}
		
		snake.grow(2);
		
		int[] moves = {
				RIGHT, RIGHT, RIGHT, RIGHT, 
				UP, 	UP, 	UP, 
				RIGHT, RIGHT, 
				DOWN, DOWN, 
				RIGHT, RIGHT, 
				UP, 	UP
		};
		
/*		snake.move(LEFT);
		snake.move(LEFT);
		snake.move(LEFT);
		snake.move(LEFT);
*/				

		for(int index=0; index<moves.length; index++){
			snake.move(moves[index]);
			drawOnCommandPrompt(snake.snakeCoords.iterator());
			
			try{
				Thread.sleep(250);
			} catch(InterruptedException x){
				System.err.println("woops");
			}
		}
	}
	
	private static void drawOnCommandPrompt(Iterator<Integer> iterator){
		char[][] screen = new char[50][50];
		System.out.println("WHAT THE char preset:  " + screen[0][0]);
		
		for(int yIndex=0; yIndex<screen.length; yIndex++){
			for(int xIndex=0; xIndex<screen[0].length; xIndex++){
				screen[yIndex][xIndex] = ' ';
			}
		}
		
		while(iterator.hasNext()){
			int num = iterator.next().intValue();
			screen[getY(num)][getX(num)] = '@';
		}
		
		for(int yIndex=screen.length-1; yIndex>=0; yIndex--){
			for(int xIndex=0; xIndex<screen[0].length; xIndex++){
				System.out.print(screen[yIndex][xIndex]);
			}
			
			System.out.println();
		}		
	}
	
}