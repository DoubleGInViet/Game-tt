package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GuideOverlay {

	private Playing playing;
	private BufferedImage singleEnemy;
	private BufferedImage singleCannon;
	private BufferedImage singleSpike;
	private BufferedImage singleBox;
	private BufferedImage singleBarrel;
	private BufferedImage singleRedPotion;
	private BufferedImage singleBluePotion;
	
	public GuideOverlay(Playing playing) {
		this.playing = playing;
		singleEnemy = LoadSave.GetSpriteAtlas(LoadSave.SINGLE_ENEMY);
		singleCannon = LoadSave.GetSpriteAtlas(LoadSave.SINGLE_CANNON);
		singleSpike = LoadSave.GetSpriteAtlas(LoadSave.SINGLE_SPIKE);
		singleBox = LoadSave.GetSpriteAtlas(LoadSave.SINGLE_BOX);
		singleBarrel = LoadSave.GetSpriteAtlas(LoadSave.SINGLE_BARREL);
		singleRedPotion = LoadSave.GetSpriteAtlas(LoadSave.SINGLE_RED_POTION);
		singleBluePotion = LoadSave.GetSpriteAtlas(LoadSave.SINGLE_BLUE_POTION);
		
	}
	
	public void draw( Graphics g) {
		g.setColor(new Color(0,0,0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_WIDTH);
		
		Font myFont = new Font( "Courier New", 1, 17);
		g.setFont(myFont);
		g.setColor(Color.white);
		g.drawString("Nhấn A để di chuyển sang trái", 20 , 100);
		g.drawString("Nhấn D để di chuyển sang phải", 20 , 150);
		g.drawString("Nhấn SPACE để nhảy lên", 20 , 200);
		g.drawString("Nhấn J để tấn công kẻ thù", 20 , 250);
		g.drawString("Nhấn K để thực hiện đòn tấn công siêu tốc", 20, 300);
		
		g.drawString("Thấy những tên kị sĩ này chứ?", 20, 350);
		g.drawImage(singleEnemy, 340, 315, 30, 40, null);
		g.drawString("Tiêu diệt chúng để qua màn nhé!", 400, 350);
		
		g.drawString("Thấy những ụ pháo này không?", 20, 400);
		g.drawImage(singleCannon, 340, 380, 45, 30, null);
		g.drawString("Không tiêu diệt được chúng đâu, cố gắng né đạn nhé!", 400, 400);
		
		g.drawString("Bước vào đây", 20, 450);
		g.drawImage(singleSpike, 160, 435, 40, 20, null);
		g.drawString("để được đoàn tụ với tổ tiên nhé!", 220, 450);
		
		g.drawString("Hãy phá",20 , 500);
		g.drawImage(singleBox, 110, 480, 40, 30, null);
		g.drawString("để nhặt bình máu", 170, 500);
		g.drawImage(singleRedPotion, 350, 480, 20, 30, null);
		g.drawString("và phá", 380, 500);
		g.drawImage(singleBarrel, 455 , 480, 30, 30, null);
		g.drawString("để nhặt bình năng lượng", 500, 500);
		g.drawImage(singleBluePotion, 750, 480, 20, 30, null);
		
		
		Font conFont = new Font( "Courier New", 1, 10);
		g.setFont(conFont);
		g.setColor(Color.white);
		g.drawString("<<Nhấn SPACE để tiếp tục>>", Game.GAME_WIDTH/2 - 50, Game.GAME_HEIGHT- 15);
		
		Font guideFont = new Font( "Courier New", 1, 25);
		g.setFont(guideFont);
		g.setColor(Color.white);
		g.drawString("HƯỚNG DẪN TRƯỚC KHI CHƠI", Game.GAME_WIDTH/2 - 200, 30);
		
	}
	
	public void keyPressed( KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_SPACE) {
			playing.setGuideDone(true);
		}
	}

	public void update() {
	}
	
}