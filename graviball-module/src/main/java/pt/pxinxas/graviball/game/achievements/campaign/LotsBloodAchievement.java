package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;

public class LotsBloodAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (isGameWon(state) && this.status == AchievementStatus.RUNNING) {
			if (state.getPlayer().getHealth() <= 10) {
				return GameStateStatus.VALIDATE;
			}
		}

		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "Lots of blood";
	}

	@Override
	public String getAchievementDesc() {
		return "Win the game with less than 10% health";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_LOTS_OF_BLOOD_ID;
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
