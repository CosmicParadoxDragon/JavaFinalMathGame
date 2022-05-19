/**
 * This is the subclass of the SubScene class that is designed to build a 
 * subscene for the MathGame project
 * 
 * @author SohrabKazak
 * @prof Gao
 * @Course CS170 - Intermediate Java
 * @Org Ohlone College
 * 
 * @date May 11, 2022
 * 
 */


package application;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MathGameSubscene extends SubScene{
	
	private final String BG_IMAGE_PATH = "/subscene_background.png";
	private final String FONT_PATH = "/PixelSmall.ttf";
	
	private boolean onScreen = false;
	BackgroundImage bg_image;
	Font smallPixel;
	AnchorPane root;
	/*
	 * Constructor for the subscene sets the inital properties in
	 * theme for the MathGame project
	 */
	public MathGameSubscene()
	{
		super(new AnchorPane(), 350, 800);
		prefWidth(350);
		prefHeight(800);
		try 
		{
			bg_image = new BackgroundImage(new Image(BG_IMAGE_PATH, 350, 800, false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
			smallPixel = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		root = (AnchorPane) this.getRoot();
		
		root.setBackground(new Background(bg_image));
		setEffects();
		setLayoutX(1026);
		setLayoutY(180);
	}
	public MathGameSubscene(int size_x, int size_y)
	{
		super(new AnchorPane(), size_x, size_y);
		prefWidth(size_x);
		prefHeight(size_y);
		try 
		{
			bg_image = new BackgroundImage(new Image(BG_IMAGE_PATH, size_x, size_y, false, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
			smallPixel = Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 20);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		root = (AnchorPane) this.getRoot();
		
		root.setBackground(new Background(bg_image));
		setEffects();
		setLayoutX(1026);
		setLayoutY(180);
	}
	
	/**
	 * builds and set the effects for this base subscene
	 * 
	 * Currently: DropShadow
	 */
	private void setEffects()
	{
		DropShadow ds = new DropShadow();  
		   
	    ds.setColor(Color.BLACK);  
	    ds.setHeight(100);
	    ds.setWidth(150);  
	    ds.setOffsetX(30);  
	    ds.setOffsetY(30);  
	    ds.setSpread(0.5);  
	    ds.setRadius(0.3);
		setEffect(ds);
	}
	/*
	 * Build and apply the translation transition that slides the 
	 * subscene onto the stage
	 */
	void moveIntoFrame(int dest_x, int dest_y)
	{
		TranslateTransition tt = new TranslateTransition();
		tt.setDuration(Duration.seconds(0.2));
		tt.setNode(this);
		if(!onScreen)
		{
			tt.setToX( dest_x );
			onScreen = true;
		}
		else if(onScreen)
		{
			tt.setToX(0);
			onScreen = false;
		}
		tt.play();
	}
	/**
	 * build and set the rotation and translation transitions
	 * to rotate the scene so it is longer on the horizontal axis 
	 */
	void rotateIntoFrame()
	{
		TranslateTransition tt = new TranslateTransition();
		RotateTransition rt = new RotateTransition();
		tt.setDuration(Duration.seconds(0.2));
		rt.setDuration(Duration.seconds(0.2));
		tt.setNode(this);
		rt.setNode(this);
		if(!onScreen)
		{
			rt.setToAngle(90);
			tt.setToX( -770 );
			onScreen = true;
		}
		else if(onScreen)
		{
			tt.setToX(0);
			rt.setToAngle(0);
			onScreen = false;
		}
		tt.play();
		rt.play();
	}
	/**
	 * build and play the tansitions for the settings panel
	 *
	 */
	void scaleOpen()
	{
		TranslateTransition tt = new TranslateTransition();
		RotateTransition rt = new RotateTransition();
		ScaleTransition st = new ScaleTransition();
		tt.setDuration(Duration.seconds(0.2));
		rt.setDuration(Duration.seconds(0.2));
		st.setDuration(Duration.seconds(0.4));
		tt.setNode(this);
		rt.setNode(this);
		st.setNode(this);
		if(!onScreen)
		{
			//rt.setToAngle(90);
			tt.setToX( -570 );
			st.setToX(2);
			
			onScreen = true;
		}
		else if(onScreen)
		{
			tt.setToX(0);
			rt.setToAngle(0);
			st.setToX(0);
			onScreen = false;
		}
		
		try 
		{
			tt.play();
			rt.play();
			st.play();			
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	/**
	 * getter for the root pane
	 * @return the root AnchorPane of the subscene
	 */
	public AnchorPane getPane()
	{
		return root;
	}
	
}
