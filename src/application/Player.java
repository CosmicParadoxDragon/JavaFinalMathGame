/**
 * This is the player class that operates as a sprite for the player
 * 
 * @author SohrabKazak
 * @prof Gao
 * @Course CS170 - Intermediate Java
 * @Org Ohlone College
 * 
 * @date May 10, 2022
 * 
 */

package application;

import javafx.scene.image.ImageView;

public class Player extends ImageView{
	//private final int GAME_HEIGHT = 1024;
	private final int GAME_WIDTH = 1024;
	private final int PLAYER_VELOCITY = 5;
	private final int BOARDER = 30;
	private double pos_x;//, pos_y;
	
	public Player() {
		super("/player.png");
		pos_x = 500;
		//pos_y = 1500 - 50;
	}
	/**
	 * move the player right bounds on the right side near the edge
	 */
	protected void move_right()
	{
		if (pos_x < GAME_WIDTH - BOARDER)
		{
			setLayoutX(pos_x += PLAYER_VELOCITY);			
		}
	}
	/**
	 * move the player to the left until the bound
	 */
	protected void move_left()
	{
		if (pos_x > BOARDER)
		{
			setLayoutX(pos_x -= PLAYER_VELOCITY);			
		}
	}
	/**
	 * shortcut set the position
	 * this solution proves much more stable that the move option
	 * @param x Positon to move to
	 */
	protected void setPosX(double x)
	{
		setLayoutX(x);
	}
}
