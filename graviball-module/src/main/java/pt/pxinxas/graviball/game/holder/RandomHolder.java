package pt.pxinxas.graviball.game.holder;

import java.util.Random;

public class RandomHolder {

	private Random random;
	private static final RandomHolder instance = new RandomHolder();
	
	private RandomHolder() {
		random = new Random();
	}
	
	public float getNextFloat() {
		return random.nextFloat();
	}
	
	public int getNextInt() {
		return random.nextInt();
	}
	
	public int getNextInt(int i) {
		return random.nextInt(i);
	}
	
	public static RandomHolder getInstance() {
		return instance;
	}
	
	
}
