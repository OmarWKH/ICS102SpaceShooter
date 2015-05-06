import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
//import javax.swing.Action;
//import javax.swing.AbstractAction;
//import java.awt.event.ActionEvent;
//undestand keystroke, actionmap, inputmap, key binding
//import javax.swing.KeyStroke;

//all
  //extract variables
  //check exception prone

public class GameWindow extends JFrame {
	private GamePanel gamePanel;

	public GameWindow() {
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gamePanel = new GamePanel();
		this.add(this.gamePanel);
		this.setVisible(true);
	}

	public GamePanel getInGamePanel() {
		return this.gamePanel;
	}

	public void setGameObjects(ArrayList<GameObject> gameObjects) {
		this.gamePanel.setGameObjects(gameObjects);
	}

	public void setPlayer(PlayerShip player) {
		this.gamePanel.setPlayer(player);
	}

/*
	https://stackoverflow.com/questions/24802461/
	how-to-repaint-properly-in-jpanel-inside-jframe
*/


	public class GamePanel extends JPanel implements KeyListener {
		//private GameEngine engine;
		private PlayerShip player;
		private ArrayList<GameObject> gameObjects;
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

		private GamePanel() {
			//this.gameEngine = engine;
			//this.player = engine.getPlayer();
			this.addKeyListener(this);
			this.setBackground(Color.BLACK);

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

			for (GameObject gameObject : gameObjects) {
				//could be called anywhere
				gameObject.draw(g2d);
			}
		}

		public void setPlayer(PlayerShip player) {
			this.player = player;
		}

		public void setGameObjects(ArrayList<GameObject> gameObjects) {
			this.gameObjects = gameObjects;
		}

		//key binding
		//have to make an action + method for each button release and button press
		
		@Override
		public void keyPressed(KeyEvent keyEvent) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			} else {
				player.keyPressed(keyEvent);
			}
		}

		@Override
		public void keyReleased(KeyEvent keyEvent) {
			player.keyReleased(keyEvent);
		}

		@Override
		public void keyTyped(KeyEvent keyEvent) {
		}
	}
}
