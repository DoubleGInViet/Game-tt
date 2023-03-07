package entities;

import static utilz.Constants.Directions.DOWN;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;
import static utilz.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
	private boolean up, right, down, left, jump;
	private boolean moving = false;
	private float playerSpeed = 2f;
	private boolean attacking = false;
	private int[][] lvlData;
	private float xDrawOffset = 15 * Game.SCALE;
	private float yDrawOffset = 6 * Game.SCALE;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.05f * Game.SCALE;
	private float jumpSpeed = -3f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	// x = 11, y = 6, width = 24, height = 28
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAni();
		initHitbox(x, y, 17 * Game.SCALE, 28 * Game.SCALE);
		
	}

	public void update() {
		updatePos();
		updateAnimation();
		setAnimation();
	}

	public void render(Graphics g) {
		update();
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset),
				(int) width, (int) height, null);
		drawHitbox(g);
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	private void updateAnimation() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}
		}

	}

	private void setAnimation() {

		int startAni = playerAction;

		if (moving) {
			playerAction = RUNNING;
		} else {
			playerAction = IDLE;
		}
		
		if( inAir) {
			if(airSpeed < 0) {
				playerAction = JUMPING;
			}
			else
				playerAction = FALLING;
		}
		
		if (attacking) {
			playerAction = ATTACK;
		}

		if (startAni != playerAction) {
			resetAnimation();
		}
	}

	public void resetAnimation() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;
		
		if( jump) {
			jump();
		}

		if (!left && !right && !inAir) {
			return;
		}
		
		float xSpeed = 0;
		
		if (left) {
			xSpeed -= playerSpeed;
		}
		if (right) {
			xSpeed += playerSpeed;
		}
		
		if(!inAir) {
			if(!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			}
		}
		
		if( inAir) {
			
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y+= airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}
			else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if( airSpeed > 0) {
					resetInAir();
				}
				else {
					airSpeed = fallSpeedAfterCollision;
				}
				updateXPos(xSpeed);
			}		
		}
		else {
			updateXPos(xSpeed);
		}				
		moving = true;
	}
	
	private void jump() {
		if( inAir) {
			return;
		}
		inAir = true;
		airSpeed = jumpSpeed;
		
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;		
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
	
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(xSpeed + hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
			moving = true;			
		}
		else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed );
			
		}
	}
	

	private void loadAni() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[9][4];
		for (int i = 0; i < animations.length; i++) {
			for (int j = 0; j < animations[i].length; j++) {
				animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
			}
		}
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if( !IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	
	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void windowFocusLost() {
		left = false;
		right = false;
		down = false;
		up = false;
	}
}
