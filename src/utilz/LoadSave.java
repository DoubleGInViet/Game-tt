package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "knight_mains.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
//	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
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
	
	public static int[][] GetLevelData(){
		
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		int[][] lvData = new int[img.getHeight()][img.getWidth()];
		for( int i = 0; i < img.getHeight(); i++) {
			for( int j =0; j< img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getRed();
				if( value >= 48) {
					value = 0;
				}
				lvData[i][j] = value;
			}
		}
		return lvData;
	}
}
