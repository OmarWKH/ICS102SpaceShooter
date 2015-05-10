//could change to list
import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

//nullpointer
//movement
//circles
//enemy sprite
//java doc
//comments, orgnization
//score, health

//endless: health increase partially with score
//score: number of kills

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
	//doesn't seem right, static not static, consistency
	public GameWindow gameWindow; //I opnly need the panel, will I do anything defined in gamePanel or only need width?
	private RandomLocation randomLocations;
	private int numberOfEnemies;
	public String gameMode;
	//must insure gameWindow is created first

	public final static String LIMITED_MODE = "Limited";
	public final static String ENDLESS_MODE = "Endless";
	//public final static INFINITE = 1;

	public final static int WON = 1;
	public final static int STILL_GOING = 0;
	public final static int LOST = -1;

	//engine is acting as a "tier"
	//forces gamewindow to be created first
	public GameEngine(GameWindow gameWindow, int numberOfEnemies) {
		this(gameWindow, numberOfEnemies, LIMITED_MODE);
	}

	public GameEngine(GameWindow gameWindow, int numberOfEnemies, String gameMode) {
		//infinite mode:
		  //spawn whenever {}
		  //player gains health if he kills, loses when health is zero

		//GameEngine.current = this;
		this.gameWindow = gameWindow;
		this.numberOfEnemies = numberOfEnemies;
		this.gameMode = gameMode;

		//enemies*(arbitrary bullets) + player(itself+arbitrary bullets);
		int estimatedNumberOfObjects = this.numberOfEnemies*5 + 1*(10+1);
		this.gameObjects = new ArrayList<>(estimatedNumberOfObjects);
		this.friendlyGameObjects = new ArrayList<>(estimatedNumberOfObjects/2);
		this.hostileGameObjects = new ArrayList<>(estimatedNumberOfObjects/2);
		this.deadGameObjects = new ArrayList<>(estimatedNumberOfObjects);
		//half the enemies and a player
		int estimatedNumberOfShooters = this.numberOfEnemies/2 + 1;
		this.shooters = new ArrayList<>(estimatedNumberOfShooters);
		player = new PlayerShip();
		//this.setPlayer(player);
		this.notifyWindowOfObjects();
		this.notifyObjectsOfWindow();

		this.addGameObject(player);

		this.randomLocations = new RandomLocation(gameWindow.getInGamePanel().getWidth(), gameWindow.getInGamePanel().getHeight(), this.numberOfEnemies);
		
		this.spawnEnemies(this.numberOfEnemies);
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
		gameObject.initializeLocation();
		//System.out.println("Object adding: " + gameObject);
		//System.out.println("Game Objects Count: " + gameObjects.size());
		//System.out.println(gameObjects);
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

	private void spawnEnemies(int numberOfSpawns) {
		Random coin = new Random();
		for (int i = 0; i < numberOfEnemies; i++) {
			int enemyType = coin.nextInt(2);
			Point2D.Double location = this.getRandomLocation();
			if (enemyType == 0) { //static enemy type, proper name
				addGameObject(new FollowerEnemyShip(player, location));
			} else if (enemyType == 1) {
				addGameObject(new ShooterEnemyShip(player, location));
			}
		}
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
			
			if (deadObject instanceof PlayerShip) {
				this.player = null;
			}
		}

		deadGameObjects.clear();
	}

	/**
	 * This method checks the state of the win/lose conditions
	 * 
	 * @return returns -1 if the player lost, 0 if the game is still going, and 1 if the player won
	 */
	public int checkEndCondition() {
		switch (this.gameMode) {
			case LIMITED_MODE:	return limitedEndCondition();
			case ENDLESS_MODE:	return endlessEndCondition();
			default:			return 0;
		}
	}

	private int limitedEndCondition() {
		if (this.player == null) {
			return LOST;
		} else if (gameObjects.size() == 1 & gameObjects.get(0) instanceof PlayerShip) {
			//since player can create objects (bullets), if player is not dead and he keeps fireing, size will not be one
			//solution: tie stop to another condition as well
			return WON;
		} else {
			return STILL_GOING;
		}
	}

	private int endlessEndCondition() {
		if (this.player == null) {
			return LOST;
		} else if (hostileGameObjects.size() == 1) {
			spawnEnemies(numberOfEnemies + numberOfEnemies/3);
		}
		return STILL_GOING;
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

	public void notifyObjectsOfWindow() {
		AbstractGameObject.panel = GameTier.gameWindow.getInGamePanel(); //norifyObjectOfWindow //gameEngine
		//System.out.println("Notify object of window: " + gameObject.getXPosition());
	}

	private Point2D.Double getRandomLocation() {
		return randomLocations.next();
	}

	public void cleanUp() {
		this.player = null;
		gameObjects.clear();
		friendlyGameObjects.clear();
		hostileGameObjects.clear();
		shooters.clear();
	}
}
