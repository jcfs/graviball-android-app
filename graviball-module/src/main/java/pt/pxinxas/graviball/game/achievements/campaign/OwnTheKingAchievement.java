package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;

public class OwnTheKingAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (isGameWon(state) && this.getStatus() == AchievementStatus.RUNNING) {
			return GameStateStatus.VALIDATE;
		}
		if (state.getCurrentLeague().getLeagueLevel() == Constants.MAX_LEAGUES_PER_GAME && state.getPlayer().getCurrentRoundPosition() == 2) {
			return GameStateStatus.INVALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "Own The King";
	}

	@Override
	public String getAchievementDesc() {
		return "Win the game winning 3 matches straight in the Best of 5 against The King the first time you reach him";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_OWN_THE_KING_ID;
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
