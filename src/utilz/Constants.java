package utilz;

import main.Game;

public class Constants {
	
	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED =25;
	
	public static class Projectiles{
		public static final int CANNON_BALL_DEFAULT_WIDTH = 15;
		public static final int CANNON_BALL_DEFAULT_HEIGHT = 15;
		
		public static final int CANNON_BALL_WIDTH = (int) (CANNON_BALL_DEFAULT_WIDTH * Game.SCALE);
		public static final int CANNON_BALL_HEIGHT = (int) (CANNON_BALL_DEFAULT_HEIGHT * Game.SCALE);
		
		public static final float SPEED = 0.75f * Game.SCALE;
	}
	
	public static class ObjectConstant{
		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;
		public static final int SPIKE = 4;
		public static final int CANNON_LEFT = 5;
		public static final int CANNON_RIGHT = 6;
		
		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 10;
		
		public static final int CONTAINER_WIDTH_DEFAULT = 40;
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;
		public static final int CONTAINER_WIDTH = (int) (CONTAINER_WIDTH_DEFAULT * Game.SCALE);
		public static final int CONTAINER_HEIGHT = (int) (CONTAINER_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int) ( POTION_WIDTH_DEFAULT * Game.SCALE);
		public static final int POTION_HEIGHT = (int) ( POTION_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 32;
		public static final int SPIKE_WIDTH = (int) (SPIKE_WIDTH_DEFAULT * Game.SCALE);
		public static final int SPIKE_HEIGHT = (int) (SPIKE_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int CANNON_WIDTH_DEFAULT = 40;
		public static final int CANNON_HEIGHT_DEFAULT = 26;
		public static final int CANNON_WIDTH = (int) (CANNON_WIDTH_DEFAULT * Game.SCALE);
		public static final int CANNON_HEIGHT = (int) (CANNON_HEIGHT_DEFAULT * Game.SCALE);
		
		public static int GetSpriteAmount( int object_type) {
			switch (object_type) {
			case RED_POTION, BLUE_POTION:
				return 7;
			case BARREL, BOX:
				return 8;
			case CANNON_LEFT, CANNON_RIGHT:
				return 7;
			}
			return 1;
		}
	}
	
	
	public static class Enemy {
		public static final int SWORD_KNIGHT = 0;
		
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;
		
		public static final int SWORD_KNIGHT_WIDTH_DEFAULT = 64;
		public static final int SWORD_KNIGHT_HEIGHT_DEFAULT = 40;
		
		public static final int SWORD_KNIGHT_WIDTH = (int) (SWORD_KNIGHT_WIDTH_DEFAULT * Game.SCALE);
		public static final int SWORD_KNIGHT_HEIGHT = (int) ( SWORD_KNIGHT_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int SWORD_KNIGHT_DRAWOFFSET_X = (int) ( 24 * Game.SCALE);
		public static final int SWORD_KNIGHT_DRAWOFFSET_Y = (int)( 14 * Game.SCALE);
		
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {
			switch(enemy_type) {
			case SWORD_KNIGHT:
				switch(enemy_state) {
				case IDLE:
					return 5;
				case RUNNING:
					return 8;
				case ATTACK:
					return 5;
				case HIT:
					return 2;
				case DEAD:
					return 4;
					
				}
			}
			return 0;
		}
		
		public static int GetMaxHealth( int enemy_type) {
			switch(enemy_type) {
			case SWORD_KNIGHT:
				return 20;
			default:
				return 1;
			}
			
		}
		
		public static int GetEnemyDmg( int enemy_type) {
			switch(enemy_type) {
			case SWORD_KNIGHT:
				return 15;
			default:
				return 1;
			}
		}
		
	}
	
	public static class Environment{
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
		public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
		public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;
		
		
		public static final int SMALL_CLOUD_WIDTH = (int) (SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int SMALL_CLOUD_HEIGHT = (int) (SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
	}
	
	public static class UI {
		public static final int B_WIDTH_DEFAULT = 140;
		public static final int B_HEIGHT_DEFAULT = 56;
		public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
		
		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
			
		}
		
		public static class URMButtons{
			public static final int URM_SIZE_DEFAULT = 56;
			public static final int URM_SIZE = (int)( URM_SIZE_DEFAULT * Game.SCALE);
		}
		
		public static class VolumeButtons{
			public static final int VOLUME_DEFAULT_WIDTH = 28; 
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
			
			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}
	
	
	
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	
	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int JUMPING = 2;
		public static final int FALLING = 3;
		public static final int HIT = 4;
		public static final int ATTACK = 5;
		public static final int ATTACK_JUMP = 6;
		public static final int BOW = 7;
		public static final int DASH = 8;
		public static final int DEAD = 9;
		
		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case IDLE: 
				return 2;	
			case DEAD:
				return 5;
			case RUNNING:
				return 4;
			case JUMPING, HIT, ATTACK, ATTACK_JUMP, BOW, DASH:
				return 3;
			default:
				return 1;			
			}
		}
	}
}
