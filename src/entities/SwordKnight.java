package entities;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Enemy.*;



import java.awt.geom.Rectangle2D;



import main.Game;
public class SwordKnight extends Enemy {

	
	public SwordKnight(float x, float y) {
		super(x, y, SWORD_KNIGHT_WIDTH, SWORD_KNIGHT_HEIGHT, SWORD_KNIGHT);
		initHitbox(  11, 19);
		initAttackbox();
	}
	
	private void initAttackbox() {
		attackBox = new Rectangle2D.Float(x, y, (int)( 28 * Game.SCALE), (int)( 28 * Game.SCALE));		
		
	}

	public void update(int [][]lvlData, Player player) {
		updateBehavior(lvlData, player);
		updateAnimationTick();
		updateAttackBox();
	}
	
	
	private void updateAttackBox() {
		if ( walkDir == LEFT) {
			attackBox.x = hitbox.x - hitbox.width - (int)( 8 * Game.SCALE) ;
		}
		else if( walkDir == RIGHT) {
			attackBox.x = hitbox.x ;
		}
		attackBox.y = hitbox.y - ( int )( 10 * Game.SCALE);	
		
	}

	public void updateBehavior(int [][]lvlData, Player player) {
		if( firstUpdate ) {
			firstUpdateCheck(lvlData);			
		}
		if( inAir ) {
			updateInAir(lvlData);
		}
		else {
			switch( state) {
			case IDLE:
				state = RUNNING;
				break;
			case RUNNING:
				if( canSeePlayer(lvlData, player)) {
					turnTowardsPlayer(player);
					if ( isPLayerCloseForAttack(player)) {
					newState(ATTACK);
					}
				}
				
				move(lvlData);
				break;
			case ATTACK:
				if( aniIndex == 0) {
					attackChecked = false;
				}			
				if( aniIndex == 3 && !attackChecked) {
					checkPlayerHit(attackBox, player);
				}
				break;
			case HIT:
				break;
			}
		}
	}
	
	
	

	
	
	public int flipX() {
		if ( walkDir == LEFT ) {
			return width;
		}
		else if ( walkDir == RIGHT ) {
			return 0;
		}
		return 0;
	}
	
	
	public int flipW() {
		if (walkDir == LEFT ) {
			return -1;
		}
		else return 1;
	} 
	
}
