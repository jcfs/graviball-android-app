package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;

public class NeverDropAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (isGameWon(state) && this.getStatus() == AchievementStatus.RUNNING) {
			return GameStateStatus.VALIDATE;
		}
		if (state.getCurrentLeague().getLeagueLevel() > 1 && state.getCurrentRound().getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE
				&& state.getCurrentLeague().getPlayerCurrentPosition() == 4) {
			return GameStateStatus.INVALIDATE;
		}

		if (state.getCurrentLeague().getLeagueLevel() == Constants.MAX_LEAGUES_PER_GAME && state.getCurrentLeague().getLeagueScore().isBOOver()
				&& state.getCurrentLeague().getPlayerCurrentPosition() == 2) {
			stop();
			return GameStateStatus.INVALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "Never Drop";
	}

	@Override
	public String getAchievementDesc() {
		return "Win the game without dropping a league";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_NEVER_DROP_ID;
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
