package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;

public class FirstTwoAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getCurrentLeague().getLeagueLevel() == 2) {
			if (state.getPlayer().getCurrentRoundPosition() != 1) {
				return GameStateStatus.INVALIDATE;
			}
			if (state.getCurrentRound().getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE
					&& state.getCurrentLeague().getPlayerCurrentPosition() == 1) {
				return GameStateStatus.VALIDATE;
			}
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "First Two";
	}

	@Override
	public String getAchievementDesc() {
		return "Be the first to arrive in every round of league #2 and win the league";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_FIRST_TWO_ID;
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
