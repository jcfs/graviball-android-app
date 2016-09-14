package pt.pxinxas.graviball.game.achievements;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.IEntity;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.Player;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.scene.BaseLeagueScene;
import pt.pxinxas.graviball.game.scene.BaseRoundScene;

/**
 * @author JSalavisa
 * 
 */
public class GameState {

	private static final GameState instance = new GameState();

	private BaseLeagueScene currentLeague;
	private BaseRoundScene currentRound;
	// League wide false start
	private Boolean falseStart = false;
	// Round wide reaction time
	private List<ReactionTime> reactionTimes;

	public void resetGameState() {
		this.setCurrentLeague(null);
		this.setCurrentRound(null);
		this.reactionTimes = null;
		this.falseStart = false;
	}

	public void resetReactionTimes() {
		this.reactionTimes = null;
	}

	public void addReactionTime(IEntity entity, double reactionTime) {
		if (reactionTimes == null) {
			reactionTimes = new ArrayList<ReactionTime>();
		}

		ReactionTime rt = new ReactionTime();
		rt.setReactionTime(reactionTime);
		if (entity.equals(EntityHolder.getPlayer())) {
			rt.setType(EntityType.PLAYER);
		} else {
			rt.setType(EntityType.OPPONENT);
		}
		reactionTimes.add(rt);
	}

	public boolean isFightingKing() {
		return this.getCurrentLeague().getLeagueLevel() == Constants.MAX_LEAGUES_PER_GAME;
	}

	public BaseLeagueScene getCurrentLeague() {
		return currentLeague;
	}

	public BaseRoundScene getCurrentRound() {
		return currentRound;
	}

	public Player getPlayer() {
		return EntityHolder.getPlayer();
	}

	public List<ReactionTime> getReactionTimes() {
		return reactionTimes;
	}

	public void setReactionTimes(List<ReactionTime> reactionTimes) {
		this.reactionTimes = reactionTimes;
	}

	public static GameState getInstance() {
		return instance;
	}

	public void setCurrentRound(BaseRoundScene currentRound) {
		this.currentRound = currentRound;
	}

	public void setCurrentLeague(BaseLeagueScene currentLeague) {
		this.currentLeague = currentLeague;
	}

	public Boolean getFalseStart() {
		return falseStart;
	}

	public void setFalseStart(Boolean falseStart) {
		this.falseStart = falseStart;
	}

}
