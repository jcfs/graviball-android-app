package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;

public class NoBloodAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getCurrentLeague().getLeagueLevel() < Constants.MAX_LEAGUES_PER_GAME && state.getPlayer().getHealth() < 100) {
			return GameStateStatus.INVALIDATE;
		}

		if (state.getCurrentLeague().getLeagueLevel() == Constants.MAX_LEAGUES_PER_GAME - 1
				&& state.getCurrentRound().getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE
				&& state.getCurrentLeague().getPlayerCurrentPosition() == 1 && this.status == AchievementStatus.RUNNING) {
			return GameStateStatus.VALIDATE;
		}

		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "There will\nbe no blood";
	}

	@Override
	public String getAchievementDesc() {
		return "Reach the King without losing any health up to him";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_NO_BLOOD_ID;
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
