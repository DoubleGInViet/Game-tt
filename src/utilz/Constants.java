package utilz;



public class Constants {

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
