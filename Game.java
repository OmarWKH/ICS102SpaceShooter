import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.util.ArrayList;
//import java.awt.event.KeyListener;
//import java.awt.event.KeyEvent;
import java.awt.Color;
//import javax.swing.Action;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
//undestand keystroke, actionmap, inputmap, key binding
import javax.swing.KeyStroke;

public class Game extends JFrame {
	private GamePanel panel;

	public Game(PlayerShip player, ArrayList<PlayerShip> gameObjects) {
		this.setSize(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.panel = new GamePanel(player, gameObjects);
		this.add(this.panel);
		this.setVisible(true);
	}

/*
	https://stackoverflow.com/questions/24802461/
	how-to-repaint-properly-in-jpanel-inside-jframe
*/


	private class GamePanel extends JPanel {
		private ArrayList<PlayerShip> gameObjects;
		private PlayerShip player;
		//seperate controls
		//private AbstractAction moveUp = () -> player.moveUp();
		private AbstractAction moveUp = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				player.moveUp();
			}
		};

		private GamePanel(PlayerShip player, ArrayList<PlayerShip> gameObjects) {
			this.gameObjects = gameObjects;
			this.player = player;
			//this.addKeyListener(this);
			this.setBackground(Color.BLACK);

			this.getInputMap().put(KeyStroke.getKeyStroke("W"), "Move Up");
			this.getActionMap().put("Move Up", this.moveUp);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for (PlayerShip gameObject : gameObjects) {
				//could be called anywhere
				gameObject.paint(g);
			}
		}

		//key binding
		/*
		@Override
		public void keyPressed(KeyEvent key) {
			player.keyPressed(key);
			this.setVisible(false);
		}

		@Override
		public void keyReleased(KeyEvent key) {
		}

		@Override
		public void keyTyped(KeyEvent key) {
		}
		*/
	}
}