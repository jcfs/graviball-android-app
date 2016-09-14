package pt.pxinxas.graviball.game.scores;

import java.math.BigDecimal;

import org.andengine.entity.IEntity;

public class Score implements Comparable<Score> {

	private static final int[] WEIGHTS = { 10, 7, 4, 0 };
	private static final int[] WEIGHTS_KING = { 1, 0 };
	private IEntity player;
	private double score = 0;

	public IEntity getPlayer() {
		return player;
	}

	public void setPlayer(IEntity player) {
		this.player = player;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void sumScore(int position) {
		this.score += WEIGHTS[position - 1];
	}

	public void sumScoreKing(int position) {
		this.score += WEIGHTS_KING[position - 1];
	}

	@Override
	public int compareTo(Score o) {
		BigDecimal oScore = new BigDecimal(o.getScore());
		BigDecimal thisScore = new BigDecimal(this.getScore());
		return oScore.compareTo(thisScore);
	}

}
