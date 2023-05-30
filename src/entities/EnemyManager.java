package entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.Enemy.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] knightArr;
	private ArrayList<SwordKnight> knight = new ArrayList<>();

	public EnemyManager(Playing playing) {
		this.playing = playing;
		LoadEnemyImgs();

	}

	public void loadEnemies(Level level) {
		knight = level.getKnights();

	}

	public void update(int[][] lvlData, Player player) {
		boolean isAnyActive = false;
		for (SwordKnight k : knight) {
			if (k.isActive()) {
				k.update(lvlData, player);
				isAnyActive = true;
			}
		}
		if (!isAnyActive) {
			playing.setLevelCompleted(true);
		}
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawKnight(g, xLvlOffset);
	}

	private void drawKnight(Graphics g, int xLvlOffset) {
		for (SwordKnight k : knight) {
			if (k.isActive()) {
				g.drawImage(knightArr[k.getState()][k.getAniIndex()],
						(int) k.getHitbox().x - xLvlOffset - SWORD_KNIGHT_DRAWOFFSET_X + k.flipX(),
						(int) k.getHitbox().y - SWORD_KNIGHT_DRAWOFFSET_Y, SWORD_KNIGHT_WIDTH * k.flipW(),
						SWORD_KNIGHT_HEIGHT, null);
				k.drawHitbox(g, xLvlOffset);
				k.drawAttackBox(g, xLvlOffset);
			}
		}
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (SwordKnight k : knight) {
			if (k.getCurrentHealth() > 0)
				if (k.isActive()) {
					if (attackBox.intersects(k.getHitbox())) {
						k.hurt(10);
						return;
					}
				}
		}
	}

	private void LoadEnemyImgs() {
		knightArr = new BufferedImage[5][8];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SWORD_KNIGHT_SPRITE);
		for (int j = 0; j < knightArr.length; j++) {
			for (int i = 0; i < knightArr[j].length; i++) {
				knightArr[j][i] = temp.getSubimage(i * SWORD_KNIGHT_WIDTH_DEFAULT, j * SWORD_KNIGHT_HEIGHT_DEFAULT,
						SWORD_KNIGHT_WIDTH_DEFAULT, SWORD_KNIGHT_HEIGHT_DEFAULT);
			}
		}

	}

	public void resetAllEnimies() {
		for (SwordKnight k : knight) {
			k.resetEnemy();
		}
	}

}
