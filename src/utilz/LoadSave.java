package utilz;

import java.awt.Color;
import static utilz.Constants.Enemy.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.SwordKnight;
import main.Game;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "sword_knight.png";
	public static final String LEVEL_ATLAS = "outside_sprites_castle.png";
	public static final String LEVEL_ONE_DATA = "level_one_data_has_enemy.png";
//	public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTON = "sound_button.png";
	public static final String URMS_BUTTON = "urm_buttons.png";
	public static final String VOLUME_BUTTON = "volume_buttons.png";
	public static final String BACKGROUND_MENU = "background_menu.png";
	public static final String GAME_NAME = "game_name.png";
	public static final String PLAYING_BG_IMG = "playing_bg_img.png";
	public static final String BIG_CLOUDS = "big_clouds.png";
	public static final String SMALL_CLOUDS = "small_clouds.png";
	public static final String SWORD_KNIGHT_SPRITE = "enemy_sword_knight.png";
	public static final String STATUS_BAR = "health_power_bar.png";
	public static final String COMPLETED_IMG = "completed_sprite.png";
	public static final String POTION_ATLAS = "potions_sprites.png";
	public static final String CONTAINER_ATLAS = "objects_sprites.png";
	public static final String TRAP_ATLAS = "trap_atlas.png";
	public static final String CANNON_ATLAS = "cannon_atlas.png";
	public static final String CANNON_BALL = "ball.png";
	public static final String DEATH_SCREEN = "death_screen.png";
	public static final String OPTIONS_MENU = "options_background.png";
	public static final String SINGLE_ENEMY = "single_enemy.png";
	public static final String SINGLE_CANNON = "single_cannon.png";
	public static final String SINGLE_SPIKE = "single_spike.png";
	public static final String SINGLE_BOX = "single_box.png";
	public static final String SINGLE_BARREL = "single_barrel.png";
	public static final String SINGLE_BLUE_POTION = "single_blue_potion.png";
	public static final String SINGLE_RED_POTION = "single_red_potion.png";
	
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		BufferedImage img = null;
		try {
			img = ImageIO.read(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/lvls");
		File file = null;
		
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];
		
		//sap xep file
		for( int i = 0; i < filesSorted.length; i++) {
			for( int j =0; j < files.length; j++) {
				if( files[j].getName().equals((i+1) + ".png")) {
					filesSorted[i] = files[j];
				}
			}
		}
		
//		for( File f: filesSorted) {
//			System.out.println(f.getName());
//		}
		
		BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		for( int i = 0; i< imgs.length; i++) {
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return imgs;
	}
	
	public static ArrayList<SwordKnight> GetKnight(){
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		ArrayList<SwordKnight> list = new ArrayList<>();
		for( int i = 0; i < img.getHeight(); i++) {
			for( int j =0; j< img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getGreen();
				if( value == SWORD_KNIGHT) {
					list.add( new SwordKnight( j * Game.TILE_SIZE , i * Game.TILE_SIZE) );
				}
			}
		}
		return list;
	}
	
	
}
