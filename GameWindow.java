import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.CardLayout;
import javax.swing.*;
//import javax.swing.Action;
//import javax.swing.AbstractAction;
//import java.awt.event.ActionEvent;
//undestand keystroke, actionmap, inputmap, key binding
//import javax.swing.KeyStroke;

//all
  //extract variables
  //check exception prone

public class GameWindow extends JFrame implements ActionListener {
	private JPanel panel;
	private GamePanel gamePanel;
	public static String backgroundLocation;
	private Image background;
	private JButton button;
	CardLayout cards;

	public GameWindow() {
		this.setSize(640, 480);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cards = new CardLayout();
		this.setLayout(cards);

		panel = new JPanel();
		button = new JButton("Limited");
		button.addActionListener(this);
		panel.add(button);
		this.add(panel, "Main");

		this.gamePanel = new GamePanel();
		this.add(this.gamePanel, "Game");
		this.setBackgroundLocation(backgroundLocation);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == button) {
			//not just card, add a last card and initiate a game and engine with certain mode
			cards.show(this.getContentPane(), "Game");
		}
		//infinite:
		//regarding health like I said before?
		//enemies multiply (increase) each number of points
	}

	public void won() {
		//clean up
		//set score to show
		//remove gamepanel
		//show won card, which goes back to menu
	}

	public void lost() {
		//clean up
		//set score to show
		//remove gamepanel
		//show lost card, wich goes back to menu
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
	public class GamePanel extends JPanel implements KeyListener, MouseListener {
		//private GameEngine engine;
		private PlayerShip player;
		private ArrayList<AbstractGameObject> gameObjects;
		private boolean isRunning;
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
			this.addKeyListener(this);
			this.addMouseListener(this);
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

			this.isRunning = true;
		}

		//private initlize
		//https://stackoverflow.com/questions/13735402/how-to-initialize-classes-dependent-on-each-other-in-java

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2d = (Graphics2D)g;
				
			g2d.drawImage(background, null, null);
			

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
		}

		public void cleanUp() {
			this.player = null;
			this.isRunning = false;
			cards.show(getContentPane(), "Main");
		}

		public void setGameObjects(ArrayList<AbstractGameObject> gameObjects) {
			this.gameObjects = gameObjects;
		}

		//key binding
		//have to make an action + method for each button release and button press
		
		@Override
		public void keyPressed(KeyEvent keyEvent) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			} else if (isRunning) {
				if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
					player.shoot();
				} else {
					player.movementKeyPressed(keyEvent);
				}
			} 
		}

		@Override
		public void keyReleased(KeyEvent keyEvent) {
			if (isRunning) {
				player.movementKeyReleased(keyEvent);
			}
		}

		@Override
		public void keyTyped(KeyEvent keyEvent) {
		}

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
		public void mousePressed(MouseEvent me) {
			if (isRunning) {
				player.shoot();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
}
