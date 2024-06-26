package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.UI.URMButtons.URM_SIZE;;

public class GameOverOverlay {
	private Playing playing;
	private BufferedImage img;
	private int imgX, imgY, imgW, imgH;
	private UrmButton menu, play;
	

	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		createImg();
		createButtons();
	}
	
	private void createButtons() {
		int menuX = (int)( 330 * Game.SCALE);
		int playX = (int)( 445 * Game.SCALE);
		int y = (int)( 195 * Game.SCALE);
		play = new UrmButton(playX, y,URM_SIZE , URM_SIZE, 1);
		menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
		
	}

	private void createImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
		imgW = (int)( img.getWidth() * Game.SCALE);
		imgH = (int)(img.getHeight() * Game.SCALE);
		imgX = Game.GAME_WIDTH/2 - imgW/2;
		imgY = (int)( 100 * Game.SCALE);
		
	}

	public void draw( Graphics g) {
		g.setColor(new Color(0,0,0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_WIDTH);
		
		g.drawImage(img, imgX, imgY, imgW, imgH, null);
		
		menu.draw(g);
		play.draw(g);
		
//		g.setColor(Color.white);
//		g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
//		g.drawString("Press ESC to return to Main Menu", Game.GAME_WIDTH / 2, 300);
	}
	
	public void update() {
		menu.update();
		play.update();
	}
	
	

	
	private boolean isIn(MouseEvent e, UrmButton b) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	public void mouseMoved(MouseEvent e) {
		play.setMouseOver(false);
		menu.setMouseOver(false);
		
		if( isIn( e, play)) {
			play.setMouseOver(true);
		}
		else if ( isIn(e, menu)) {
			menu.setMouseOver(true);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if( isIn(e, menu)) {
			if( menu.isMousePressed()) {
				playing.loadFirstLevel();
				playing.setGameState(Gamestate.MENU);
				
				//playing.resetAll();
				
			}
		}
		else if (isIn(e, play)) {
			if( play.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
			}
		}
		menu.resetBools();
		play.resetBools();
	}
	
	public void mousePressed(MouseEvent e) {
		if( isIn( e, play)) {
			play.setMousePressed(true);;
		}
		else if ( isIn(e, menu)) {
			menu.setMousePressed(true);
		}
	}
}
