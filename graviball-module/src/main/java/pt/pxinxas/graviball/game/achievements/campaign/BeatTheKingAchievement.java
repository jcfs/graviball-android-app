package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;

public class BeatTheKingAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (isGameWon(state)) {
			return GameStateStatus.VALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "King Slayer";
	}

	@Override
	public String getAchievementDesc() {
		return "Win the game";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_BEAT_THE_KING_ID;
	}

	@Override
	public void initAchievement() {
		this.setStatus(AchievementStatus.RUNNING);
	}

	@Override
	public AchievementDifficulty getAchievementDifficulty() {
		return AchievementDifficulty.MEDIUM;
	}

}
