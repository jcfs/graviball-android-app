package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;

public class FirstForeverAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getPlayer().getCurrentRoundPosition() != 1) {
			return GameStateStatus.INVALIDATE;
		}
		if (isGameWon(state)) {
			return GameStateStatus.VALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "First Forever";
	}

	@Override
	public String getAchievementDesc() {
		return "Be the first to arrive in every round of the game";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_FIRST_FOREVER_ID;
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
