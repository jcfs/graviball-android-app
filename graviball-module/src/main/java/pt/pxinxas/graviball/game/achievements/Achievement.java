package pt.pxinxas.graviball.game.achievements;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.scene.BaseLeagueScene;

/**
 * @author jpmm
 * 
 */
public abstract class Achievement {

	protected AchievementStatus status;

	public void validateAchievement(GameState state) {
		GameStateStatus gameStateStatus = validate(state);

		if (gameStateStatus == GameStateStatus.VALIDATE) {
			this.setStatus(AchievementStatus.ACHIEVED);
		} else if (gameStateStatus == GameStateStatus.INVALIDATE) {
			this.setStatus(AchievementStatus.NOTRUNNING);
		}
	}

	protected abstract GameStateStatus validate(GameState state);

	public abstract void initAchievement();

	public abstract String getAchievementName();

	public abstract String getAchievementDesc();

	public abstract Integer getAchievementId();

	public abstract AchievementDifficulty getAchievementDifficulty();
	
	public AchievementType getAchievementType() {
		return AchievementType.REGULAR;
	}

	protected boolean isGameWon(GameState state) {
		BaseLeagueScene league = state.getCurrentLeague();
		if (league.getLeagueLevel() == Constants.MAX_LEAGUES_PER_GAME) {
			if (league.getLeagueScore().isBOOver()) {
				if (state.getPlayer().getCurrentRoundPosition() == 1) {
					return true;
				}
			}
		}
		return false;
	}

	protected void stop() {
		this.status = AchievementStatus.NOTRUNNING;
	}
	
	public AchievementStatus getStatus() {
		return status;
	}

	public void setStatus(AchievementStatus status) {
		this.status = status;
	}
}
