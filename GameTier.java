import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameTier {
	public static GameEngine gameEngine;
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

		int interval = 17;
		ActionListener gameLoop = (ActionEvent ae) -> { gameEngine.update(); gameWindow.repaint(); };
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
