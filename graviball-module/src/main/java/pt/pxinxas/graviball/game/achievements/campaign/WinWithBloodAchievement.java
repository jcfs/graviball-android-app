package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;

public class WinWithBloodAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.isFightingKing() && state.getPlayer().getHealth() > 20) {
			return GameStateStatus.INVALIDATE;
		}

		if (isGameWon(state)) {
			return GameStateStatus.VALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "Win with Blood";
	}

	@Override
	public String getAchievementDesc() {
		return "Reach the king with 20% or less health and win the game";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_WIN_WITH_BLOOD_ID;
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
