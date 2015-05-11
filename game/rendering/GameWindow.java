package rendering;

import tier.*;
import engine.*;
import gameobjects.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.*;

/**
 * Class that handles the window, including the menus and the game scene.
 * It also handles input. I don't know how I feel about that.
 *
 * @author Omar Khashoggi
 */

public class GameWindow extends JFrame {
	private JPanel panel;
	private JPanel secondaryPanel;
	private GamePanel gamePanel;
	public static String backgroundLocation;
	private Image background;
	private CardLayout cards;
	private GridLayout listLayout = new GridLayout(0, 1);
	private MenusActions listener;

	private String[] gameModes = {"Limited", "Endless"}; //get dynamically
	private String[] difficulties = {"Normal", "Hard"}; //get dynamically
	private JComboBox<String> modeChanger;
	private JComboBox<String> difficultyChanger;
	//other settings, resulotion (font size too), decide all game play values with ui
	
	private JButton button;
	private JLabel label;
	private JTextArea text;
	private int finalScore;
	/**
	 * This constructor specifies the frame's values and adds all the panels in a cardlayout.
	 *
	 */
	public GameWindow() {
		this.setSize(960, 720);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.cards = new CardLayout();
		this.setLayout(cards);
		this.listener = new MenusActions();
		this.setBackgroundLocation(backgroundLocation);

		//main menu
		panel = new JPanel(listLayout);
		secondaryPanel = new JPanel(new FlowLayout());
		modeChanger = new JComboBox<>(gameModes);
		secondaryPanel.add(modeChanger);
		difficultyChanger = new JComboBox<>(difficulties);
		secondaryPanel.add(difficultyChanger);
		button = new JButton("Start");
		secondaryPanel.add(asProperButton(button));
		secondaryPanel.setOpaque(false);
		panel.add(secondaryPanel);
		button = new JButton("Help");
		panel.add(asProperButton(button));
		button = new JButton("Exit");
		panel.add(asProperButton(button));
		this.add(asLayeredPane(panel), "Main");
		
		//help
		panel = new JPanel(listLayout);
		button = new JButton("Menu");
		panel.add(asProperButton(button));
		text = new JTextArea("Move with WASD or Arrows.\nShoot with LMB or Space.\nExit level with Escape.\nHard increases player and enemy HP + enemy speed and decreases enemy cooldown\nEndless introduces waves with increasing numbers and increasing HP depending on kill count."); //\nPause/Unpause with P.");
		text.setLineWrap(true);
		panel.add(asProper(text));
		this.add(asLayeredPane(panel), "Help");

		//lost menu
		panel = new JPanel(listLayout);
		button = new JButton("Menu");
		panel.add(asProperButton(button));
		label = new JLabel("You lost");
		panel.add(asProper(label));
		this.add(asLayeredPane(panel), "Lost");

		//won menu
		panel = new JPanel(listLayout);
		button = new JButton("Menu");
		panel.add(asProperButton(button));
		label = new JLabel("You won");
		panel.add(asProper(label));
		this.add(asLayeredPane(panel), "Won");

		this.setVisible(true);
	}

	/**
	 * This method styles a JComponent in the desired way. Makes it transparent and makes the font bold, size 20 and white.
	 *
	 * @param component the JComponent to be changed
	 * @return returns a JComponent with the desired changed made so it fits the UI feel
	 */
	private JComponent asProper(JComponent component) {
		component.setFont(new Font(component.getFont().getFontName(), Font.BOLD, 20));
		component.setForeground(Color.WHITE);
		component.setOpaque(false);
		return component;
	}

	/**
	 * This method styles a JButton in the desired way. Makes it transparent and makes the font bold, size 40 and white.
	 * The method uses {@link #asProper(JComponent)}.
	 *
	 * @param component the JButton to be changed
	 * @return returns a JButton with the desired changed made so it fits the UI feel
	 */
	private JButton asProperButton(JButton button) {
		button = (JButton)asProper(button);
		button.setFont(new Font(button.getFont().getFontName(), Font.BOLD, 40));
		button.addActionListener(listener);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		return button;
	}

	/**
	 * This method adds a background to a panel and makes it transparent.
	 * It uses {@link #newBackgroundPanel()} to get a background panel.
	 *
	 * @param panel the panel to be changed
	 * @return returns a JLayeredPane that includes the panel and a background panel
	 */
	private JLayeredPane asLayeredPane(JPanel panel) {
		panel.setOpaque(false);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, this.getWidth(), this.getHeight());
		panel.setBounds(0, 0, this.getWidth(), this.getHeight());
		layeredPane.add(panel, new Integer(1));
		layeredPane.add(newBackgroundPanel(), new Integer(-1));
		return layeredPane;
	}

	/**
	 * This method creates a background panel with the background image.
	 * It's used by {@link #asLayeredPane(panel)}.
	 *
	 * @return return a JButton with the desired changed made so it fits the UI feel
	 */
	private JPanel newBackgroundPanel() {
		JPanel backgroundPanel = new JPanel(listLayout);
		ImageIcon backgroundActually = new ImageIcon(backgroundLocation); 
		JLabel theThingWeDoToMakeLifeSimple = new JLabel();
		theThingWeDoToMakeLifeSimple.setIcon(backgroundActually);
		backgroundPanel.add(theThingWeDoToMakeLifeSimple);
		backgroundPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		return backgroundPanel;
	}

	/**
	 * This inner class handles button actions in the window.
	 *
	 */
	class MenusActions extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent ae) {
			JButton button;
			try {
				button = (JButton)ae.getSource();
				if (button.getText().equals("Start")) {
					startGame();
				} else if(button.getText().equals("Help")) {
					cards.show(getContentPane(), "Help");
				} else if (button.getText().equals("Menu")) {
					cards.show(getContentPane(), "Main");
				} else if (button.getText().equals("Exit")) {
					System.exit(0);
				}
			} catch (ClassCastException cce) {
				cce.printStackTrace();
				System.exit(1);
			}
		}
	}

	/**
	 * This method starts the game depending on the values set by the user.
	 * It passes the values to {@link tier.GameTier#startGame(String, String)} in the tier.
	 *
	 */
	private void startGame() {
		gamePanel = new GamePanel();
		add(gamePanel, "Game");
		cards.show(getContentPane(), "Game");
		gamePanel.requestFocusInWindow();
		String difficulty = difficultyChanger.getItemAt(difficultyChanger.getSelectedIndex());
		String mode = modeChanger.getItemAt(modeChanger.getSelectedIndex());
		GameTier.startGame(difficulty, mode);
	}

	//merge
	/**
	 * This method is run after a win. It shows the win panel and cleans up the previous game.
	 *
	 */
	public void won() {
		this.gamePanel.cleanUp();
		//show score
		cards.show(this.getContentPane(), "Won");
		this.gamePanel = null;
	}
	/**
	 * This method is run after a loss. It shows the win panel and cleans up the previous game.
	 *
	 */
	public void lost() {
		this.gamePanel.cleanUp();
		//show score
		cards.show(this.getContentPane(), "Lost");
		this.gamePanel = null;
	}

	/**
	 * This method is used to get the actual game panel.
	 *
	 * @return returns a GamePanel. Could return null if not game is running.
	 */
	public GamePanel getInGamePanel() {
		return this.gamePanel;
	}

	/**
	 * This method passes the game objects list to the game panel.
	 *
	 * @param gameObjects a reference to a list of game objects
	 */
	public void setGameObjects(ArrayList<AbstractGameObject> gameObjects) {
		this.gamePanel.setGameObjects(gameObjects);
	}

	/**
	 * This method passes the player to the game panel.
	 * 
	 * @param player the player
	 */
	public void setPlayer(PlayerShip player) {
		this.gamePanel.setPlayer(player);
	}

	/**
	 * This method sets the background given a location.
	 * It throws an exception if the image failed to load.
	 *
	 * @param path the path to the background image location
	 */
	public void setBackgroundLocation(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			background = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
		} catch (IOException ioe) {
			System.out.println("Failed to load image at: " + backgroundLocation);
			ioe.printStackTrace();
			System.exit(0);
		}
	}


	/**
	 * This inner class is the actual game panel. It represesnt the game scene and should only exist when the game is running
	 *
	 */
	public class GamePanel extends JPanel {
		private PlayerShip player;
		private ArrayList<AbstractGameObject> gameObjects;
		private int hpXLocation;
		private int hpYLocation;
		private int scoreXLocation;
		private int socreYLocation;
		
		//seperate controls
		//seperate this?
		/**
		 * This constructor initlizes GamePanel and adds listeners
		 *
		 */
		public GamePanel() {
			this.addKeyListener(new KeyboardInGameControls());
			this.addMouseListener(new MouseInGameControls());
			gameObjects = new ArrayList<AbstractGameObject>(); //dummy for paint, tied to structure problem

			/*
			this.getInputMap().put(KeyStroke.getKeyStroke("W"), "Move Up");
			this.getActionMap().put("Move Up", this.moveUp);

			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Move Up");
			this.getActionMap().put("Move Up", this.moveUp);

			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Exit");
			this.getActionMap().put("Exit", this.exit);
			*/

			this.setFocusable(true);
			this.requestFocusInWindow();
		}

		/**
		 * This method overrides paint(g) to paint the background, HP, score, and game objects.
		 * It's never called directly. Only with repaint()
		 *
		 * @param g a graphics object
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D)g;
			
			g2d.drawImage(background, null, null);
			
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font(g2d.getFont().getFontName(), Font.BOLD, 14));
			g2d.drawString("HP: " + player.getHealthPoints(), hpXLocation, hpYLocation);
			g2d.drawString("Killed: " + GameTier.gameEngine.getKillCount(), scoreXLocation, socreYLocation);

			for (AbstractGameObject gameObject : gameObjects) {
				try {
					gameObject.draw(g2d);
				} catch (NullPointerException npe) { //proper exception
					System.out.println("Object failed painting. We have: " + gameObjects);
					npe.printStackTrace();
					System.exit(0);
				}
			}
		}

		/**
		 * This method sets the player and calculates the appropriate location for HP and score to be painted.
		 *
		 * @param player the player
		 */
		public void setPlayer(PlayerShip player) {
			this.player = player;
			this.hpXLocation = new Double(this.getWidth() * 0.1).intValue();
			this.hpYLocation = new Double(this.getHeight() * 0.1).intValue();
			this.scoreXLocation = new Double(this.getWidth() - this.getWidth() * 0.1).intValue();
			this.socreYLocation = this.hpYLocation;
		}

		/**
		 * This method sets the game objects.
		 * 
		 * @param gameObjects a list of game objects
		 */
		public void setGameObjects(ArrayList<AbstractGameObject> gameObjects) {
			this.gameObjects = gameObjects;
		}

		/**
		 * This method cleans up after a game is done. It sets player to null, changes the view to the menu, and records the final score.
		 *
		 */
		public void cleanUp() {
			this.player = null;
			cards.show(getContentPane(), "Main");
			finalScore = GameTier.finalScore;
		}
		
		/**
		 * This inner class handles the in-game keybord controls.
		 *
		 */
		class KeyboardInGameControls extends KeyAdapter {
			/**
			 * This method override keyPressed(). When it's fired with the right keys it could indicates that player should start moving or firing or the game should stop.
			 * 
			 * @param keyEvent the key event
			 */
			@Override
			public void keyPressed(KeyEvent keyEvent) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
					lost();
				} /*else if (keyEvent.getKeyCode() == KeyEvent.VK_P) {
					GameTier.isRunning = !GameTier.isRunning;
				}*/ else if (GameTier.isRunning) {
					if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
						player.shoot();
					} else {
						player.movementKeyPressed(keyEvent);
					}
				} 
			}

			/**
			 * This method override keyReleased(). When it's fired it could indicate that player should stop and so informs player.
			 * 
			 * @param keyEvent the key event
			 */
			@Override
			public void keyReleased(KeyEvent keyEvent) {
				if (GameTier.isRunning) {
					player.movementKeyReleased(keyEvent);
				}
			}
		}

		/**
		 * This class handles in-game mouse controls.
		 *
		 */
		class MouseInGameControls extends MouseAdapter {
			/**
			 * This method overrides mousePressed(). When it's fired it indicated that player should shoot and so informs player.
			 * 
			 * @param me the mouse event
			 */
			@Override
			public void mousePressed(MouseEvent me) {
				if (GameTier.isRunning) {
					player.shoot();
				}
			}
		}
	}
}
