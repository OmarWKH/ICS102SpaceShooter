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
	
	public static void main(String[] args) {
		//concrete safe structure?
		gameWindow = new GameWindow();
		gameEngine = new GameEngine(gameWindow);
		//gameWindow.setVisible(true);
		//PlayerShip player = new PlayerShip();
		//engine.addGameObject(player);

		FollowerEnemyShip sh = new FollowerEnemyShip(gameEngine.getPlayer());
		gameEngine.addGameObject(sh);

		int interval = 17;
		//ActionListener gameLoop = (ActionEvent ae) -> { gameEngine.update(); gameWindow.repaint(); };
		ActionListener gameLoop = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameEngine.update();
				gameWindow.repaint();
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

	/*
	public static void playerHereIsYourShip() {
		PlayerShip player = new PlayerShip(gameWindow);

	}
	*/
}
