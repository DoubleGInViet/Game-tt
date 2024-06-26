package main;

import java.awt.Graphics;

import audio.AudioPlayer;
import gamestates.GameOptions;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import ui.AudioOptions;
import utilz.LoadSave;


public class Game implements Runnable {
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Playing playing;
	private Menu menu;
	private GameOptions gameOptions;
	private AudioOptions audioOptions;
	private AudioPlayer audioPlayer;
	
	public static final int TILES_DEFAULT_SIZE = 32;
	public static final float SCALE = 1.5f;
	public static final int TILES_IN_WIDTH = 26;
	public static final int TILES_IN_HEIGHT = 14;
	public static final int TILE_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public static final int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
	public static final int GAME_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;
	
	
	public Game() {
		LoadSave.GetAllLevels();
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		startGameLoop();
		
	}

	public void initClasses() {
		audioOptions = new AudioOptions(this);
		audioPlayer = new AudioPlayer();
		menu = new Menu(this);
		playing = new Playing(this);
		gameOptions = new GameOptions(this);
		
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		switch( Gamestate.state ) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case OPTIONS:
			gameOptions.update();
			break;
		case QUIT:
		default:
			System.exit(0);
			break;
		}
		
		
	}
	
	public void render(Graphics g) {
		switch( Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		case OPTIONS:
			gameOptions.draw(g);
			break;
		default:
			break;	
		}
		
		
		
	}
	
	public void windowFocusLost() {
		if( Gamestate.state == Gamestate.PLAYING) {
			playing.getPlayer().resetDirBooleans();
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
	public GameOptions getGameOptions() {
		return gameOptions;
	}
	
	public AudioOptions getAudioOptions() {
		return audioOptions;
	}
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
	
	
	@Override
	public void run() {
		// Mỗi frame kéo dài bao lâu
		double timePerFrame = 1000000000.0 / FPS_SET;
		// Khoảng thời gian giữa mỗi lần update
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		double deltaU = 0;
		double deltaF = 0;
		
		long lastCheck = System.currentTimeMillis();
		
		int frames = 0;
		int updates = 0;

		while (true) {

			long currentTime = System.nanoTime();
			// số lần cập nhật trong khoảng thời gian current - pre, nếu deltaU >=1 tính là 1 lần update
			// current - pre thường là 1000 nên deltaU thường nhỏ hơn 1
			// cộng dồn vào đến khi lớn hơn bằng 1 mới tính là 1 lần update
			deltaU += (currentTime - previousTime) / timePerUpdate;			
			if (deltaU >= 1) {
				
				update();
				deltaU--;
				updates++;
			}

			deltaF += (currentTime - previousTime) / timePerFrame;		
			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			
			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
//				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;

			}
			previousTime = currentTime;


		}
		
		

	}

}
