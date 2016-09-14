package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;

public class WrongTimingAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getCurrentLeague().getLeagueLevel() != Constants.MAX_LEAGUES_PER_GAME
				&& state.getCurrentRound().getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE) {
			if (!state.getFalseStart()) {
				return GameStateStatus.INVALIDATE;
			}
		}
		if (isGameWon(state) && state.getFalseStart()) {
			return GameStateStatus.VALIDATE;
		} else if (isGameWon(state)) {
			return GameStateStatus.INVALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "Wrong Timing";
	}

	@Override
	public String getAchievementDesc() {
		return "Win the game with at least one false start per league";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_WRONG_TIMING_ID;
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
