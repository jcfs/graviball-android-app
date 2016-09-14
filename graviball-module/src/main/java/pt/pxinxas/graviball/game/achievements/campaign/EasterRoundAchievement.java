package pt.pxinxas.graviball.game.achievements.campaign;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementConstants;
import pt.pxinxas.graviball.game.achievements.AchievementDifficulty;
import pt.pxinxas.graviball.game.achievements.AchievementStatus;
import pt.pxinxas.graviball.game.achievements.AchievementType;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.achievements.GameStateStatus;
import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.scene.EasterEggRoundScene;

public class EasterRoundAchievement extends Achievement {

	@Override
	protected GameStateStatus validate(GameState state) {
		if (state.getCurrentRound() instanceof EasterEggRoundScene && state.getCurrentRound().getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE
				&& ((EasterEggRoundScene)state.getCurrentRound()).getLeagueScore().getMainPlayerPosition() == 1) {
			return GameStateStatus.VALIDATE;
		}
		return GameStateStatus.KEEPGOING;
	}

	@Override
	public String getAchievementName() {
		return "The Hidden one";
	}

	@Override
	public String getAchievementDesc() {
		return "Win the Hidden Round";
	}

	@Override
	public Integer getAchievementId() {
		return AchievementConstants.ACHIEVE_EASTER_ROUND_LEAGUE_ID;
	}

	@Override
	public void initAchievement() {
		this.setStatus(AchievementStatus.RUNNING);
	}

	@Override
	public AchievementDifficulty getAchievementDifficulty() {
		return AchievementDifficulty.MEDIUM;
	}

	@Override
	public AchievementType getAchievementType() {
		return AchievementType.HIDDEN;
	}

}
