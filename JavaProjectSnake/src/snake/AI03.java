/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.util.Iterator;

/**
 *
 * @author jay
 */
public class AI03 extends AI02{
	Snakes snakes;
	Snake attack = null;
	int range = 10;
	
/*	
	public AI03(int x, int y){
		super(x, y, 3);
	}
	
	public AI03(int x, int y, int length){
		super(x, y, length);
	}
	
	private AI03(){
		this( //send it to current objects constructor
				AIUtils.getRandomish(Arena.getXBounds()),
				AIUtils.getRandomish(Arena.getYBounds()));			
	}
	
	public AI03(int length){
		this();
		super.grow(length);
	}
	
	public AI03(Snakes snakes){
		this();
		//this.snakes = snakes;
		setSnakes(snakes);
	}
	
	//sets how many steps away enemy snake has to be to bother attacking
	public void setRange(int range){
		this.range = range;
	}
	
	public void setSnakes(Snakes snakes){
		this.snakes = snakes;
	}
	
	//check if 
	private int getDirectionToTeamUp(Snakes snakes, int range){
		Iterator<Snake> snakesIterator = snakes.getSnakes();
		
		while(snakesIterator.hasNext()){
			Snake otherSnake = snakesIterator.next();
			
			if(!(otherSnake instanceof AI03)){ //it's one of ours don't touch him
				continue;						//unless run into him by chance
			}
			
			this.inRange(otherSnake, range);
		}
		
		
	}
	
	public void move(){
		if(attack==null || attack.isDead()){ //if other snakes dead
			attack = snakes.inRange(attack, range);
			
			if(attack == null){ //
				super.move();
				//AIUtils.getRandomDirection(this); <== I dont think i need, think it over when thinking straight
			} else if(!attack.isDead()){ //dont think i need isDead()
				int middle = AIUtils.getMiddle(attack);
				this.getKillingEndCoords()
			}
			
		}
		
		
	}
*/
}
