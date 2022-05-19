/**
 * This is the enemy class that operates as a sprite for the enemies
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

import java.util.Random;

import javafx.scene.image.ImageView;

public class Enemies extends ImageView{
	
	private final int GAME_HEIGHT = 1024;
	private final int GAME_WIDTH = 1024;
	private final int BOTTOM_GAME_BOUNDARY = 200;
	private double y_velocity = 1.0; 
	private int x_velocity = 1; 
	private double pos_x, pos_y;
	private boolean alive;
	Random randomSpawnLocation;

	//private final ImageView sprite;
	
	public Enemies()
	{
		super("/enemy.png");
		randomSpawnLocation = new Random();
		pos_x = randomSpawnLocation.nextDouble(0, GAME_HEIGHT - 600);
		pos_y = randomSpawnLocation.nextDouble(0, GAME_WIDTH - BOTTOM_GAME_BOUNDARY);
		setLayoutX(pos_x);
		setLayoutY(pos_y);
		alive = true;
	}
	
	private void strafe()
	{
		setLayoutX(pos_x += x_velocity);
		if (pos_x > GAME_WIDTH - 50 || pos_x < 10) 
		{
			x_velocity *= -1;
		}
	}
	private void move_down()
	{
		setLayoutY(pos_y += y_velocity);			
		if (pos_y < 10 || pos_y > GAME_HEIGHT - BOTTOM_GAME_BOUNDARY)
		{
			y_velocity *= -1;
		}
	}
	
	protected void move()
	{
		if (alive)
		{
			strafe();
			move_down();			
		}
		else
		{
			destroy();
		}
	}
	private void destroy()
	{
		alive = false;
	}
	public double get_x()
	{
		return getLayoutX() + 16;
	}
	public double get_y()
	{
		return getLayoutY() + 16;
	}
}
