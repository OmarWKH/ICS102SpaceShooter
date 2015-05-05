import java.util.ArrayList;

public class GameEngine {
	private PlayerShip player;
	private ArrayList<PlayerShip> gameObjects;

	public GameEngine() {
		this.gameObjects = new ArrayList<>();
	}

	public void setPlayer(PlayerShip player) {
		this.player = player;
		this.gameObjects.add(this.player);
	}

	public void update() {
		this.player.move();
	}

	public ArrayList<PlayerShip> getGameObjects() {
		return this.gameObjects;
	}
}