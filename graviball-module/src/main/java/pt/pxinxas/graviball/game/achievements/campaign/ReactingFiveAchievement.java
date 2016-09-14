package pt.pxinxas.graviball.game.achievements.campaign;

import java.util.Collections;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.EntityType;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;

public class ReactingFiveAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getCurrentLeague().getLeagueLevel() == 5) {
			Collections.sort(state.getReactionTimes());
			if (state.getReactionTimes().get(0).getType() != EntityType.PLAYER) {
				return GameStateStatus.INVALIDATE;
			}
			if (isGameWon(state)) {
				return GameStateStatus.VALIDATE;
			}
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "Reacting Five";
	}

	@Override
	public String getAchievementDesc() {
		return "Be the first to react in every round of league #5 and win the league";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_REACTING_FIVE_ID;
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
