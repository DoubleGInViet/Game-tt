package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.prefs.BackingStoreException;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Menu extends State implements Statemethods {
	
	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg;
	private BufferedImage bgrImg, gameName;
	private int xMenu, yMenu, menuHeight, menuWidth;
	
	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBgr();
		
	}

	
	private void loadBgr() {
		bgrImg = LoadSave.GetSpriteAtlas(LoadSave.BACKGROUND_MENU);
		gameName = LoadSave.GetSpriteAtlas(LoadSave.GAME_NAME);
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
		xMenu = Game.GAME_WIDTH/2 - menuWidth/2;
		yMenu = (int) ( 95 * Game.SCALE);		
	}


	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH/2 , (int)(200 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH/2, (int)(270 * Game.SCALE), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH/2, (int)(340 * Game.SCALE), 2, Gamestate.QUIT);
		
	}

	@Override
	public void update() {
		for( MenuButton mb : buttons) {
			mb.update();
		}
		
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(bgrImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(gameName, xMenu, 10, menuWidth, 100 , null);
		g.drawImage(backgroundImg, xMenu, yMenu, menuWidth, menuHeight, null);
		
		for(MenuButton mb : buttons) {
			mb.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		for(MenuButton mb : buttons) {
			if(isIn(e, mb))
				mb.setMousePressed(true);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb: buttons) {
			if( isIn(e, mb)) {
				if(mb.isMousePressed()) {
					mb.applyGamestate();
				}
				break;
			}
		}
		resetButtons();
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
			mb.resetBools();
		}
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.setMouseOver(false);
		}
		
		for(MenuButton mb : buttons) {
			if( isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
