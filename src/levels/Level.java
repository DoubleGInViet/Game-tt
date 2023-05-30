package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.SwordKnight;
import main.Game;
import objects.Cannon;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;
import utilz.HelpMethods;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetKnight;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {
	private int[][] lvlData;
	private BufferedImage img;
	private ArrayList<SwordKnight> swordKnights;
	private ArrayList<Potion> potions;
	private ArrayList<GameContainer> containers;
	private ArrayList<Spike> spikes;
	private ArrayList<Cannon> cannons;
	private int lvlTilesWide ;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	
	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		createPotions();
		createContainers();
		createSpikes();
		createCannons();
		calcLvlOffsets();
		calcPlayerSpawn();
	}
	
	private void createCannons() {
		cannons = HelpMethods.GetCannons(img);
		
	}

	private void createPotions() {
		potions = HelpMethods.GetPotions(img);
		
	}

	private void createContainers() {
		containers = HelpMethods.GetContainers(img);
		
	}
	
	private void createSpikes() {
		spikes = HelpMethods.GetSpikes(img);
		
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
		
	}

	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILE_SIZE * maxTilesOffset;
	}

	private void createEnemies() {
		swordKnights = GetKnight(img);
		
	}

	private void createLevelData() {
		lvlData = GetLevelData(img); 
		
	}

	public int getSpriteIndex(int x, int y) {
			return lvlData[y][x];
	}
	
	public int[][] getLevelData(){
		return lvlData;
	}
	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}
	
	public ArrayList<SwordKnight> getKnights(){
		return swordKnights;
	}
	
	public Point getPlayerSpawn() {
		return playerSpawn;
	}
	
	public ArrayList<Potion> getPotions(){
		return potions;
	}
	
	public ArrayList<GameContainer> getContainers(){
		return containers;	
	}
	
	public ArrayList<Spike> getSpikes(){
		return spikes;
	}
	
	public ArrayList<Cannon> getCannons(){
		return cannons;
	}
}
