package application;
//import javafx.scene.layout.BorderPane;
//Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application 
{
	public static void main(String[] args)
	{
		launch(args);
	}
	
	@Override
	public void start(Stage MainStage) throws Exception
	{
		try {
			MathGameFX mathGame = new MathGameFX();
			MainStage = mathGame.getMathGame();
			MainStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
