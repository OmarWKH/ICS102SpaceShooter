import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
//import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
//import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
//import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
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
//import javax.swing.Action;
//import javax.swing.AbstractAction;
//import java.awt.event.ActionEvent;
//undestand keystroke, actionmap, inputmap, key binding
//import javax.swing.KeyStroke;

//all
  //extract variables
  //check exception prone

public class GameWindow extends JFrame {
	private JPanel panel;
	private JPanel secondaryPanel;
	private GamePanel gamePanel;
	public static String backgroundLocation;
	private Image background;
	private String[] gameModes = {"Limited", "Endless"}; //get dynamically
	private String[] difficulties = {"Normal", "Hard"}; //get dynamically
	private JComboBox<String> modeChanger;
	private JComboBox<String> difficultyChanger;
	//other settings, resulotion (font size too), decide all game play values with ui
	private GridLayout listLayout = new GridLayout(0, 1);
	private MenusActions listener;
	private JButton button;
	private JLabel label;
	private JTextArea helpText;
	//private JButton restart; //gameModes, gameDifficulty, some variable, maybe an array of game modes taken from engine?
	CardLayout cards;

	public GameWindow() {
		this.setSize(960, 720);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new CardLayout();
		this.setLayout(cards);
		listener = new MenusActions();
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
		helpText = new JTextArea("Move with WASD or Arrows.\nShoot with LMB or Space.\nExit level with Escape.\nHard increases player and enemy HP + enemy speed and decreases enemy cooldown\nEndless introduces waves with increasing numbers and increasing HP depending on kill count."); //\nPause/Unpause with P.");
		helpText.setLineWrap(true);
		panel.add(asProper(helpText));
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

	private JComponent asProper(JComponent component) {
		component.setFont(new Font(component.getFont().getFontName(), Font.BOLD, 20));
		component.setForeground(Color.WHITE);
		component.setOpaque(false);
		return component;
	}

	private JButton asProperButton(JButton button) {
		button = (JButton)asProper(button);
		button.setFont(new Font(button.getFont().getFontName(), Font.BOLD, 40));
		button.addActionListener(listener);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		return button;
	}

	private JLayeredPane asLayeredPane(JPanel panel) {
		panel.setOpaque(false);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, this.getWidth(), this.getHeight());
		panel.setBounds(0, 0, this.getWidth(), this.getHeight());
		layeredPane.add(panel, new Integer(1));
		layeredPane.add(newBackgroundPanel(), new Integer(-1));
		return layeredPane;
	}

	private JPanel newBackgroundPanel() {
		JPanel backgroundPanel = new JPanel(listLayout);
		ImageIcon backgroundActually = new ImageIcon(backgroundLocation); 
		JLabel theThingWeDoToMakeLifeSimple = new JLabel();
		theThingWeDoToMakeLifeSimple.setIcon(backgroundActually);
		backgroundPanel.add(theThingWeDoToMakeLifeSimple);
		backgroundPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
		return backgroundPanel;
	}


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

			
			//infinite:
			//regarding health like I said before?
			//enemies multiply (increase) each number of points
		}
	}

	private void startGame() {
		gamePanel = new GamePanel();
		add(gamePanel, "Game");
		cards.show(getContentPane(), "Game");
		gamePanel.requestFocusInWindow();
		String difficulty = difficultyChanger.getItemAt(difficultyChanger.getSelectedIndex());
		String mode = modeChanger.getItemAt(modeChanger.getSelectedIndex());
		GameTier.startGame(difficulty, mode);
	}

	public void won() {
		this.gamePanel.cleanUp();
		//set score to show
		//show won card, which goes back to menu
		cards.show(this.getContentPane(), "Won");
		this.gamePanel = null;
	}

	public void lost() {
		this.gamePanel.cleanUp();
		//set score to show
		//show lost card, wich goes back to menu
		cards.show(this.getContentPane(), "Lost");
		this.gamePanel = null;
	}

	public GamePanel getInGamePanel() {
		return this.gamePanel;
	}

	public void setGameObjects(ArrayList<AbstractGameObject> gameObjects) {
		this.gamePanel.setGameObjects(gameObjects);
	}

	public void setPlayer(PlayerShip player) {
		this.gamePanel.setPlayer(player);
	}

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

/*
	https://stackoverflow.com/questions/24802461/
	how-to-repaint-properly-in-jpanel-inside-jframe
*/

	//sperate
	public class GamePanel extends JPanel {
		//private GameEngine engine;
		private PlayerShip player;
		private ArrayList<AbstractGameObject> gameObjects;
		private int hpXLocation;
		private int hpYLocation;
		private int scoreXLocation;
		private int socreYLocation;
		//private sprites? array of 'em?

		/*
		//seperate controls
		//private AbstractAction moveUp = () -> player.moveUp();
		private AbstractAction moveUp = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				player.movementControls(ae);
				System.out.println(ae.getActionCommand());
			}
		};

		private AbstractAction exit = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		};
		*/

		public GamePanel() {
			//this.gameEngine = engine;
			//this.player = engine.getPlayer();
			this.addKeyListener(new KeyboardInGameControls());
			this.addMouseListener(new MouseInGameControls());
			this.setBackground(Color.BLACK);
			gameObjects = new ArrayList<AbstractGameObject>(); //dummy for paint, tied to structure problem
			/*
			this.getInputMap().put(KeyStroke.getKeyStroke("W"), "Move Up");
			this.getActionMap().put("Move Up", this.moveUp);

			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "Move Up");
			this.getActionMap().put("Move Up", this.moveUp);

			this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Exit");
			this.getActionMap().put("Exit", this.exit);
			*/

			//rendering is your job, so if you gonna render, you pass the sprites?

			//player.setSprite(null);
			this.setFocusable(true);
			this.requestFocusInWindow();
		}

		//private initlize
		//https://stackoverflow.com/questions/13735402/how-to-initialize-classes-dependent-on-each-other-in-java

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
					System.out.println("Object failed painting: " + gameObject);
					npe.printStackTrace();
					System.exit(0);
				}
			}
		}

		public void setPlayer(PlayerShip player) {
			this.player = player;
			this.hpXLocation = new Double(this.getWidth() * 0.1).intValue();
			this.hpYLocation = new Double(this.getHeight() * 0.1).intValue();
			this.scoreXLocation = new Double(this.getWidth() - this.getWidth() * 0.1).intValue();
			this.socreYLocation = this.hpYLocation;
		}

		public void cleanUp() {
			this.player = null;
			cards.show(getContentPane(), "Main");
		}

		public void setGameObjects(ArrayList<AbstractGameObject> gameObjects) {
			this.gameObjects = gameObjects;
		}

		//key binding
		//have to make an action + method for each button release and button press


		
		
/*
		@Override
		public void keyTyped(KeyEvent keyEvent) {
		}
*/
		class KeyboardInGameControls extends KeyAdapter {
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

			@Override
			public void keyReleased(KeyEvent keyEvent) {
				if (GameTier.isRunning) {
					player.movementKeyReleased(keyEvent);
				}
			}
		}

		class MouseInGameControls extends MouseAdapter {
			@Override
			public void mousePressed(MouseEvent me) {
				if (GameTier.isRunning) {
					player.shoot();
				}
			}
		}
/*
		@Override
		public void mouseClicked(MouseEvent me) {
		}

		@Override
		public void mouseEntered(MouseEvent me) {
		}

		@Override
		public void mouseExited(MouseEvent me) {
		}

		

		@Override
		public void mouseReleased(MouseEvent e) {
		}
*/
	}
}
