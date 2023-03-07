package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;

public class Playing extends State implements Statemethods{
	private LevelManager levelManager;
	private Player player;
	
	public Playing(Game game) {
		super(game);
		initClasses();
	}

	
	
	private void initClasses() {
		levelManager = new LevelManager(game);
		player = new Player(200, 200, (int)(64 * Game.SCALE), (int)(40* Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());	
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}



	@Override
	public void update() {
		player.update();
		levelManager.update();
		
	}



	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_J:
			player.setAttacking(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_ESCAPE:
			Gamestate.state = Gamestate.MENU;
		}
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A: {
			player.setLeft(false);

			break;
		}
		case KeyEvent.VK_D: {
			player.setRight(false);
			break;
		}
		case KeyEvent.VK_SPACE: {
			player.setJump(false);
			break;
		}

		}
		
	}
}
