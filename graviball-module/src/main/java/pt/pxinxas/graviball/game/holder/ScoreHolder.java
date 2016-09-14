package pt.pxinxas.graviball.game.holder;

public class ScoreHolder {

	private static int currentPosition = 0;

	public static synchronized void init() {
		ScoreHolder.currentPosition = 0;
	}

	public static synchronized int arrived() {
		return ++ScoreHolder.currentPosition;
	}

	public static synchronized int getCurrentPosition() {
		return currentPosition;
	}

	public static synchronized void setCurrentPosition(int currentPosition) {
		ScoreHolder.currentPosition = currentPosition;
	}

}
