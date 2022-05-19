package application;
/**
 * This is the projectile class that operates as a sprite for the projectile
 * Stores the answer that the projectile repersents
 * 
 * @author SohrabKazak
 * @prof Gao
 * @Course CS170 - Intermediate Java
 * @Org Ohlone College
 * 
 * @date May 10, 2022
 * 
 */

import javafx.scene.image.ImageView;

public class Projectile extends ImageView{

	private int answer;
	private double VELOCITY = 15;
	private double pos_y;
	private final static String bullet_image = "/bullet_image.png";
	
	public Projectile(int ans, double pos_y, double pos_x)
	{
		super(bullet_image);
		this.answer = ans;
		this.pos_y = pos_y;
		setLayoutX(pos_x);
		setLayoutY(pos_y);
	}
	public void move()
	{
		if (!intersects())
		{
			setLayoutY(pos_y -= VELOCITY);
		}
	}
	
	public boolean intersects()
	{
		if (pos_y > -100)
		{
			return false;
		}
		return true;
	}
	public int getAnswer()
	{
		return answer;
	}
	public double get_x()
	{
		return getLayoutX() + 7;
	}
	public double get_y()
	{
		return getLayoutY() + 4;
	}
}
