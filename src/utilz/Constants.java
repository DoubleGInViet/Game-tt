package utilz;

import main.Game;

public class Constants {

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
		
		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case IDLE: 
				return 2;	
			
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
