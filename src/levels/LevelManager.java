package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
	
	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelOne  = new Level(LoadSave.GetLevelData());
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
			for( int j =0; j< levelOne.getLevelData()[0].length ; j++) {
				int index = levelOne.getSpriteIndex(j, i); 
				g.drawImage( levelSprite[index], Game.TILE_SIZE * j - lvlOffset , Game.TILE_SIZE*i, Game.TILE_SIZE, Game.TILE_SIZE, null); 
			}
		}
	}
	
	public void update() {
		
	}
	
	public Level getCurrentLevel() {
		return levelOne;
	}
}
