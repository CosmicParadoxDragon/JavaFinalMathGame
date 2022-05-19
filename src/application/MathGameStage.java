/**
 * This is the class that builds the game stage for the MathGame Project
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MathGameStage {
	/* === Game Constants === */
	private final int GAME_HEIGHT = 1024;
	private final int GAME_WIDTH = 1024;
	private final int PLAYER_LOCATION_OFFSET = 190;
	private final int BOTTOM_MENU_OFFSET = 150;
	private final int BOTTOM_MENU_BUTTON_BOX_OFFSET = 370;	
	private final int UPPER_BOX_OFFSET_Y = 10;
	private final int UPPER_BOX_OFFSET_X = 10;
	private final int ENEMY_RADIUS = 32;
	private final int BULLET_RADIUS = 5;
	/* === Game Fonts === */
	private Font boardFont;
	private Font fontHigh;
	/* === Game Primatives === */
	private long gameTime = 0;
	private int score_ = 0;
	private long gameStart;
	private boolean movingLeft = false, movingRight = false;
	private int answer;
	/* === Game Custom Objects === */
	private Player player_obj;
	private MathGameUIButton menuButton, leaderboardButton;
	private MathGameSubscene leaderboard_scene;
	private LeaderboardReader leaderboard_reader;
	/* === Game JavaFX Objects === */
	private AnimationTimer gameTimer;
	private Label time_label, score_label, ammo_label, score;
	private Label time_played, problem_board;
	private TextField ammo_answer;
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage, storedMenu;
	private Image bg_image, bottom_menu_image;
	private URL bg_music, hit_sound, shot_sound, fail_sound;
	private AudioClip weaponSound, weaponFail, hitSoundClip;
	private Media gameBackgroundMusic;
	private MediaPlayer gameBackgroundMusicPlayer;
	/* === Game Complex Types === */
	private String problem, name;
	private List<Projectile> projectiles;
	private List<Enemies> enemies;
	private File leaderboard_file;
	
	/**
	 * Basic constructor that uses helper functions to build the GameStage
	 * @throws IOException 
	 */
	public MathGameStage(Stage storedMenu, String name) throws IOException
	{
		this.storedMenu = storedMenu;
		this.name = name;
		loadAssets();
		buildMediaInterface();
		buildGameStage();
		createSpawner();
		buildGameStageUI();
		loadLeaderboard();
		showPlayerObj();
		createListeners();
		createGameLoop();
	}
	/**
	 * Helper Function to 
	 * Loads all the assets that are external 
	 */
	private void loadAssets()
	{
		try 
		{
			bg_music = getClass().getResource("/game_music.wav");
			hit_sound = getClass().getResource("/hit_notice.wav");
			shot_sound = getClass().getResource("/shot_sound.wav");
			fail_sound = getClass().getResource("/failed.mp3");
			bg_image = new Image ("/space_background_2.png", GAME_WIDTH, GAME_HEIGHT, false, true);
			bottom_menu_image = new Image("/bottom_menu_image.png", GAME_WIDTH, BOTTOM_MENU_OFFSET, false, true);
			boardFont = Font.loadFont(getClass().getResourceAsStream("/PixelHigh.ttf"), 250);
			fontHigh = Font.loadFont(getClass().getResourceAsStream("/PixelHigh.ttf"), 40);
			leaderboard_file = new File("assets/external_info/Leaderboard.txt");
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	/**
	 * builds the leaderboard sun scene perfroms a runLater, to puase the execution of a 
	 * thread at runtime.
	 * There are interfering JavaFX threads during that time that produces Exceptions
	 * @throws IOException
	 */
	private void loadLeaderboard() throws IOException
	{	
		leaderboard_scene = new MathGameSubscene();
		leaderboard_scene.setLayoutY(10);
		leaderboard_scene.setOpacity(0.6);
		leaderboard_reader = new LeaderboardReader(leaderboard_file, leaderboard_scene);
		Platform.runLater(leaderboard_reader);
		gamePane.getChildren().add(leaderboard_scene);
		
	}
	/**
	 * Helper Function to
	 * instansiates all the audio componets required for the GameStage
	 */
	private void buildMediaInterface()
	{
		
		gameBackgroundMusic = new Media( bg_music.toString() );
		gameBackgroundMusicPlayer = new MediaPlayer(gameBackgroundMusic);
		gameBackgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		hitSoundClip = new AudioClip( hit_sound.toString() );	
		weaponSound = new AudioClip( shot_sound.toString() );	
		weaponFail = new AudioClip( fail_sound.toString() );
	}
	/**
	 * Helper Function to
	 * Set up the options for the Stage and intsanisates the
	 * other components
	 */
	private void buildGameStage()
	{
		enemies = new ArrayList<Enemies>();
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_HEIGHT, GAME_WIDTH);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		gameStage.initStyle(StageStyle.UNDECORATED);
		gameStage.setHeight(GAME_HEIGHT);
		gameStage.setWidth(GAME_WIDTH);
		gameStage.getIcons().add(new Image("/player.png"));
		
		//gameCanvas.setOnMouseMoved(e -> player_positionX = e.getX());
		gamePane.setBackground(new Background (new BackgroundImage( bg_image,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT)));
		
		gameBackgroundMusicPlayer.play();
		gameStage.show();
	}
	/**
	 * Function to create an enemy object and add it to the list 
	 * and the pane. Create a new problem to solve and update the 
	 * problem_board
	 */
	private void spawnEnemy()
	{
		Enemies tmp = new Enemies();
		enemies.add(tmp);
		gamePane.getChildren().add(tmp);
		generate_problem();
		updateProblemBoard();
	}
	/**
	 * Create a new math that is two random numbers between 0 and 20 which are either 
	 * added or subtracted
	 */
	private void generate_problem()
	{
		Random r = new Random();
		int a = r.nextInt(20);
		int b = r.nextInt(20);
		boolean o = r.nextBoolean();
		if (o)
		{
			problem = Integer.toString(a) + " + " + Integer.toString(b);
			answer = a + b;			
		}
		if (!o)
		{
			if (b > a)
			{
				a += b;
				b = a - b;
			    a -= b;
			}
			problem = Integer.toString(a) + " - " + Integer.toString(b);
			answer = a - b;
		}
	}
	/**
	 * Function to update the problem board with a new 
	 * math problem
	 */
	private void updateProblemBoard()
	{
		problem_board.setText(problem);
		problem_board.setStyle("-fx-text-fill: white;");
	}
	/**
	 * Helper function that creates all the UI Elements
	 * of the Game Stage
	 * 
	 * monolithic function
	 * TODO split if time allows
	 */
	private void buildGameStageUI()
	{	
		
		ImageView bottom_menu = new ImageView(bottom_menu_image);
		bottom_menu.setLayoutX(0);
		bottom_menu.setLayoutY(GAME_HEIGHT - BOTTOM_MENU_OFFSET);
		
		problem_board = new Label();
		problem_board.setLayoutX(300);
		problem_board.setLayoutY(300);
		problem_board.setFont(boardFont);
		
		updateProblemBoard();
		
		time_label = new GameStageUILabel("Time Elapsed: ");
	
		time_played = new GameStageUILabel("");

		HBox time_box = new HBox();
		time_box.getChildren().addAll(time_label, time_played);
		time_box.setLayoutY(75);
		
		score = new GameStageUILabel(Integer.toString(score_));
		
		score_label = new GameStageUILabel("Score: ");
		
		HBox score_box = new HBox();
		score_box.getChildren().addAll(score_label, score);

		VBox upper_box = new VBox();
		upper_box.setLayoutY(UPPER_BOX_OFFSET_X);
		upper_box.setLayoutX(UPPER_BOX_OFFSET_Y);
		upper_box.getChildren().addAll(time_box, score_box);
		
		ammo_label = new GameStageUILabel("Load Answer: ");
		ammo_label.setTextFill(Color.GREEN);
		
		ammo_answer = new TextField();
		ammo_answer.setFont(fontHigh);
		ammo_answer.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		
		HBox ammo_box = new HBox(); 
		ammo_box.setLayoutY(GAME_WIDTH - BOTTOM_MENU_OFFSET + 10);
		ammo_box.setLayoutX(GAME_HEIGHT/2 - 240);
		ammo_box.getChildren().addAll(ammo_label, ammo_answer);

		buildGameUILeaderboardButton();
		buildGameUIMenuButton();
		
		VBox buttons_box = new VBox();
		buttons_box.setLayoutY(GAME_HEIGHT - BOTTOM_MENU_OFFSET + 7);
		buttons_box.setLayoutX(GAME_WIDTH - BOTTOM_MENU_BUTTON_BOX_OFFSET);
		buttons_box.getChildren().addAll(leaderboardButton, menuButton);
		buttons_box.toFront();
		
		GameStageUILabel player_name = new GameStageUILabel(name);
		player_name.setLayoutY(30);
		player_name.setLayoutX(480);

		gamePane.getChildren().addAll( player_name, bottom_menu, upper_box, ammo_box, buttons_box, problem_board);
		projectiles = new ArrayList<Projectile>();
	}
	/**
	 * Helper function that calls the movement function of the leaderboard_scene
	 * Pull out the leaderboard in the game stage joins the leaderboard reader/ writer thread
	 * @throws InterruptedException
	 */
	private void leaderboardToggle() throws InterruptedException
	{
		leaderboard_scene.moveIntoFrame(-370, 0);
		if (!leaderboard_reader.isAlive())
		{
			leaderboard_reader.join();
		}
		else
		{
			leaderboard_reader.start();
		}
	}
	/**
	 * builds that leaderboard button
	 */
	private void buildGameUILeaderboardButton()
	{
		leaderboardButton = new MathGameUIButton("Leaderboard");
		leaderboardButton.setOnAction(event -> {
			try 
			{
				leaderboardToggle();
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		});
	}
	/**
	 * builds that menu button for the game stage
	 */
	private void buildGameUIMenuButton()
	{
		menuButton = new MathGameUIButton("Menu");
		menuButton.setOnAction(event -> {
			try 
			{
				backToMainMenu();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		});
	}
	/**
	 * Helper function that performs clean up operations on the GameStage
	 * and shows the MenuStage
	 * @throws IOException
	 */
	private void backToMainMenu() throws IOException
	{
		gameBackgroundMusicPlayer.stop();
		storedMenu.show();
		gameStage.close();
		checkAndStoreHighScore();
	}
	
	/**
	 * Function to call when exiting game to check if the score
	 * should be written to the persistant leaderboard
	 * @throws IOException 
	 */
	private void checkAndStoreHighScore() throws IOException
	{
		Entry tmp = new Entry(name, gameTime, score_);
		
		try 
		{
			leaderboard_reader.write(tmp);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the live score board 
	 */
	private void updateScore()
	{
		score.setText(Integer.toString(score_));
	}
	/**
	 * Inital creation of the player object 
	 */
	private void showPlayerObj()
	{
		player_obj = new Player();
		player_obj.setLayoutX(GAME_WIDTH/ 2);
		player_obj.setLayoutY(GAME_HEIGHT - PLAYER_LOCATION_OFFSET);
		gamePane.getChildren().add(player_obj);
	}
	/**
	 * Temporary class meant to placehold for the controller of the 
	 * emeny spawns logical the same as spawnEnemy currently
	 * is called as a helper in the constructor
	 * 
	 * WIP
	 */
	private void createSpawner()
	{
		Enemies tmp = new Enemies();
		enemies.add(tmp);
		gamePane.getChildren().add(tmp);
		generate_problem();
	}
	/**
	 * Create the listeners attached to the gameScene
	 * MousePosition -> tracks position updates player_obj position
	 * MouseClick -> trigger fire fuction
	 * TODO KeyPressed -> tab key to pull open leaderboard
	 */
	private void createListeners()
	{
		
		gameScene.setOnMouseMoved(e -> player_obj.setPosX(e.getX()));
		//gameScene.setOnKeyPressed(e -> problem_board.setStyle());
		gameScene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {
				try 
				{
					int answer = Integer.valueOf(ammo_answer.getText());
					ammo_answer.setText("");
					fire(answer);					
				}
				catch (NumberFormatException e)
				{
					weaponFail.play();
					ammo_answer.setText("");
				}
			} 
		});
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				// TODO pop open leaderboard
				
			}
		});
	}
	/**
	 * Creates the projectile at the location of the player_obj 
	 * and sets its answer value
	 * @param answer Value that is passed to the projectile
	 */
	private void fire(int answer)
	{
		weaponSound.play();
		Projectile tmp = new Projectile(answer, player_obj.getLayoutY(), player_obj.getLayoutX());
		projectiles.add(tmp);
		gamePane.getChildren().add(tmp);
	}
	/**
	 * Function checks all the objects that could move on screen and then
	 * steps forward one step in the physics of each object
	 */
	private void aniamte()
	{
		if (movingLeft)
		{
			player_obj.move_left();
		}
		else if (movingRight)
		{
			player_obj.move_right();
		}

		if (!projectiles.isEmpty())
		{
			for (Projectile projectile : projectiles)
			{
				projectile.move();
			}
		}
		if (!enemies.isEmpty())
		{
			for (Enemies enemy : enemies)
			{
				enemy.move();
			}
		}
	}
	/**
	 * Checks if any enemy is colliding with any projectile
	 * if is it is updates the score, removes both objects, plays sound effect,
	 * and spawns new enemey
	 */
	private void checkCollisions()
	{
		for(int i = 0; i < projectiles.size(); i++)
		{
			for(int j = 0; j < enemies.size(); j++)
			{
				if (BULLET_RADIUS + ENEMY_RADIUS > distance_between(projectiles.get(i), enemies.get(j)))
				{
					if(projectiles.get(i).getAnswer() != answer)
					{
						problem_board.setStyle("-fx-text-fill: red;");
					}
					else if(projectiles.get(i).getAnswer() == answer)
					{
						problem_board.setStyle("-fx-text-fill: green;");
						gamePane.getChildren().remove(enemies.get(j));
						gamePane.getChildren().remove(projectiles.get(i));
						projectiles.remove(i);
						enemies.remove(j);							
						hitSoundClip.play();
						score_ += 1;
						spawnEnemy();
					}
				}
			}
		}
	}
	/**
	 * The main game loop
	 */
	private void createGameLoop()
	{
		gameTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				aniamte();
				checkCollisions();
				updateScore();
				if (gameTime == 0)
				{
					gameStart = now;
					gameTime = -1;
				}
				else if (gameTime != 0)
				{
					gameTime = now - gameStart;
				}
				
				time_played.setText(convertToRealTime(gameTime));
			}
		};
		gameTimer.start();
	}
	/**
	 * Helper function to display the time elapsed 
	 * @param time Takes in the current time
	 * @return The time in format of seconds only in a string
	 */
	private String convertToRealTime(long time)
	{ 	
		return Long.toString(time / (long)1000000000.0) + ":" + Long.toString(time % (long)1000000.0);
	}
	/**
	 * Helper function for the collision detection function
	 * @param projectile A projectile to check against
	 * @param enemy An enemy to check the projectile against
	 * @return the distance between the projectile and the enemy objects
	 */
	private double distance_between(Projectile projectile, Enemies enemy)
	{
		return Math.sqrt(Math.pow(projectile.get_x() - enemy.get_x(), 2) + Math.pow(projectile.get_y() - enemy.get_y(), 2));
	}
}
