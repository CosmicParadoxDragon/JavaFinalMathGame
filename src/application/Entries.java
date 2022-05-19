/**
 * This is extension of the StackPane class to build labels to display
 * the entries in the leaderboard class
 * This was supposed to contain a background image for each entry but 
 * visually it looks good with it
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

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Entries extends StackPane{
	private final Font fontSmall = Font.loadFont(getClass().getResourceAsStream("/PixelSmall.ttf"), 35);
	Label text;
	Image image;
	
	public Entries(String text_entry) {
		
		text = new Label(text_entry);
		text.setFont(fontSmall);
		text.setTextFill(Color.WHITE);
		text.setTextAlignment(TextAlignment.LEFT);
		text.setPrefSize(300, 10);
		getChildren().add(text);
	}
}
