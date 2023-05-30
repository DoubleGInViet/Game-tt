package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] levelSprite;
	private ArrayList<Level> levels;
	private int lvlIndex = 0;
	
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levels = new ArrayList<>();
		buildAllLevels();
	}
	
	public void loadNextLevel() {
		lvlIndex++;
		if( lvlIndex >= levels.size() ) {
			lvlIndex = 0;
			Gamestate.state = Gamestate.MENU;			
		}	
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManager().loadObjects(newLevel);
	}
	
	private void buildAllLevels() {
		BufferedImage[] allLevels = LoadSave.GetAllLevels();
		for( BufferedImage img: allLevels) {
			levels.add(new Level(img));
		}		
	}
	
	
	// bat dau lai tu level 1
	public void loadFirstLevel() {
		lvlIndex = 0;
		Level newLevel = levels.get(lvlIndex);
		game.getPlaying().getEnemyManager().loadEnemies(newLevel);
		game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
		game.getPlaying().setLvlOffset(newLevel.getLvlOffset());
		game.getPlaying().getObjectManager().loadObjects(newLevel);
	}

	public void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
		levelSprite = new BufferedImage[48];
		for( int i = 0; i< 4; i++) {
			for ( int j =0; j<12 ; j++) {
				int index = i*12 + j;
				levelSprite[index] = img.getSubimage(j*32, i*32, 32, 32);
			}
		}
		
	}
	
	public void draw( Graphics g, int lvlOffset) {
		for( int i =0; i < Game.TILES_IN_HEIGHT; i++) {
			for( int j =0; j< levels.get(lvlIndex).getLevelData()[0].length ; j++) {
				int index = levels.get(lvlIndex).getSpriteIndex(j, i); 
				g.drawImage( levelSprite[index], Game.TILE_SIZE * j - lvlOffset , Game.TILE_SIZE*i, Game.TILE_SIZE, Game.TILE_SIZE, null); 
			}
		}
	}
	
	public void update() {
		
	}
	

	public void setLvlIndex(int lvlIndex) {
		this.lvlIndex = lvlIndex;
	}

	public Level getCurrentLevel() {
		return levels.get(lvlIndex);
	}
	
	public Level getFirstLevel() {
		return levels.get(0);
	}
	

	public int getAmountOfLevels() {
		return levels.size();
	}


	public int getLvlIndex() {
		return lvlIndex;
	}
	
	
}
