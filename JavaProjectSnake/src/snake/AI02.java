package snake;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jay
 */
public class AI02 extends Snake{
	private int randomLocation = getRandomLocation();

	public AI02(int x, int y){
		super(x, y, 3);
	}
	
	public AI02(int x, int y, int length){
		super(x, y, length);
	}
	
	public AI02(){
		this( //send it to current objects constructor
				AIUtils.getRandomish(Arena.getXBounds()),
				AIUtils.getRandomish(Arena.getYBounds()));			
	}
	
	public AI02(int length){
		this();
		super.grow(length);
	}

	private int getRandomLocation(){
		return super.getSnakeCoords(
				AIUtils.getRandomish(Arena.getXBounds()),
				AIUtils.getRandomish(Arena.getYBounds()));
	}
	
	public void move(){
		
		//if snakes at it's destination, get a new one
		if(super.getKillingEndCoords() == randomLocation){
			int check = getRandomLocation();
			
			if(check == randomLocation){
				move();
				return;
			} else {
				randomLocation = check;
			}
		}
		
		//figure out the longest distance getting to the snakes destination
		int position = this.getKillingEndCoords().intValue();

		int x = Snake.getX(position)
				- Snake.getX(randomLocation);
		int y = Snake.getY(position)
				- Snake.getY(randomLocation);
		
		int hopefullDirection = -1;
		
		//take farthest of the two directions
		if(Math.abs(x) >= Math.abs(y)){
			hopefullDirection = (x > 0) ? LEFT : RIGHT;
		} else {
			hopefullDirection = (y < 0) ? UP : DOWN;		
		}
		
		//move the snake
		move(AIUtils.getDirection(this, hopefullDirection));
	}
}