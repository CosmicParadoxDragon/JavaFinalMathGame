/**
 * This is the custom class that is designed to build buttons for the game ui
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MathGameUIButton extends Button
{

	private final Font fontHigh = Font.loadFont(getClass().getResourceAsStream("/PixelSmall.ttf"), 40);
	private final String UNSELECTED_STYLE = "-fx-background-color: transparent; -fx-background-image: url(unselected_game_ui_button.png); -fx-background-size: 301px 41px; -fx-background-position: center; -fx-background-repeat: no-repeat;";
	private final String SELECTED_STYLE = "-fx-background-color: transparent; -fx-background-image: url(selected_game_ui_button.png); -fx-background-size: 301px 41px; -fx-background-position: center; -fx-background-repeat: no-repeat;";
	private AudioClip selectAudioClip, confirmAudioClip;
	/**
	 * Constructor for the MathGameButton
	 * sets styling options in line with the MathGameFX
	 * @param buttonText passed into the Button construtor
	 * 
	 */
	public MathGameUIButton(String buttonText) {
		setStyle(UNSELECTED_STYLE);
		setText(buttonText);
		setFont(fontHigh);	
		setPrefSize(300, 41);
		setTextFill(Color.PURPLE);
		
		setListeners();
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
				setStyle(SELECTED_STYLE);
				selectAudioClip.play();
			}
		});
		setOnMouseExited( new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event)
			{
				setStyle(UNSELECTED_STYLE);
			}
		});
	}
}
