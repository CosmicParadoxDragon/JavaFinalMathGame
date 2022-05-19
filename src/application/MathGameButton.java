/**
 * This is the custom class that is designed to build buttons
 * for the MathGame project
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

import java.net.URL;
//import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.Media;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MathGameButton extends Button{
	//private MediaPlayer mediaPlayer, mediaPlayerMenuSelect, mediaPlayerMenuConfirm;
	//private Media menuSoundConfirm, menuSoundSelect;
	
	private final Font fontHigh = Font.loadFont(getClass().getResourceAsStream("/PixelHigh.ttf"), 40);
	private final String STYLE = "-fx-background-color: transparent; -fx-background-image: url(/main_button_3.png); -fx-background-size: 300px 112px; -fx-background-position: center; -fx-background-repeat: no-repeat;";
	private AudioClip selectAudioClip, confirmAudioClip;
	/**
	 * Constructor for the MathGameButton
	 * sets styling options in line with the MathGameFX
	 * @param buttonText passed into the Button construtor
	 * 
	 */
	public MathGameButton(String buttonText) {
		setStyle(STYLE);
		setText("      "+buttonText);
		setFont(fontHigh);	
		setPrefSize(300, 120);
		setTextFill(Color.PURPLE);
		setListeners();
		
		DropShadow ds = new DropShadow();  
	    ds.setColor(Color.WHITE);  
	    ds.setHeight(100);  
	    ds.setWidth(150);

	    ds.setBlurType(BlurType.GAUSSIAN);
		setEffect(ds);
		
		buildMediaInterface();
	}
	/**
	 * Creates the audio setup for the buttons
	 */
	private void buildMediaInterface()
	{
		URL resource_select = getClass().getResource("/select.wav");
		selectAudioClip = new AudioClip(resource_select.toString());
		
		URL resource_confirm = getClass().getResource("/confirm.wav");
		confirmAudioClip = new AudioClip(resource_confirm.toString());
	}
	/**
	 * Creates the basic listeners for the buttons for the 
	 * MathGame changes colors and playing AudioClips
	 */
	private void setListeners()
	{
		setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				confirmAudioClip.play();	
			}
		});
		setOnMouseEntered( new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				setTextFill(Color.GREEN);
				selectAudioClip.play();
			}
		});
		setOnMouseExited( new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				setTextFill(Color.PURPLE);
			}
		});
	}
}
