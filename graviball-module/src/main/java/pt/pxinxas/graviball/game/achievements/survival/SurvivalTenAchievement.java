package pt.pxinxas.graviball.game.achievements.survival;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;

public class SurvivalTenAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getCurrentLeague().getLeagueLevel() == 9 && state.getCurrentRound().getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE 
				&& state.getCurrentLeague().getPlayerCurrentPosition() == 1) {
			return GameStateStatus.VALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public void initAchievement() {
		this.setStatus(AchievementStatus.RUNNING);
	}

	@Override
	public String getAchievementName() {
		return "Survival 10";
	}

	@Override
	public String getAchievementDesc() {
		return "Reach league 10 in a survival game";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_SURVIVAL_10_ID;
	}

	@Override
	public AchievementDifficulty getAchievementDifficulty() {
		return AchievementDifficulty.EASY;
	}

}
