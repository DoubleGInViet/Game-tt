package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import audio.AudioPlayer;
import entities.Player;
import levels.LevelManager;
import entities.EnemyManager;

import main.Game;
import objects.ObjectManager;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;
import ui.GameOverOverlay;
import ui.GuideOverlay;
import ui.LevelCompletedOverlay;


public class Playing extends State implements Statemethods {
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private ObjectManager objectManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletedOverlay levelCompletedOverlay;
	private GuideOverlay guideOverlay;

	private Player player;
	private boolean paused = false;
	private boolean gameOver;
	private boolean lvlCompleted ;
	private boolean playerDying;
	private boolean guideDone = false;

	private int xLvlOffset;
	private int leftBorder = (int) (0.4 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.6 * Game.GAME_WIDTH);
	private int maxLvlOffsetX ;
	
	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudPos;
	private Random rnd = new Random();

	public Playing(Game game) {
		
		super(game);
		initClasses();
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
		smallCloudPos = new int[8];
		for (int i = 0; i < smallCloudPos.length; i++) {
			smallCloudPos[i] = (int) (70 * Game.SCALE) + rnd.nextInt((int) (150 * Game.SCALE));
		}
		calcLvlOffset();
		loadStartLevel();
	}

	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
	}
	
	public void loadFirstLevel() {
		resetAll();
		levelManager.loadFirstLevel();
		player.setSpawn(levelManager.getFirstLevel().getPlayerSpawn());

	}
	
	private void loadStartLevel() {
		enemyManager.loadEnemies(levelManager.getFirstLevel());
		objectManager.loadObjects(levelManager.getFirstLevel());
	}

	private void calcLvlOffset() {
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
	}

	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		objectManager = new ObjectManager(this);
		
		player = new Player( 200, 200, (int)(64* Game.SCALE), (int)(40 * Game.SCALE), this);
		player.loadLvlData(levelManager.getFirstLevel().getLevelData());
		player.setSpawn(levelManager.getFirstLevel().getPlayerSpawn());
		
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletedOverlay = new LevelCompletedOverlay(this);
		guideOverlay = new GuideOverlay(this);
		
	}

	@Override
	public void update() {
		if( paused) {
			pauseOverlay.update();
		}
		else if ( lvlCompleted ) {
			levelCompletedOverlay.update();
		}
		else if(gameOver) {
			gameOverOverlay.update();
		}
		else if( !guideDone) {
			guideOverlay.update();
		}
		else if(playerDying) {
			player.update();
		}
		else {
			levelManager.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			player.update();
			checkCloseToBorder();
		}
		
	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder) {
			xLvlOffset += diff - rightBorder;

		} else if (diff < leftBorder) {
			xLvlOffset += diff - leftBorder;

		}

		if (xLvlOffset > maxLvlOffsetX) {
			xLvlOffset = maxLvlOffsetX;

		} else if (xLvlOffset < 0) {
			xLvlOffset = 0;

		}

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		for (int i = 0; i < 5; i++) {
			g.drawImage(bigCloud, 0 + i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (200 * Game.SCALE),
					BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		}
		for (int i = 0; i < smallCloudPos.length; i++) {
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudPos[i],
					SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
		}

		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);
		
		

		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} 
		else if( !guideDone) {
			guideOverlay.draw(g);
		}
		else if(lvlCompleted) {
			levelCompletedOverlay.draw(g);
		}
		else if (gameOver) {
			gameOverOverlay.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (!gameOver)
			if (paused) {
				pauseOverlay.mouseDragged(e);
			}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if( paused)
				pauseOverlay.mousePressed(e);
			else if( lvlCompleted)
				levelCompletedOverlay.mousePressed(e);
		}
		else 
			gameOverOverlay.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if( paused )
				pauseOverlay.mouseReleased(e);
			else if (lvlCompleted)
				levelCompletedOverlay.mouseReleased(e);
		}
		else
			gameOverOverlay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if( paused )
				pauseOverlay.mouseMoved(e);
			else if (lvlCompleted)
				levelCompletedOverlay.mouseMoved(e);
		}
		else
			gameOverOverlay.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if( gameOver) {
			
		}
		else if( !guideDone) {
			guideOverlay.keyPressed(e);
		}
		else 
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_J: {
				if (player.isInAir()) {
					player.setAttack_jump(true);
					break;
				} else {
					player.setAttacking(true);
					break;
				}
			}
			case KeyEvent.VK_K:
				player.powerAttack();
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(true);
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
			}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
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

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

	public void setLvlOffset(int lvlOffset) {
		this.maxLvlOffsetX = lvlOffset;
	}
	
	public void unpause() {
		paused = false;
	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		lvlCompleted = false;
		playerDying = false;
		player.resetAll();
		enemyManager.resetAllEnimies();
		objectManager.resetAllObjects();
	}
	
	public void checkObjectHit(Rectangle2D.Float attackBox) {
		objectManager.checkObjectHit(attackBox);
		
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}
	
	public void checkPotionTouched(Rectangle2D.Float hitbox) {
		objectManager.checkObjectTouched(hitbox);
		
	}
	
	public void checkSpikesTouched(Player p) {
		objectManager.checkSpikesTouched(p);
		
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted = levelCompleted;	
		if( lvlCompleted )
			game.getAudioPlayer().lvlCompleted();
	}
	
	public ObjectManager getObjectManager() {
		return objectManager;
	}
	
	public LevelManager getLevelManager() {
		return levelManager;
	}

	public void setPlayerDying(boolean playerDying) {
		this.playerDying= playerDying;
		
	}

	public void setGuideDone(boolean b) {
		this.guideDone = b;
		
	}
	
}
