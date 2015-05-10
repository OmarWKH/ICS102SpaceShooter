import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/*
1. Movement is so tight it feels like I am pushing a rock 
2. If you put the mouse closer to the ship bullets are shooten so slow
3. Pressing in diagonal direction is impossible you move in the last pressed button direction
4. It's impossible to shoot with mouse click when mouse is moving
*/

public class GameTier {
	public static GameWindow gameWindow;
	public static GameEngine gameEngine;
	//if gameWindow is static, using it?
	//public static PlayerShip player;
	public static int numberOfEnemies;
	public static String imagesFolder;
	public static boolean isRunning;
	
	public static void main(String[] args) {
		//concrete safe structure?

		imagesFolder = "images/";
		GameWindow.backgroundLocation = GameTier.imagesFolder + "Background.png";
		
		gameWindow = new GameWindow();

		/*
		//stariting operations

		//variables

		//stopping operations

		//force doing 'em

		*/

		//gameWindow.setVisible(true);
		//PlayerShip player = new PlayerShip();
		//engine.addGameObject(player);

		//GameTier.isRunning = true;
		

/*
		while (true) {
			engine.update();
			gameWindow.repaint();
		}
*/
	}

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

		//menu

		gameEngine = new GameEngine(gameWindow, numberOfEnemies, mode);

		int interval = 17;
		//ActionListener gameLoop = (ActionEvent ae) -> { gameEngine.update(); gameWindow.repaint(); };
		ActionListener gameLoop = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//if (true) {
					//long started = System.currentTimeMillis();
					gameEngine.update();
					gameWindow.repaint();
					//long ended = System.currentTimeMillis();
					//System.out.println(ended-started);
					int gameCondition = gameEngine.checkEndCondition();

					if (gameCondition != GameEngine.STILL_GOING) {
						gameEngine.cleanUp();
						if (gameCondition == GameEngine.WON) {
							gameWindow.won();
						} else {
							gameWindow.lost();
						}
						gameEngine = null;
						//GameTier.isRunning = false;
						//return;
					}

					//if()
						//clear (and before "setup")
				//}
			}
		};
		Timer timer = new Timer(interval, gameLoop);
		timer.setRepeats(true);
		timer.start();
	}

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

	/*
	public static void playerHereIsYourShip() {
		PlayerShip player = new PlayerShip(gameWindow);

	}
	*/
}
