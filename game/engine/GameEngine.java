package engine;

import tier.*;
import rendering.*;
import gameobjects.*;
import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * This class acts as the engine of the game. It handles storing, creating, and removing objects, collision, and organized shooting and moving.
 * 
 * @author Omar Khashoggi (based on Moayad Alnammi's)
 */

public class GameEngine {
	private PlayerShip player;
	private ArrayList<AbstractGameObject> gameObjects;
	private ArrayList<AbstractGameObject> friendlyGameObjects;
	private ArrayList<AbstractGameObject> hostileGameObjects;
	private ArrayList<AbstractGameObject> deadGameObjects;
	private ArrayList<Shooter> shooters;
	public GameWindow gameWindow;
	private RandomLocation randomLocations;
	private int numberOfEnemies;
	public String gameMode;
	private int killCount;

	public final static String LIMITED_MODE = "Limited";
	public final static String ENDLESS_MODE = "Endless";
	
	public final static int WON = 1;
	public final static int STILL_GOING = 0;
	public final static int LOST = -1;

	/**
	 * This constructor creates a game in the given window with the given number of enemies and in the specified game mode
	 * It creates ArrayLists with initial estimations of size and creates a player and spawn enemies.
	 * It also notifies the window of the game objects list and vice versa.
	 * 
	 * @param gameWindow the window containing the panel the game is drawn on
	 * @param numberOfEnemies the number of enemies or intial enemies
	 * @param gameMode the game mode the game is played in
	 */
	public GameEngine(GameWindow gameWindow, int numberOfEnemies, String gameMode) {
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
		
		this.notifyWindowOfObjects();
		this.notifyObjectsOfWindow();

		this.addGameObject(player);

		this.randomLocations = new RandomLocation(gameWindow.getInGamePanel().getWidth(), gameWindow.getInGamePanel().getHeight(), this.numberOfEnemies);
		
		this.spawnEnemies(this.numberOfEnemies);
	}

	/**
	 * This constructor creates a game in the given window with the given number of enemies and in the default game mode Limited.
	 * It does the same as the other constructor
	 * 
	 * @param gameWindow the window containing the panel the game is drawn on
	 * @param numberOfEnemies the number of enemies or intial enemies
	 */
	public GameEngine(GameWindow gameWindow, int numberOfEnemies) {
		this(gameWindow, numberOfEnemies, LIMITED_MODE);
	}

	/**
	 * This method adds a game object to the appropriate list.
	 *
	 * @param gameObject the game object
	 */
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
	}

	/**
	 * This method creates a bullet with a given shooter and then adds it to the game objects.
	 *
	 * @param shooter the shooter of the bullet
	 */
	public void addBullet(Shooter shooter) {
		this.addGameObject(new Bullet(shooter));
	}

	/**
	 * This method returns the game objects list.
	 *
	 * @return returns game objects list
	 */
	public ArrayList<AbstractGameObject> getGameObjects() {
		return this.gameObjects;
	}

	/**
	 * This method return the palyer.
	 *
	 * @return returns the player
	 */
	public PlayerShip getPlayer() {
		return this.player;
	}

	/**
	 * This method is reponsible for spawning te number of given enemies.
	 * It randomized the type of spawned enemies and gives them random uniuqe locations using RandomLocation.
	 *
	 * @param numberOfSpawns the number of enemies to spawn
	 */
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

	/**
	 * This method updates the game. It lets te shooters shoot. The movers move. Checks collisions. And removes the dead objects.
	 *
	 */
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

	/**
	 * This method checks collision between friendly and hostile objects. And if it detects collision it informs the object.
	 *
	 */
	private void whoIsTouchingWho() {
		//no random access? a list seems better, let's check the rest first
		for (AbstractGameObject friendly : friendlyGameObjects) {
			for (AbstractGameObject hostile : hostileGameObjects) {
				if (areIntersecting(friendly, hostile)) {
					friendly.gotHit();
					hostile.gotHit();
				}
			}
		}
	}

	/**
	 * This method is how collision is detected. It takes two objects and creates a surrounding rectangles, then checks if they intersect.
	 *
	 * @return returns true if the objects intersect and false otherwise.
	 */
	private static boolean areIntersecting(AbstractGameObject first, AbstractGameObject second) {
		Rectangle2D.Double firstRectangle = new Rectangle2D.Double(first.getXPosition(), first.getYPosition(), first.getWidth(), first.getWidth());
		Rectangle2D.Double secondRectangle = new Rectangle2D.Double(second.getXPosition(), second.getYPosition(), second.getWidth(), second.getWidth());
		return firstRectangle.intersects(secondRectangle);
	}

	/**
	 * This method is used to remove objects in deadGameObjects list. It removes all objects from the appropriate places to allow them to be actually discarded off.
	 *
	 */
	private void aSendOff() {
		//also no random access
		for (AbstractGameObject deadObject : deadGameObjects) {
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
	 * This method checks the state of the win/lose conditions for the current mode
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

	/**
	 * This method checks the condition for the limited mode.
	 *
	 * @return returns -1 if the player is dead, 1 if the player is the only remaining object, and 0 otherwise.
	 */
	private int limitedEndCondition() {
		//here is a random access, but it's rare
		if (this.player == null) {
			return LOST;
		} else if (gameObjects.size() == 1 && gameObjects.get(0) instanceof PlayerShip) {
			//since player can create objects (bullets), if player is not dead and he keeps fireing, size will not be one
			return WON;
		} else {
			return STILL_GOING;
		}
	}

	/**
	 * This method checks the condition for the endless mode. And makes sure it goes on if player is alive.
	 *
	 * @return returns -1 if the player is dead. If not, and if only one enemy/enemy bullet remains, it adds more enemies than were added last time and increases player's HP by an amount dependant on how many player killed. It then returns 0.
	 */
	private int endlessEndCondition() {
		if (this.player == null) {
			return LOST;
		} else if (hostileGameObjects.size() <= 1) {
			spawnEnemies(numberOfEnemies + numberOfEnemies/3);
			int playerHP = player.getHealthPoints() + (new Double(0.3*this.killCount).intValue());
			player.setHealthPoints(playerHP);
		}
		return STILL_GOING;
	}

	/**
	 * This method checks whether an object is friendly or not.
	 *
	 * @param gameObject the object to be tested
	 */
	private static boolean isFriendly(AbstractGameObject gameObject) {
		boolean isFriendlyIfBullet = true;
		if (gameObject instanceof Bullet) {
			isFriendlyIfBullet = !( ((Bullet)gameObject).getShooter() instanceof AbstractEnemyShip );
		}
		return !(gameObject instanceof AbstractEnemyShip) && isFriendlyIfBullet;
	}

	/**
	 * The method is called by an object if it's dead. It adds it to the dead objects list. If the object is an enemy ship it increases the kill count.
	 *
	 * @param deadObject the dead object
	 */
	public void manDown(AbstractGameObject deadObject) {
		this.deadGameObjects.add(deadObject);
		if (deadObject instanceof AbstractEnemyShip) {
			this.killCount++;
		}
	}

	/**
	 * This method returns the kill count.
	 *
	 * @return returns the number of enemy ships killed
	 */
	public int getKillCount() {
		return this.killCount;
	}

	/**
	 * This method passes the game objects to the window.
	 *
	 */
	public void notifyWindowOfObjects() {
		this.gameWindow.setGameObjects(this.getGameObjects());
	}

	/**
	 * This method passes the player to the window.
	 *
	 */
	public void notifyWindowOfPlayer() {
		this.gameWindow.setPlayer(this.getPlayer());
	}

	//see here, we are taking it from tier, but we have it here
	/**
	 * This method passes the game window (actually game panel) to a static reference in AbstractGameObject to be used by all game objects.
	 *
	 */
	public void notifyObjectsOfWindow() {
		AbstractGameObject.panel = GameTier.gameWindow.getInGamePanel();
	}

	/**
	 * This method returns the actual game panel. We pretend to deal with game window for simplicity.
	 *
	 * @return returns the actual game panel.
	 */
	public GameWindow.GamePanel getGameWindow() {
		return this.gameWindow.getInGamePanel();
	}

	/**
	 * This method gets and returns a random location from RandomLocation.
	 *
	 * @return returns a point defining a random unique location.
	 */
	private Point2D.Double getRandomLocation() {
		return randomLocations.next();
	}

	/**
	 * This method is called when game is over. It removes and clears all lists. And passes the final score to GameTier
	 *
	 */
	public void cleanUp() {
		this.player = null;
		gameObjects.clear();
		friendlyGameObjects.clear();
		hostileGameObjects.clear();
		shooters.clear();
		GameTier.finalScore = this.killCount;
	}
}
