package entities;

import static utilz.HelpMethods.*;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import main.Game;
import gamestates.Playing;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private boolean right, left, jump, hit;
	private boolean moving = false;
	private boolean attacking = false;
	private boolean attack_jump = false;
	private int[][] lvlData;
	private float xDrawOffset = 22 * Game.SCALE;
	private float yDrawOffset = 6 * Game.SCALE;
	private int hitboxOffset = 0;

	// Jumping / Gravity
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.3f * Game.SCALE;

	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarXStart = (int) (34 * Game.SCALE);
	private int healthBarYStart = (int) (14 * Game.SCALE);
	private int healthWidth = healthBarWidth;

	private int powerBarWidth = (int) (104 * Game.SCALE);
	private int powerBarHeight = (int) (2 * Game.SCALE);
	private int powerBarXStart = (int) (44 * Game.SCALE);
	private int powerBarYStart = (int) (34 * Game.SCALE);
	private int powerWidth = powerBarWidth;
	private int powerMaxValue = 200;
	private int powerValue = powerMaxValue;

	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private Playing playing;

	private int tileY = 0;
	private boolean powerAttackActive;
	private int powerAttackTick;
	private int powerGrowSpeed = 15;
	private int powerGrowTick;

	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		this.state = IDLE;
		this.maxHealth = 100;
		this.currentHealth = maxHealth;
		this.walkSpeed = Game.SCALE * 1.0f;
		loadAni();
		initHitbox(17, 28);
		intitAttackBox();
	}

	public void setSpawn(Point spawn) {
		this.x = spawn.x;
		this.y = spawn.y;
		hitbox.x = x;
		hitbox.y = y;
	}

	private void intitAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (22 * Game.SCALE), (int) (20 * Game.SCALE));
	}

	public void update() {
		updateHealthBar();
		updatePowerBar();

		if (currentHealth <= 0) {
			if (state != DEAD) {
				state = DEAD;
				aniTick = 0;
				aniIndex = 0;
				playing.setPlayerDying(true);
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
			} else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
				playing.setGameOver(true);
				playing.getGame().getAudioPlayer().stopSong();
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
			} else
				updateAnimation();
			return;
		}

		updateAttackBox();
		updatePos();
		if (moving) {
			checkPotionTouched();
			checkSpikesTouched();
			tileY = (int) (hitbox.y / Game.TILE_SIZE);
			if (powerAttackActive) {
				powerAttackTick++;
				if (powerAttackTick >= 35) {
					powerAttackTick = 0;
					powerAttackActive = false;
				}
			}
		}

		if (attacking || powerAttackActive) {
			checkAttack();
		}
		updateAnimation();
		setAnimation();
	}

	private void checkSpikesTouched() {
		playing.checkSpikesTouched(this);

	}

	private void checkPotionTouched() {
		playing.checkPotionTouched(hitbox);

	}

	private void checkAttack() {

		if (attackChecked || aniIndex != 1) {
			return;
		}
		attackChecked = true;

		if (powerAttackActive) {
			attackChecked = false;
		}
		playing.checkEnemyHit(attackBox);
		playing.checkObjectHit(attackBox);
		playing.getGame().getAudioPlayer().playAttackSound();

	}

	private void updateAttackBox() {
		if (right && left) {
			if (flipW == 1) {
				attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 5);
			} else {
				attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 5);
			}
		} else if (right || (powerAttackActive && flipW == 1)) {
			attackBox.x = hitbox.x + hitbox.width + (int) (5 * Game.SCALE);
		} else if (left || (powerAttackActive && flipW == -1)) {
			attackBox.x = hitbox.x - hitbox.width - (int) (5 * Game.SCALE);
		}
		attackBox.y = hitbox.y + (10 * Game.SCALE);

	}

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}

	private void updatePowerBar() {
		powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);
		powerGrowTick++;
		if (powerGrowTick >= powerGrowSpeed) {
			powerGrowTick = 0;
			changePower(1);
		}
	}

	public void changeHealth(int value) {
		currentHealth += value;
		if (currentHealth <= 0) {
			currentHealth = 0;
		} else if (currentHealth >= maxHealth) {
			currentHealth = maxHealth;
		}
	}

	public void changePower(int value) {
		powerValue += value;
		if (powerValue <= 0) {
			powerValue = 0;
		} else if (powerValue >= powerMaxValue) {
			powerValue = powerMaxValue;
		}

	}

	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[state][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX,
				(int) (hitbox.y - yDrawOffset), (int) width * flipW, (int) height, null);

		drawAttackBox(g, lvlOffset);
		drawHitbox(g, lvlOffset);
		drawUI(g);
	}

	private void drawUI(Graphics g) {
		// status bar
		g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);

		// health bar
		g.setColor(Color.RED);
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);

		// powerbar
		g.setColor(Color.yellow);
		g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	// Trong github == updateAnimationTick
	private void updateAnimation() {
		aniTick++;
		if (aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(state)) {
				aniIndex = 0;
				attacking = false;
				attack_jump = false;
				attackChecked = false;
				hit = false;
			}
		}

	}

	private void setAnimation() {

		int startAni = state;

		if (moving) {
			state = RUNNING;
		} else {
			state = IDLE;
		}

		if (inAir) {
			if (airSpeed < 0) {
				state = JUMPING;
			} else
				state = FALLING;
		}

		if (powerAttackActive) {
			state = DASH;
			aniIndex = 1;
			aniTick = 0;
			return;
		}

		if (inAir) {
			if (attack_jump) {
				state = ATTACK_JUMP;
			}
		} else {
			if (attacking) {
				state = ATTACK;
				if (startAni != ATTACK) {
					aniIndex = 1;
					aniTick = 0;
					return;
				}
			}
		}

		if (startAni != state) {
			resetAnimation();
		}
	}

	public void resetAnimation() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump) {
			jump();
		}

		if (!inAir) {
			if (!powerAttackActive) {
				if ((!left && !right) || (left && right)) {
					return;
				}
			}
		}

		float xSpeed = 0;

		if (left & !right) {
			xSpeed -= walkSpeed;
			flipX = width;
			flipW = -1;
			hitboxOffset = 1;
		}
		if (right & !left) {
			xSpeed += walkSpeed;
			flipX = 0;
			flipW = 1;
			hitboxOffset = 0;
		}

		if (powerAttackActive) {
			if ((!left && !right) || (left && right)) {
				if (flipW == -1) {
					xSpeed = -walkSpeed;
				} else
					xSpeed = walkSpeed;

			}
			xSpeed *= 3;

		}

		if (!inAir) {
			if (!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			}
		}

		if (inAir && !powerAttackActive) {

			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);

				if (airSpeed > 0) {
					resetInAir();
				} else {
					airSpeed = fallSpeedAfterCollision;
				}
				updateXPos(xSpeed);
			}
		} else {
			updateXPos(xSpeed);
		}
		moving = true;

	}

	private void jump() {
		if (inAir) {
			return;
		}
		playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
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

	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(xSpeed + hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
			moving = true;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
			if (powerAttackActive) {
				powerAttackActive = false;
				powerAttackTick = 0;
			}
		}
	}

	private void loadAni() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[10][5];
		for (int i = 0; i < animations.length; i++) {
			for (int j = 0; j < animations[i].length; j++) {
				animations[i][j] = img.getSubimage(64 * j, 40 * i, 64, 40);
			}
		}
		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
	}

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
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

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public boolean isInAir() {
		return inAir;
	}

	public boolean isAttack_jump() {
		return attack_jump;
	}

	public void setAttack_jump(boolean attack_jump) {
		this.attack_jump = attack_jump;
	}

	public void windowFocusLost() {
		left = false;
		right = false;

	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		attack_jump = false;
		jump = false;
		state = IDLE;
		currentHealth = maxHealth;
		powerValue = powerMaxValue;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}

	}

	public void kill() {
		currentHealth = 0;
	}

	public int getTileY() {
		return tileY;
	}

	public void powerAttack() {
		if (powerAttackActive) {
			return;
		}
		if (powerValue >= 60) {
			powerAttackActive = true;
			changePower(-60);
		}

	}

}
