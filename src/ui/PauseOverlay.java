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
	private UrmButton menuButton, replayButton, unpauseButton;
	private int bgX, bgY, bgW, bgH;
	private AudioOptions audioOptions;

	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();

		audioOptions = playing.getGame().getAudioOptions();
		createURMButtons();

	}

	private void createURMButtons() {

		int menuX = (int) (350 * Game.SCALE);
		int replayX = (int) (430 * Game.SCALE);
		int unpauseX = (int) (510 * Game.SCALE);
		int urmY = (int) (325 * Game.SCALE);
		menuButton = new UrmButton(menuX, urmY, URM_SIZE, URM_SIZE, 2);
		replayButton = new UrmButton(replayX, urmY, URM_SIZE, URM_SIZE, 1);
		unpauseButton = new UrmButton(unpauseX, urmY, URM_SIZE, URM_SIZE, 0);

	}

	private void loadBackground() {
		background = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int) (background.getWidth() * Game.SCALE);
		bgH = (int) (background.getHeight() * Game.SCALE);
		bgX = (int) (Game.GAME_WIDTH / 2 - background.getWidth() / 2);
		bgY = (int) (25 * Game.SCALE);

	}

	public void update() {

		menuButton.update();
		replayButton.update();
		unpauseButton.update();
		audioOptions.update();
	}

	public void draw(Graphics g) {
		// background pause menu
		g.drawImage(background, bgX, bgY, bgW, bgH, null);

		// urm button
		menuButton.draw(g);
		replayButton.draw(g);
		unpauseButton.draw(g);
		audioOptions.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		audioOptions.mouseDragged(e);

	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, menuButton)) {
			menuButton.setMousePressed(true);
		} else if (isIn(e, replayButton)) {
			replayButton.setMousePressed(true);
		} else if (isIn(e, unpauseButton)) {
			unpauseButton.setMousePressed(true);
		} else
			audioOptions.mousePressed(e);

	}

	public void mouseReleased(MouseEvent e) {

		// menu, replay and unpause
		if (isIn(e, menuButton)) {
			if (menuButton.isMousePressed()) {
				playing.setGameState(Gamestate.MENU);
				playing.loadFirstLevel();
				playing.unpause();
			}
		}

		else if (isIn(e, replayButton)) {
			if (replayButton.isMousePressed()) {
				playing.resetAll();
			}
		}

		else if (isIn(e, unpauseButton)) {
			if (unpauseButton.isMousePressed()) {
				playing.unpause();
			}
		} else
			audioOptions.mouseReleased(e);
		menuButton.resetBools();
		replayButton.resetBools();
		unpauseButton.resetBools();

	}

	public void mouseMoved(MouseEvent e) {

		menuButton.setMouseOver(false);
		replayButton.setMouseOver(false);
		unpauseButton.setMouseOver(false);

		// menu, replay and unpause button
		if (isIn(e, menuButton)) {
			menuButton.setMouseOver(true);
		} else if (isIn(e, replayButton)) {
			replayButton.setMouseOver(true);
		} else if (isIn(e, unpauseButton)) {
			unpauseButton.setMouseOver(true);
		} else
			audioOptions.mouseMoved(e);
	}

	private boolean isIn(MouseEvent e, PauseButton pb) {
		return pb.getBounds().contains(e.getX(), e.getY());
	}
}
