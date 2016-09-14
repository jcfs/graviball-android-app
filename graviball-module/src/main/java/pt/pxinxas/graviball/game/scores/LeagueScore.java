package pt.pxinxas.graviball.game.scores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.andengine.entity.IEntity;

import pt.pxinxas.graviball.game.entity.FallingEntity;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.scene.KingRoundScene;

public class LeagueScore {

	private List<Score> scores;

	public LeagueScore() {
		this.setScores(new ArrayList<Score>());
	}

	public void clearScore() {
		this.setScores(new ArrayList<Score>());
	}

	public void addEntityScore(IEntity entity, int position) {
		boolean flag = true;

		if (((FallingEntity) entity).getCurrentRoundScene() instanceof KingRoundScene) {
			for (Score score : scores) {
				if (score.getPlayer() == entity) {
					score.sumScoreKing(position);
					flag = false;
				}
			}

			if (flag) {
				Score score = new Score();
				score.setPlayer(entity);
				score.sumScoreKing(position);
				scores.add(score);
			}
		} else {
			for (Score score : scores) {
				if (score.getPlayer() == entity) {
					score.sumScore(position);
					flag = false;
				}
			}

			if (flag) {
				Score score = new Score();
				score.setPlayer(entity);
				score.sumScore(position);
				scores.add(score);
			}
		}

	}

	public int getMainPlayerPosition() {
		int position = 0;
		Collections.sort(scores);
		for (Score score : scores) {
			position++;
			if (score.getPlayer() == EntityHolder.getPlayer()) {
				break;
			}
		}
		return position;
	}

	public boolean isBOOver() {
		for (Score score : scores) {
			if (score.getScore() == 3)
				return true;
		}
		return false;
	}

	public int getEntityScore(IEntity entity) {
		for (Score score : scores) {
			if (score.getPlayer().equals(entity)) {
				return (int) score.getScore();
			}
		}
		return -1;
	}

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

}
