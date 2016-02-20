/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

/**
 *
 * @author Owner
 */
public class Arena {
	private static int xBounds = 80;
	private static int yBounds = 80;
	
	public static void setBounds(int x, int y){
		xBounds = x;
		yBounds = y;
	}
	
	public static int getXBounds(){
		return xBounds;
	}

	public static int getYBounds(){
		return yBounds;
	}
}