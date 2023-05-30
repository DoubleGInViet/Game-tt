package entities;

import static utilz.Constants.Enemy.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.*;

import java.awt.geom.Rectangle2D;


import static utilz.Constants.Directions.*;

import main.Game;
public abstract class Enemy extends Entity {
	
	protected int enemyType;
	protected boolean firstUpdate = true;

	protected int walkDir = LEFT;
	protected int tileY;
	protected int attackDistance = (int) (28 * Game.SCALE);
	protected boolean active = true;
	protected boolean attackChecked;
	
	
	public Enemy(float x, float y, int width, int height, int enemyType) {
		super(x, y, width, height);
		this.enemyType = enemyType;
		maxHealth = GetMaxHealth(enemyType);
		currentHealth = maxHealth;
		walkSpeed = Game.SCALE * 0.4f;
	}
	
	
	protected void firstUpdateCheck(int[][] lvlData) {
		if( !IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
		firstUpdate = false;
	}
	
	
	protected void updateInAir(int[][] lvlData) {
		if( CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
			hitbox.y += airSpeed;
			airSpeed += GRAVITY;
		}
		else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
			tileY = (int)( hitbox.y / Game.TILE_SIZE);
		}
	}
	
	
	protected void updateAnimationTick() {
		aniTick++;
		if( aniTick >= ANI_SPEED) {
			aniTick = 0;
			aniIndex++;
			if( aniIndex >= GetSpriteAmount(enemyType, state) ) {
				aniIndex =0;
				switch ( state) {
				case ATTACK, HIT -> state =IDLE;
				case DEAD -> active = false;
				}
			}
		}
	}

	
	protected void move(int[][] lvlData) {
		float xSpeed = 0;
		if( walkDir == LEFT) {
			xSpeed = -walkSpeed;
		}
		else {
			xSpeed = walkSpeed;
		}
		if( CanMoveHere(hitbox.x + xSpeed , hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			if( IsFloor( hitbox, xSpeed , lvlData)) {
				hitbox.x += xSpeed;
				return;
			}
		}

		changeWalkDir();
	}
	
	
	protected boolean canSeePlayer(int[][] lvlData, Player player) {
		int playerTileY = (int)( player.getHitbox().y / Game.TILE_SIZE);
		if( playerTileY == tileY ) {
			if( isPlayerInRange( player)) {
				if( IsSightClear(lvlData, hitbox, player.hitbox, tileY)) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	protected void turnTowardsPlayer( Player player) {
		if( player.hitbox.x > hitbox.x ) {
			walkDir = RIGHT;
		}
		else walkDir = LEFT;
	}


	private boolean isPlayerInRange(Player player) {
		int absValue = (int)( Math.abs(player.hitbox.x - hitbox.x));
		return absValue <= attackDistance * 5;
	}
	
	
	protected boolean isPLayerCloseForAttack( Player player) {
		int absValue = (int)( Math.abs(player.hitbox.x - hitbox.x));
		return absValue < attackDistance;
	}


	protected void newState(int enemyState) {
		this.state = enemyState;
		aniTick = 0;
		aniIndex=0;	
		
	}
	
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if( currentHealth <= 0) {
			newState(DEAD);
		}
		else newState(HIT);
	}
	

	public void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if( attackBox.intersects(player.hitbox)) {
			player.changeHealth(-GetEnemyDmg(enemyType));
			player.setHit(true);
		}
		attackChecked = true;
		
	}
	
	public void resetEnemy() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(IDLE);
		active = true;
		airSpeed = 0;
	}
	
	protected void changeWalkDir() {
		if( walkDir == LEFT) {
			walkDir = RIGHT;
		}
		else walkDir = LEFT;
		
	}

	public int getState() {
		return state;
	}
	
	public boolean isActive() {
		return active;
	}
}
