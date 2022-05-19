package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameStageUILabel extends Label{
	private final Font fontHigh = Font.loadFont(getClass().getResourceAsStream("/PixelMiddle.ttf"), 35);

	public GameStageUILabel(String text) {
		super (text);
		setFont(fontHigh);
		setTextFill(Color.WHITE);
	}
}
