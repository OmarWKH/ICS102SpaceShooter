//could change to list
import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class GameEngine {
	//this is dangerous terretory
	//should the whole thing be static thing?
	//this is a side effect of unclear structure
	//still need notify() to control the order of things?
	//private static GameEngine current;
	private PlayerShip player;
	private ArrayList<AbstractGameObject> gameObjects;
	private ArrayList<AbstractGameObject> friendlyGameObjects;
	private ArrayList<AbstractGameObject> hostileGameObjects;
	private ArrayList<AbstractGameObject> deadGameObjects;
	private ArrayList<Shooter> shooters;
	public GameWindow gameWindow;
	//must insure gameWindow is created first

	//engine is acting as a "tier"
	//forces gamewindow to be created first
	public GameEngine(GameWindow gameWindow) {
		//GameEngine.current = this;
		this.gameWindow = gameWindow;
		this.gameObjects = new ArrayList<>(50);
		this.friendlyGameObjects = new ArrayList<>(25);
		this.hostileGameObjects = new ArrayList<>(25);
		this.deadGameObjects = new ArrayList<>(25);
		this.shooters = new ArrayList<>(25);
		player = new PlayerShip();
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
		} else if (gameObject instanceof Shooter) {
			this.shooters.add((Shooter)gameObject);
		}

		if (isFriendly(gameObject)) {
			this.friendlyGameObjects.add(gameObject);
		} else {
			this.hostileGameObjects.add(gameObject);
		}

		this.gameObjects.add(gameObject);
		this.notifyObjectOfWindow(gameObject);
		//System.out.println("Object adding: " + gameObject);
		System.out.println("Game Objects Count: " + gameObjects.size());
		System.out.println(gameObjects);
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
		//shoot em
		for (Shooter shooter : shooters) {
			shooter.shoot();
		}

		//move em
		for (AbstractGameObject gameObject : gameObjects) {
			gameObject.move();
		}

		//detect collosion
		this.whoIsTouchingWho();

		//dispose of em
		this.aSendOff();
	}

	private void whoIsTouchingWho() {
		/*
		for (int i = 0; i < gameObjects.size(); i++) {
			for (int j = i+1; j < gameObjects.size(); j++) {
				boolean isFriendly = !(gameObjects.get(i) instanceof AbstractEnemyShip) && 
			}
		}

		for (AbstractGameObject gameObject : gameObjects) {
			for (AbstractGameObject gameObject)
			boolean isFriendly = !()
		}
		*/

		for (AbstractGameObject friendly : friendlyGameObjects) {
			for (AbstractGameObject hostile : hostileGameObjects) {
				//System.out.println(areIntersecting(friendly, hostile));
				//bounds always at 0 0 so intersect
				//System.out.println(" F: " + friendly.getXPosition() + ".." + friendly.getYPosition() + ".. " + friendly.getImage().getData().getBounds());
				//System.out.println(" H: " + hostile.getXPosition() + ".." + hostile.getYPosition() + ".. " + hostile.getImage().getData().getBounds());
				if (areIntersecting(friendly, hostile)) {
					friendly.gotHit();
					hostile.gotHit();
				}
			}
		}
	}

	//check perfectness
	private static boolean areIntersecting(AbstractGameObject first, AbstractGameObject second) {
		Rectangle2D.Double firstRectangle = new Rectangle2D.Double(first.getXPosition(), first.getYPosition(), first.getWidth(), first.getWidth());
		Rectangle2D.Double secondRectangle = new Rectangle2D.Double(second.getXPosition(), second.getYPosition(), second.getWidth(), second.getWidth());
		return firstRectangle.intersects(secondRectangle);
	}

	private void aSendOff() {
		for (AbstractGameObject deadObject : deadGameObjects) {
			//could make it null
			//could remove from all lists
			//could make it one list and check in whoIsTouchingWho

			//pull out isFriendly, make who is watching easier


			this.gameObjects.remove(deadObject);
			if (isFriendly(deadObject)) {
				this.friendlyGameObjects.remove(deadObject);
			} else {
				this.hostileGameObjects.remove(deadObject);
			}

			if (deadObject instanceof Shooter) {
				this.shooters.remove(deadObject);
			}
			
			if (deadObject instanceof PlayerShip || (gameObjects.size() == 1 & gameObjects.get(0) instanceof PlayerShip)) {
				GameTier.stop();
				this.player = null;
				//this.gameWindow.getInGamePanel().freeze();
				this.gameWindow.getInGamePanel().stop();

				friendlyGameObjects.clear();
				hostileGameObjects.clear();
				shooters.clear();
			}
		}

		deadGameObjects.clear();
	}

	private static boolean isFriendly(AbstractGameObject gameObject) {
		boolean isFriendlyIfBullet = true;
		if (gameObject instanceof Bullet) {
			isFriendlyIfBullet = !( ((Bullet)gameObject).getShooter() instanceof AbstractEnemyShip );
		}
		return !(gameObject instanceof AbstractEnemyShip) && isFriendlyIfBullet;
	}

	public void manDown(AbstractGameObject deadObject) {
		this.deadGameObjects.add(deadObject);
	}

	public void notifyWindowOfObjects() {
		this.gameWindow.setGameObjects(this.getGameObjects());
	}

	public void notifyWindowOfPlayer() {
		this.gameWindow.setPlayer(this.getPlayer());
	}

	public void notifyObjectOfWindow(AbstractGameObject gameObject) {
		gameObject.initializeLocation();
		//System.out.println("Notify object of window: " + gameObject.getXPosition());
	}

	public static Point2D.Double getRandomLocation() {
		return new Point2D.Double(50, 50);
	}
}
