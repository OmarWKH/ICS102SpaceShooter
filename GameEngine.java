import java.util.ArrayList;

public class GameEngine {
	private PlayerShip player;
	private ArrayList<GameObject> gameObjects;
	private GameWindow gameWindow;

	//engine is acting as a "tier"
	//forces gamewindow to be created first
	public GameEngine(GameWindow gameWindow) {
		this.gameObjects = new ArrayList<>();
		//this.setPlayer(player);
		this.gameWindow = gameWindow;
		this.notifyWindowOfObjects();
	}

	public GameEngine() {
		this(null);
	}

	/*
	public void setPlayer(PlayerShip player) {
		this.player = player;
		this.gameObjects.add(this.player);

		this.notifyWindowOfPlayer();
		this.notifyObjectOfWindow(this.player);
	}
	*/
	public PlayerShip getPlayer() {
		return this.player;
	}

	public void addGameObject(GameObject gameObject) {
		if (gameObject instanceof PlayerShip) {
			this.player = (PlayerShip)gameObject;
			this.notifyWindowOfPlayer();
		}
		this.gameObjects.add(gameObject);
		this.notifyObjectOfWindow(gameObject);
	}

	public ArrayList<GameObject> getGameObjects() {
		return this.gameObjects;
	}

	public GameWindow.GamePanel getGameWindow() {
		return this.gameWindow.getInGamePanel();
	}

	public void update() {
		for (GameObject gameObject : gameObjects) {
			gameObject.move();
		}
	}

	public void notifyWindowOfObjects() {
		this.gameWindow.setGameObjects(this.getGameObjects());
	}

	public void notifyWindowOfPlayer() {
		this.gameWindow.setPlayer(this.getPlayer());
	}

	public void notifyObjectOfWindow(GameObject gameObject) {
		gameObject.initializeLocationOn(this.getGameWindow());
	}
}
