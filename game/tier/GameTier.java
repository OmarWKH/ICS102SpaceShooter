package tier;

import engine.*;
import rendering.*;
import gameobjects.*;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Class that ties the game parts together (engine and window).
 * It functions to initlize gameplay values then sustain a gameloop until a condition is met.
 *
 * @author Omar Khashoggi
 */

public class GameTier {
	//ties to problems in engine, refer to notify
	public static GameWindow gameWindow;
	public static GameEngine gameEngine;
	public static int numberOfEnemies;
	public static String imagesFolder;
	public static boolean isRunning;
	public static int finalScore;
	
	public static void main(String[] args) {
		//concrete safe structure?

		imagesFolder = "images/";
		GameWindow.backgroundLocation = GameTier.imagesFolder + "Background.png";
		
		gameWindow = new GameWindow();
	}

	/**
	 * This method is used to start the game. It sets gameplay values and sustain the game loop asking the engine all the while about the game's condition. It initiates the clean up if the game is over.
	 *
	 * @param difficulty the difficulty of the game to be started
	 * @param mode the mode for the game to be started in
	 */
	public static void startGame(String difficulty, String mode) {
		GameTier.isRunning = true;

		switch (difficulty) {
			case "Normal": 	initializeGamePlayValues(5, 3, 1, 0.5, 500, 2000);
							break;
			case "Hard":	initializeGamePlayValues(5, 5, 5, 0.7, 500, 1000);
							break;
			default:		initializeGamePlayValues(5, 3, 1, 0.5, 500, 2000);
							break;
		}

		gameEngine = new GameEngine(gameWindow, numberOfEnemies, mode);

		int interval = 17;
		Timer timer = new Timer(interval, null);
		ActionListener gameLoop = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
					gameEngine.update();
					gameWindow.repaint();

					int gameCondition = gameEngine.checkEndCondition();
					if (gameCondition != GameEngine.STILL_GOING) {
						gameEngine.cleanUp();

						if (gameCondition == GameEngine.WON) {
							gameWindow.won();
						} else {
							gameWindow.lost();
						}

						gameEngine = null;
						timer.stop();
					}
			}
		};
		timer.addActionListener(gameLoop);
		timer.setRepeats(true);
		timer.start();
	}

	/**
	 * This method initlized gameplay values and sprite locations.
	 *
	 * @param numberOfEnemies the number of enemies in the game, if the mode is Endless it's the initial number
	 * @param playerHP the player's HP
	 * @param enemyHP enemy HP, applies to all enemies
	 * @param enemySpeed enemy speed, applied to all enemies
	 * @param playerCoolDown the time before player is allowed to shoot again
	 * @param enemyCoolDown the time before an enemy is allowed to shoot again
	 */
	public static void initializeGamePlayValues(int numberOfEnemies, int playerHP, int enemyHP, double enemySpeed, long playerCoolDown, long enemyCoolDown) {
		GameTier.numberOfEnemies = numberOfEnemies;

		Bullet.imageLocation = GameTier.imagesFolder + "Bullet.png";
		PlayerShip.imageLocation = GameTier.imagesFolder + "PlayerShip.png";
		ShooterEnemyShip.imageLocation = GameTier.imagesFolder + "EnemyShip.png";
		FollowerEnemyShip.imageLocation = GameTier.imagesFolder + "EnemyShip.png";

		PlayerShip.xAcceleration = 1;
		PlayerShip.yAcceleration = 1;
		PlayerShip.maxXVelocity = 4;
		PlayerShip.maxYVelocity = 4;

		Bullet.xVelocity = 5;
		Bullet.yVelocity = 5;
		FollowerEnemyShip.xVelocity = 0.5;
		FollowerEnemyShip.yVelocity = 0.5;
		
		PlayerShip.healthPoints = playerHP;
		Bullet.healthPoints = 1;
		ShooterEnemyShip.healthPoints = enemyHP;
		FollowerEnemyShip.healthPoints = enemyHP;

		PlayerShip.coolDownTime = playerCoolDown;
		ShooterEnemyShip.coolDownTime = enemyCoolDown;
	}
}
