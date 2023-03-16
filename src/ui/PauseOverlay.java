package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.*;
import static utilz.Constants.UI.VolumeButtons.*;
public class PauseOverlay {
	private Playing playing;
	private BufferedImage background;
	private SoundButton musicButton, sfxButton;
	private UrmButton menuButton, replayButton, unpauseButton;
	private VolumeButton volumeButton, slider;
	private int bgX, bgY, bgW, bgH;
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();
		createURMButtons();
		createVolumeButton();
	}
	
	
	private void createVolumeButton() {
		int vX = (int) (352 * Game.SCALE);
		int vY  = (int)( 278 * Game.SCALE);
		volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
		
	}


	private void createURMButtons() {
		
		int menuX = (int)( 350 * Game.SCALE );
		int replayX = (int)( 430 * Game.SCALE);
		int unpauseX = (int)( 510 * Game.SCALE);
		int urmY = (int)( 325 * Game.SCALE);
		menuButton = new UrmButton(menuX, urmY, URM_SIZE , URM_SIZE, 2);
		replayButton = new UrmButton(replayX, urmY, URM_SIZE, URM_SIZE, 1);
		unpauseButton = new UrmButton(unpauseX, urmY, URM_SIZE, URM_SIZE, 0);
		
	}


	private void createSoundButtons() {
		// music and sfx have the same x
		int soundX = (int)( 500 * Game.SCALE);
		int musicY = (int)( 140 * Game.SCALE);
		int sfxY = (int)( 186 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
		
	}


	private void loadBackground() {
		background = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int)( background.getWidth() * Game.SCALE);
		bgH = (int)( background.getHeight() * Game.SCALE);
		bgX = (int)( Game.GAME_WIDTH/2 - background.getWidth()/2);
		bgY = (int)( 25 * Game.SCALE);
		
	}


	public void update() {
		musicButton.update();
		sfxButton.update();
		
		menuButton.update();
		replayButton.update();
		unpauseButton.update();
		volumeButton.update();
	}
	
	public void draw(Graphics g) {
		// background pause menu
		g.drawImage(background, bgX, bgY, bgW, bgH, null);
		
		// music and sfx
		musicButton.draw(g);
		sfxButton.draw(g);
		
		// urm button
		menuButton.draw(g);
		replayButton.draw(g);
		unpauseButton.draw(g);
		
		//volume
		volumeButton.draw(g);
	}
	
	
	public void mouseDragged(MouseEvent e) {
		if( volumeButton.isMousePressed()) {
			volumeButton.changeX(e.getX());
		}
		
	}
	
	

	public void mousePressed(MouseEvent e) {
		if( isIn(e , musicButton) ) {
			musicButton.setMousePressed(true);
		}
		else if( isIn( e, sfxButton)) {
			sfxButton.setMousePressed(true);
		}
		
		else if( isIn( e, menuButton)) {
			menuButton.setMousePressed(true);
		}
		else if( isIn(e, replayButton)) {
			replayButton.setMousePressed(true);
		}
		else if( isIn(e, unpauseButton)) {
			unpauseButton.setMousePressed(true);
		}
		else if( isIn(e, volumeButton)) {
			volumeButton.setMousePressed(true);
		}
	}



	public void mouseReleased(MouseEvent e) {
		if( isIn(e , musicButton) ) {
			if( musicButton.isMousePressed()) {
				musicButton.setMuted( !musicButton.isMuted()  );
				
			}
		}
		else if( isIn( e, sfxButton)) {
			if( sfxButton.isMousePressed()) {
				sfxButton.setMuted( !sfxButton.isMuted() );
			}
		}
		musicButton.resetBools();
		sfxButton.resetBools();
		
		
		//menu, replay and unpause
		if( isIn(e, menuButton)) {
			if(menuButton.isMousePressed()) {
				Gamestate.state = Gamestate.MENU;
			}
		}
		menuButton.resetBools();
		
		if( isIn( e, replayButton)) {
			if(replayButton.isMousePressed()) {
				System.out.println("replay lvl!");
			}
		}
		replayButton.resetBools();
		
		if( isIn( e, unpauseButton)) {
			if(unpauseButton.isMousePressed()) {			
				playing.unpause();
			}
		}		
		unpauseButton.resetBools();
		
		// volume button
		volumeButton.resetBools();
	}



	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuButton.setMouseOver(false);
		replayButton.setMouseOver(false);
		unpauseButton.setMouseOver(false);
		volumeButton.setMouseOver(false);

		// music and sfx
		if( isIn( e, musicButton)) {
			musicButton.setMouseOver(true);
		}
		else if( isIn(e, sfxButton)) {
			sfxButton.setMouseOver(true);
		}
		
		//menu, replay and unpause button
		else if( isIn( e, menuButton)) {
			menuButton.setMouseOver(true);
		}
		else if( isIn(e, replayButton)) {
			replayButton.setMouseOver(true);
		}
		else if( isIn(e, unpauseButton)) {
			unpauseButton.setMouseOver(true);
		}
		
		
		else if( isIn(e, volumeButton)) {
			volumeButton.setMouseOver(true);
		}
	}
	
	private boolean isIn( MouseEvent e, PauseButton pb) {
		return pb.getBounds().contains(e.getX(), e.getY());
	}
}
