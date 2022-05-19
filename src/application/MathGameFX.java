/**
 * This is the class that builds the main menu for the MathGame Project
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MathGameFX extends Application{
	/* === Fonts === */
	//private Font fontHigh = Font.loadFont(getClass().getResourceAsStream("/PixelHigh.ttf"), 40);
	private Font menuFontHigh = Font.loadFont(getClass().getResourceAsStream("/PixelHigh.ttf"), 120);
	private Font nameEntryFont = Font.loadFont(getClass().getResourceAsStream("/PixelHigh.ttf"), 100);
	/* === Constants === */
	private final int GAME_HIEGHT = 1024, GAME_WIDTH = 1024, MENU_START_X = 200, MENU_START_Y = 250;
	private final int TRANSFORM_DESTINATION_X = -370, TRANSFORM_DESTINATION_Y = 0;
	private File leaderboard_file;
	/* === FX Objects === */
	private Image icon_image, bg_image;
	private AnchorPane anchor;
	private Scene menuScene;
	private Stage menuStage;
	private MathGameStage gameStage;			//Not sure why this is saying unused 
	//private BackgroundImage menuBackdrop;
	private Label tray;

	/* === Custom Objects === */
	MathGameSubscene leaderboard_scene, name_entry, settings_scene;
	Player player_obj;
	LeaderboardReader leaderboard_reader;
	
	/* === MathGame Complex Data Types === */
	List<MathGameButton> menuButtons;
	String name;
	
	/**
	 * MathGame constructor calls all of the helper functions to build the main menu stage
	 * @throws IOException 
	 */
	public MathGameFX() throws IOException 
	{
		loadAssets();
		buildMenuStage(GAME_HIEGHT, GAME_WIDTH);
		setGameBackground();
		
		buildMediaInterface();	
		createMenuTray();
	}
	/**
	 * Helper Function to load external assets
	 * loads the assests that are accessed externally
	 * collected for exception purposes
	 */
	private void loadAssets()
	{
		try 
		{
			icon_image = new Image("/player.png");
			bg_image = new Image("/background_image.png");
			leaderboard_file = new File("assets/external_info/Leaderboard.txt");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	/**
	 * Helper Function to load the leaderboard into current instance
	 * @throws IOException 
	 */
	private void buildLeaderboardScene() throws IOException
	{
		leaderboard_scene = new MathGameSubscene();
		leaderboard_reader = new LeaderboardReader(leaderboard_file, leaderboard_scene); 
		leaderboard_reader.start();
		anchor.getChildren().add(leaderboard_scene);
	}
	/**
	 * helper function containing all the settings for the main menu 
	 * stage
	 * @throws IOException 
	 */
	private void buildMenuStage(int res_x, int res_y) throws IOException
	{
		menuButtons = new ArrayList<MathGameButton>();
		anchor = new AnchorPane();
		menuScene = new Scene(anchor, res_x, res_y);
		menuStage = new Stage();
		menuStage.setScene(menuScene);
		menuStage.initStyle(StageStyle.UNDECORATED);		
		menuStage.getIcons().add(icon_image);	
		buildNameEntry();
		buildSettingsScene();
		buildLeaderboardScene();
		
	}
	private void buildNameEntry()
	{
		name_entry = new MathGameSubscene();
		TextField name_field = new TextField();
		name_field.setText("Enter Name");
		name_field.setFont(nameEntryFont);
		name_field.setRotate(-90);
		name_field.setLayoutX(-215);
		name_field.setLayoutY(315);
		MathGameUIButton confirm_button = new MathGameUIButton("CONFIRM");
		confirm_button.setRotate(-90);
		confirm_button.setLayoutX(140);
		confirm_button.setLayoutY(360);
		
		confirm_button.setOnAction(event -> setName(name_field.getText()));
		
		name_entry.getPane().getChildren().addAll(name_field, confirm_button);
		anchor.getChildren().add(name_entry);
	}
	/**
	 * Setter for the player name
	 * is passed into the gameStage when called
	 * @param new_name player's name
	 * 
	 */
	private void setName(String new_name)
	{
		name = new_name;
		name_entry.rotateIntoFrame();
	}
	/**
	 * Helper function that calls the helper functions to build 
	 * the button tray in the main menu
	 */
	private void createMenuTray()
	{		
		buildMenuTray();
		buildMenuTitle();
		createButton("Name");
		buildStartButton();
		buildLeaderboardButton();
		buildSettingsButton();
		createExitButton();
	}
	
	private void buildSettingsScene()
	{
		int SCENE_WIDTH = 800, SCENE_HEIGHT = 300;
		final Font fontSmall = Font.loadFont(getClass().getResourceAsStream("/PixelSmall.ttf"), 35);
		settings_scene = new MathGameSubscene(SCENE_WIDTH, SCENE_HEIGHT);
		anchor.getChildren().add(settings_scene);
		
		GameStageUILabel height_in_label, width_in_label;
		height_in_label = new GameStageUILabel("Height: ");
		width_in_label = new GameStageUILabel("Width: ");
		
		TextField height_in, width_in;
		height_in = new TextField();
		width_in = new TextField();
		
		height_in.setFont(fontSmall);
		width_in.setFont(fontSmall);
		
		HBox resoultion_box = new HBox();
		resoultion_box.getChildren().addAll(height_in_label, height_in, width_in_label, width_in);
		resoultion_box.setLayoutX(50);
		resoultion_box.setLayoutY(50);
		
		MathGameUIButton confirm_button = new MathGameUIButton("Confirm");
		confirm_button.setLayoutY(SCENE_HEIGHT - 100);
		confirm_button.setLayoutX(SCENE_WIDTH /3);
		confirm_button.setOnAction(event -> this.commitSettings(Double.parseDouble(height_in.getText()), 
					Double.parseDouble(width_in.getText())));
		
		settings_scene.setOnMouseDragged(e -> this.dragStage(e, settings_scene));
		settings_scene.setOnMouseMoved(e -> this.calculateGap(e, settings_scene));
		
		settings_scene.getPane().getChildren().addAll(resoultion_box, confirm_button);
	}
	private double gapX = 0, gapY = 0;
	
	private void calculateGap(MouseEvent event, MathGameSubscene scene) {
		gapX = event.getScreenX() - scene.getLayoutX();
		gapY = event.getScreenY() - scene.getLayoutY();
	}
	
	private void dragStage(MouseEvent event, MathGameSubscene scene) {
		scene.setLayoutX(event.getScreenX() - gapX);
		scene.setLayoutY(event.getScreenY() - gapY);
	}
	
	private void commitSettings(double x, double y)
	{
		AnchorPane anchor_new = new AnchorPane();
		anchor_new.setPrefSize(x, y);
		menuScene = new Scene(anchor_new, x ,y);
		menuStage.setScene(menuScene);
	}
	/**
	 * Function to build the settings button
	 */
	private void buildSettingsButton()
	{
		MathGameButton settingsButton = new MathGameButton("Settings");
		settingsButton.setOnAction(event -> openSettings());
		
		addButton(settingsButton);
	}
	
	/**
	 * Function to open the settings menu
	 */
	private void openSettings()
	{
		settings_scene.toFront();
		settings_scene.moveIntoFrame(-800, TRANSFORM_DESTINATION_Y);
	}
	
	/**
	 * Getter for the menu Stage
	 * @return menuStage
	 */
	public Stage getMathGame() 
	{
		return menuStage;
	}
	/**
	 * Sets the bg_image as the background for the menu stage
	 */
	private void setGameBackground()
	{
		//anchor.setBackground(background);
		anchor.setBackground(new Background (new BackgroundImage( bg_image,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));
	}
	
	/* === Make Title === */
	/**
	 * Helper function to set up the Title on the main menu 
	 * and apply the effects
	 */
	private void buildMenuTitle()
	{		
		Text titleText = new Text();
		titleText.setFont(menuFontHigh);
		titleText.setText("M A T H  G A M E");
		titleText.setFill(Color.WHITE);
		titleText.setLayoutX(250);
		titleText.setLayoutY(120);
		
		DropShadow ds = new DropShadow();  
	    ds.setColor(Color.BLACK);  
	    ds.setHeight(100);  
	    ds.setWidth(150);  
	    ds.setOffsetX(30);  
	    ds.setOffsetY(30);  
	    ds.setSpread(0.5);  
	    ds.setRadius(0.3); 
		titleText.setEffect(ds);
		anchor.getChildren().add(titleText);
	}
	
	/**
	 * Depreciated after revision moved these to the 
	 * custom button class
	 */
	private void buildMediaInterface()
	{
		/*
		URL resource = getClass().getResource("/confirm.wav");
		confirmSound = new AudioClip( resource.toString() );
		*/
	}
	/* === Building Buttons ===  */
	/**
	 * Adds a button to the menu list that populates the
	 * menu tray
	 * @param button Button to add to the menu tray
	 */
	private void addButton(MathGameButton button)
	{
		button.setLayoutX(MENU_START_X);
		button.setLayoutY(MENU_START_Y + menuButtons.size() * 125);
		menuButtons.add(button);
		anchor.getChildren().add(button);
	}
	/**
	 * Creates a name button from a string implements the 
	 * MathGameButton custom class
	 * @param string Text for the button
	 */
	private void createButton(String string)
	{		
		MathGameButton button = new MathGameButton(string);
		button.setOnAction(event -> getName());
	
		
		addButton(button);	
	}
	/**
	 * Specific helper function to build the 
	 * leader board button
	 */
	private void buildLeaderboardButton()
	{
		MathGameButton button = new MathGameButton("Leaderboard");
		addButton(button);
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				leaderboard_scene.moveIntoFrame(TRANSFORM_DESTINATION_X, TRANSFORM_DESTINATION_Y);
				if (!leaderboard_reader.isAlive())
				{
					try 
					{
						leaderboard_reader.join();
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				else
				{
					leaderboard_reader.start();
				}
			}
		});
	}
	/**
	 * Specific helper function to build the start button
	 * then adds it to the tray
	 * handle will check for a name 
	 * 
	 * @contains The ActionEvent to instaniate the GameStage and hide the main menu stage
	 */
	private void buildStartButton()
	{
		MathGameButton button = new MathGameButton("Start");
		button.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event)
			{
				
				if (name == null)
				{
					getName();
					return;
				}
				try 
				{
					gameStage = new MathGameStage(menuStage, name);
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
				menuStage.hide();
			}
		});
		addButton(button);
	}
	/**
	 * Specifc helper funtion to build the Exit button and
	 * adds it to the menu tray
	 * 
	 * @contains The ActionEvent that closes the game
	 */
	private void createExitButton()
	{
		MathGameButton button = new MathGameButton("Exit");
		button.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event)
			{
				try 
				{
					leaderboard_reader.join();
				} catch (InterruptedException e) 
				{	
					e.printStackTrace();
				}
				menuStage.close();
			}
		});
		addButton(button);
	}
	
	/* === Build Stages === */
	/**
	 * Helper function to build the tray on the main menu
	 */
	private void buildMenuTray()
	{
		tray = new Label();
		//tray.setText("S");
		tray.setStyle("-fx-background-image: url(/menu_frame.png); -fx-background-repeat: no-repeat; -fx-background-size: contain;");
		tray.setPrefSize(600, 750);
		tray.setLayoutX(MENU_START_X -90);
		tray.setLayoutY(MENU_START_Y -55);
		
		DropShadow ds = new DropShadow();  
	   
	    ds.setColor(Color.BLACK);  
	    ds.setHeight(100);
	    ds.setWidth(150);  
	    ds.setOffsetX(30);  
	    ds.setOffsetY(30);  
	    ds.setSpread(0.5);  
	    ds.setRadius(0.3);
		tray.setEffect(ds);
		
		anchor.getChildren().add(tray);
	}
	/**
	 * Opens the propmt to enter the player name
	 */
	private void getName()
	{
		name_entry.toFront();
		name_entry.rotateIntoFrame();
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		
		
	}
}
