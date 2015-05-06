import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameStarter {
	/*
	public GameEngine engine;
	public GameWindow window;
	public PlayerShip player;
	*/
	public static void main(String[] args) {
		//concrete safe structure?
		GameWindow gameWindow = new GameWindow();
		GameEngine engine = new GameEngine(gameWindow);
		PlayerShip player = new PlayerShip();
		engine.addGameObject(player);

		int interval = 17;
		ActionListener gameLoop = (ActionEvent ae) -> { engine.update(); gameWindow.repaint(); };
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
