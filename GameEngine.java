import java.util.ArrayList;

public class GameEngine {
	//this is dangerous terretory
	//should the whole thing be static thing?
	//this is a side effect of unclear structure
	//still need notify() to control the order of things?
	//private static GameEngine current;
	private PlayerShip player;
	private ArrayList<AbstractGameObject> gameObjects;
	private GameWindow gameWindow;

	//engine is acting as a "tier"
	//forces gamewindow to be created first
	public GameEngine(GameWindow gameWindow) {
		//GameEngine.current = this;
		this.gameWindow = gameWindow;
		this.gameObjects = new ArrayList<>();
		player = new PlayerShip();
		/**
		Trying to get Player access to addBullet()
		**/
		this.addGameObject(player);
		//this.setPlayer(player);
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

	public void addGameObject(AbstractGameObject gameObject) {
		if (gameObject instanceof PlayerShip) {
			this.player = (PlayerShip)gameObject;
			this.notifyWindowOfPlayer();
		}
		this.gameObjects.add(gameObject);
		this.notifyObjectOfWindow(gameObject);
		//System.out.println("Object adding: " + gameObject);
		System.out.println(gameObjects.size());
	}

	public ArrayList<AbstractGameObject> getGameObjects() {
		return this.gameObjects;
	}

	public GameWindow.GamePanel getGameWindow() {
		return this.gameWindow.getInGamePanel();
	}

	public void addBullet(Shooter shooter) {
		this.addGameObject(new Bullet(shooter));
	}

	public void update() {
		for (AbstractGameObject gameObject : gameObjects) {
			gameObject.move();
		}
	}

	public void notifyWindowOfObjects() {
		this.gameWindow.setGameObjects(this.getGameObjects());
	}

	public void notifyWindowOfPlayer() {
		this.gameWindow.setPlayer(this.getPlayer());
	}

	public void notifyObjectOfWindow(AbstractGameObject gameObject) {
		gameObject.initializeLocationOn(this.getGameWindow());
		//System.out.println("Notify object of window: " + gameObject);
	}
}
