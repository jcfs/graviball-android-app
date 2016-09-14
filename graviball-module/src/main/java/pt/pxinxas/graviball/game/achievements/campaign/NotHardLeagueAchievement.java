package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;

public class NotHardLeagueAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getCurrentLeague().getLeagueLevel() == 3) {
			if (state.getCurrentRound().getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE) {
				if (state.getCurrentLeague().getPlayerCurrentPosition() == 1) {
					return GameStateStatus.VALIDATE;
				}
			}
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "That was\nnot hard";
	}

	@Override
	public String getAchievementDesc() {
		return "Beat league #3";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_NOT_HARD_LEAGUE_ID;
	}

	@Override
	public void initAchievement() {
		this.setStatus(AchievementStatus.RUNNING);
	}

	@Override
	public AchievementDifficulty getAchievementDifficulty() {
		return AchievementDifficulty.EASY;
	}

}
