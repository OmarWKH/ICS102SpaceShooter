package engine;

import tier.*;
import rendering.*;
import gameobjects.*;
import java.util.ArrayList;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

//java doc
//movement
//nullpointer
//freeze later in endless? Kill: 29, HP: 11
//rotation messes up sometimes

//could change to list
//orgnization: see notifyObjectsOfWindow, nullpointer
//sound? player cool down sound?


//undestand keystroke, actionmap, inputmap, key binding
//other settings, resulotion (font size too), decide all game play values with ui
// search // in all, I remember GameWindow and maybe others had some

////https://stackoverflow.com/questions/13735402/how-to-initialize-classes-dependent-on-each-other-in-java

/*
1. Movement is so tight it feels like I am pushing a rock 
2. Pressing in diagonal direction is impossible you move in the last pressed button direction
you want

//enemy looking different
//bullet speed too slow

Also when first wave of enemies is killed the other wave appears from no where!
Maybe create a one out of scene each time  a space ship dies and slowly push it into the scene
Also make wrapping from one direction only

I was thinking of adding power ups actually

https://unity3d.com/learn/tutorials/projects/space-shooter
Unity - Project: Space Shooter
unity3d.com
either continue it on Stencyl or Unity
*/

public class GameEngine {
	//this is a side effect of unclear structure
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

	public GameEngine(GameWindow gameWindow, int numberOfEnemies) {
		this(gameWindow, numberOfEnemies, LIMITED_MODE);
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
	}

	public void addBullet(Shooter shooter) {
		this.addGameObject(new Bullet(shooter));
	}

	public ArrayList<AbstractGameObject> getGameObjects() {
		return this.gameObjects;
	}

	public PlayerShip getPlayer() {
		return this.player;
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

	private static boolean areIntersecting(AbstractGameObject first, AbstractGameObject second) {
		Rectangle2D.Double firstRectangle = new Rectangle2D.Double(first.getXPosition(), first.getYPosition(), first.getWidth(), first.getWidth());
		Rectangle2D.Double secondRectangle = new Rectangle2D.Double(second.getXPosition(), second.getYPosition(), second.getWidth(), second.getWidth());
		return firstRectangle.intersects(secondRectangle);
	}

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

	private int endlessEndCondition() {
		if (this.player == null) {
			return LOST;
		} else if (hostileGameObjects.size() == 1) {
			spawnEnemies(numberOfEnemies + numberOfEnemies/3);
			int playerHP = player.getHealthPoints() + (new Double(0.3*this.killCount).intValue());
			player.setHealthPoints(playerHP);
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
		if (deadObject instanceof AbstractEnemyShip) {
			this.killCount++;
		}
	}

	public int getKillCount() {
		return this.killCount;
	}

	public void notifyWindowOfObjects() {
		this.gameWindow.setGameObjects(this.getGameObjects());
	}

	public void notifyWindowOfPlayer() {
		this.gameWindow.setPlayer(this.getPlayer());
	}

	//see here, we are taking it from tier, but we have it here
	public void notifyObjectsOfWindow() {
		AbstractGameObject.panel = GameTier.gameWindow.getInGamePanel();
	}

	public GameWindow.GamePanel getGameWindow() {
		return this.gameWindow.getInGamePanel();
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
		GameTier.finalScore = this.killCount;
	}
}
