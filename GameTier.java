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
	public static GameEngine gameEngine;
	//if gameWindow is static, using it?
	public static GameWindow gameWindow;
	public static String imagesFolder = "images/";
	//public static PlayerShip player;
	public static boolean isRunning;
	public static int numberOfEnemies = 5;
	
	public static void main(String[] args) {
		//concrete safe structure?
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

		gameWindow = new GameWindow();
		AbstractGameObject.panel = GameTier.gameWindow.getInGamePanel(); //norifyObjectOfWindow //gameEngine
		gameEngine = new GameEngine(gameWindow, numberOfEnemies);


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
		int interval = 17;
		//ActionListener gameLoop = (ActionEvent ae) -> { gameEngine.update(); gameWindow.repaint(); };
		ActionListener gameLoop = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isRunning) {
					long started = System.currentTimeMillis();
					gameEngine.update();
					gameWindow.repaint();
					long ended = System.currentTimeMillis();
					//System.out.println(ended-started);
				}
			}
		};
		Timer timer = new Timer(interval, gameLoop);
		timer.setRepeats(true);
		timer.start();
/*
		while (true) {
			engine.update();
			gameWindow.repaint();
		}
*/
	}

	public static void stop() {
		isRunning = false;
	}

	public static boolean isRunning() {
		return GameTier.isRunning;
	} 

	/*
	public static void playerHereIsYourShip() {
		PlayerShip player = new PlayerShip(gameWindow);

	}
	*/
}
