package pt.pxinxas.graviball.game.achievements.campaign;

import java.util.Collections;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.EntityType;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;

public class ReactingOneAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getCurrentLeague().getLeagueLevel() == 1) {
			Collections.sort(state.getReactionTimes());
			if (state.getReactionTimes().get(0).getType() != EntityType.PLAYER) {
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
		return "Reacting One";
	}

	@Override
	public String getAchievementDesc() {
		return "Be the first to react in every round of league #1 and win the league";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_REACTING_ONE_ID;
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
