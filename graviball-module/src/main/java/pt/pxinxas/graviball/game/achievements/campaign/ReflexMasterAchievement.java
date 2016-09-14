package pt.pxinxas.graviball.game.achievements.campaign;

import java.util.Collections;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.EntityType;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;

public class ReflexMasterAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		Collections.sort(state.getReactionTimes());
		if (state.getReactionTimes().get(0).getType() != EntityType.PLAYER) {
			return GameStateStatus.INVALIDATE;
		}
		if (isGameWon(state) && this.getStatus() == AchievementStatus.RUNNING) {
			return GameStateStatus.VALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "Reflex Master";
	}

	@Override
	public String getAchievementDesc() {
		return "Be the first to react in every round of the game";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_REFLEX_MASTER_ID;
	}

	@Override
	public void initAchievement() {
		this.setStatus(AchievementStatus.RUNNING);
	}

	@Override
	public AchievementDifficulty getAchievementDifficulty() {
		return AchievementDifficulty.HARD;
	}
}
