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
	
	
	public static void main(String[] args) {
		//concrete safe structure?

		imagesFolder = "images/";
		initializeGamePlayValues();
		
		gameWindow = new GameWindow();
		AbstractGameObject.panel = GameTier.gameWindow.getInGamePanel(); //norifyObjectOfWindow //gameEngine



		startGame();
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

	public static void initializeGamePlayValues() {
		numberOfEnemies = 5;
		
		GameWindow.backgroundLocation = GameTier.imagesFolder + "BackdropBlackLittleSparkBlack.png";
		Bullet.imageLocation = GameTier.imagesFolder + "Bullet.png";
		FollowerEnemyShip.imageLocation = GameTier.imagesFolder + "PlayerShip.png";
		PlayerShip.imageLocation = GameTier.imagesFolder + "PlayerShip.png";
		ShooterEnemyShip.imageLocation = GameTier.imagesFolder + "PlayerShip.png";

		Bullet.xVelocity = 5;
		Bullet.yVelocity = 5;
		FollowerEnemyShip.xVelocity = 0.5;
		FollowerEnemyShip.yVelocity = 0.5;
		ShooterEnemyShip.xVelocity = 1;
		ShooterEnemyShip.yVelocity = 1;
		
		PlayerShip.xAcceleration = 1;
		PlayerShip.yAcceleration = 1;
		PlayerShip.maxXVelocity = 4;
		PlayerShip.maxYVelocity = 4;
		
		Bullet.healthPoints = 1;
		FollowerEnemyShip.healthPoints = 1;
		PlayerShip.healthPoints = 3;
		ShooterEnemyShip.healthPoints = 1;

		PlayerShip.coolDownTime = 500;
		ShooterEnemyShip.coolDownTime = 2000;
	}

	public static void startGame() {
		gameEngine = new GameEngine(gameWindow, numberOfEnemies);
		int interval = 17;
		//ActionListener gameLoop = (ActionEvent ae) -> { gameEngine.update(); gameWindow.repaint(); };
		ActionListener gameLoop = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//long started = System.currentTimeMillis();
				gameEngine.update();
				gameWindow.repaint();
				//long ended = System.currentTimeMillis();
				//System.out.println(ended-started);
				int gameCondition = gameEngine.checkEndCondition();

				if (gameCondition != GameEngine.STILL_GOING) {
					gameEngine.cleanUp();
					if (gameCondition == GameEngine.WON) {
						//gameWindow.winState();
					} else {
						//gameWindow.lossState();
					}
					gameEngine = null;
					return;
				}

				//if()
					//clear (and before "setup")
			}
		};
		Timer timer = new Timer(interval, gameLoop);
		timer.setRepeats(true);
		timer.start();
	}

	/*
	public static void playerHereIsYourShip() {
		PlayerShip player = new PlayerShip(gameWindow);

	}
	*/
}
