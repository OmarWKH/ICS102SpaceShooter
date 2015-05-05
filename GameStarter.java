public class GameStarter {
	public static void main(String[] args) {
		GameEngine engine = new GameEngine();
		PlayerShip player = new PlayerShip();
		engine.setPlayer(player);
		Game gameWindow = new Game(player, engine.getGameObjects());

		while (true) {
			engine.update();
			gameWindow.repaint();
		}
	}
}