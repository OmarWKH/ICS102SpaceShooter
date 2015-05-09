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
	
	public static void main(String[] args) {
		//concrete safe structure?
		gameWindow = new GameWindow();
		gameEngine = new GameEngine(gameWindow);

		/*
		AbstractGameObject.panel = GameTier.gameWindow.getInGamePanel();
		FollowerEnemyShip.healthPoints = 1;
		FollowerEnemyShip.imageLocation = GameTier.imagesFolder + "PlayerShip.png";
		FollowerEnemyShip.xVelocity = 0.1;
		FollowerEnemyShip.yVelocity = 0.1;
		Bullet.xVelocity = 5;
		Bullet.yVelocity = 5;
		Bullet.imageLocation = GameTier.imagesFolder + "Bullet.png";
		//if all of them need to set it, abstractr method or force constructor
		Bullet.healthPoints = 1;
		PlayerShip.imageLocation = GameTier.imagesFolder + "PlayerShip.png";
		PlayerShip.healthPoints = 3;
		PlayerShip.coolDownTime = 500;
		ShooterEnemyShip.healthPoints = 1;
		ShooterEnemyShip.imageLocation = GameTier.imagesFolder + "PlayerShip.png";
		ShooterEnemyShip.xVelocity = 1;
		ShooterEnemyShip.yVelocity = 1;
		ShooterEnemyShip.coolDownTime = 500;
		ShooterEnemyShip.lastShotTime;
		*/

		//gameWindow.setVisible(true);
		//PlayerShip player = new PlayerShip();
		//engine.addGameObject(player);

		FollowerEnemyShip sh = new FollowerEnemyShip(gameEngine.getPlayer());
		ShooterEnemyShip es = new ShooterEnemyShip(gameEngine.getPlayer());
		gameEngine.addGameObject(sh);
		gameEngine.addGameObject(es);

		isRunning = true;
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
