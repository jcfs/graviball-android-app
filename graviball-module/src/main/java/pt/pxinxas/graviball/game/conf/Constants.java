package pt.pxinxas.graviball.game.conf;

/**
 * @author jpmm
 * 
 */
public class Constants {
	/* Text pool chunks */
	public static final int POOL_CHUNK = 10;
	/*
	 * Screen Constants
	 */
	public static int SCREEN_WIDTH = 480;
	public static int SCREEN_HEIGHT = 800;
	/*
	 * Physics Constants
	 */
	public static final float FALL_ACCELERATION = 5000.0f;
	public static final float FALL_DECCELERATION = -1200.0f;
	public static final float FALL_MAX_VELOCITY = 1000;
	public static final float SHUTE_MAX_VELOCITY = 300;
	public static final float NO_ACCELERATION = 0;
	public static final float NO_VELOTICY = 0;
	public static final float SLOW_SPEED_THRESHOLD = 350f;
	/*
	 * League constants
	 */
	public static final int FPS_CAP = 60;
	public static final int OPPONENTS_COUNT = 3;
	public static final int MAX_LEAGUES_PER_GAME = 5;
	public static final int MAX_PLAYER_PER_ROUND = 4;
	public static final int MAX_ROUNDS_PER_LEAGUE = 3;
	public static final int LEAGUE_DEPTH = 1800;
	public static final double[] LEAGUE_OFFSTES = { 0.4f, 0.3f, 0.2f, 0.1f, 0f };
	/*
	 * MedKit constants
	 */
	public static final int MEDKIT_COST = 50;
	public static final int MEDKIT_HEALTH_INCREASE = 10;
	/*
	 * Round constants
	 */
	public static final int INITIAL_HEIGHT = 100;
	public static final int[] INITIAL_WIDTH = { 40, 140, 240, 340 };
	public static final int[] GOLD_PER_PLACE = { 50, 25, 10, 0 };

}
