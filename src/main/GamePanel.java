package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInput;
import static inputs.KeyboardInput.*;
import inputs.MouseInput;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

	
	private MouseInput mouseInput;
	private int frames = 0;
	private long lastCheck = 0;
	private Game game;

	public GamePanel(Game game) {

		mouseInput = new MouseInput(this);
		this.game = game;		
		setPanelSize();
		addKeyListener(new KeyboardInput(this));
		addMouseListener(mouseInput);
		addMouseMotionListener(mouseInput);

	}

	

	private void setPanelSize() {
		Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
		setPreferredSize(size);
	}

	public void updateGame() {		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}
	
	public Game getGame() {
		return game;
	}

}
