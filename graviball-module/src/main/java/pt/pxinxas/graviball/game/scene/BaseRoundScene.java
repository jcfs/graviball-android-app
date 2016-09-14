package pt.pxinxas.graviball.game.scene;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.AchievementManager;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.Opponent;
import pt.pxinxas.graviball.game.entity.RoundBackGround;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.entity.enumerator.RoundStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.RandomHolder;
import pt.pxinxas.graviball.game.holder.TextHolder;

public class BaseRoundScene extends Scene {
	protected BaseLeagueScene leagueScene;
	protected RoundStatus status;
	protected List<Opponent> opponents;
	protected Text countdown;
	protected Text falseStart;
	protected Text promotionText;
	protected float delay;
	protected float playerDelay;
	protected int roundIndex;
	protected Integer currentInt;

	protected void addBackground(RoundBackGround leagueBackGround) {
		this.setBackground(leagueBackGround.getAutoParallaxBackground());
		Sprite backgroundSprites = leagueBackGround.getBackgroundSprites();
		backgroundSprites.setZIndex(-2);
		backgroundSprites.setHeight(Constants.LEAGUE_DEPTH);
		this.attachChild(backgroundSprites);
	}

	protected void handleCountdown(float elapsedTime) {
		if (currentInt == null) {
			currentInt = RandomHolder.getInstance().getNextInt(4);
		}
		if (this.getStatus() == RoundStatus.STOPPED) {
			if (elapsedTime < 1.5) {
				countdown.setText("Round " + getRoundIndex());
				countdown.setVisible(true);
			} else if (elapsedTime < 2.5) {
				countdown.detachSelf();
				countdown = TextHolder.getInstance().COUNTDOWN_READY;
				countdown.setVisible(true);
			} else if (elapsedTime < 3.8) {
				countdown.detachSelf();
				countdown = TextHolder.getInstance().COUNTDOWN_SET;
				countdown.setVisible(true);
			} else if (elapsedTime > 4.3f + currentInt) {
				countdown.detachSelf();
				countdown = TextHolder.getInstance().COUNTDOWN_GO;
				countdown.setVisible(true);
				this.setStatus(RoundStatus.RUNNING);
				currentInt = null;
			}
			countdown.setPosition(Constants.SCREEN_WIDTH / 2 - countdown.getWidth() / 2, Constants.SCREEN_HEIGHT / 2);
			countdown.detachSelf();
			this.attachChild(countdown);
		}
	}

	protected List<Achievement> verifyAchievements() {
		GameState.getInstance().setCurrentLeague(this.getLeagueScene());
		GameState.getInstance().setCurrentRound(this);
		List<Achievement> checkAchievements = AchievementManager.getInstance().checkAchievements(GameState.getInstance());
		GameState.getInstance().resetReactionTimes();
		return checkAchievements;
	}

	protected boolean isRoundFinished() {
		for (Opponent opponent : opponents) {
			if (opponent.getStatus() != PlayerStatus.FINISHED) {
				return false;
			}
		}
		return (EntityHolder.getPlayer().getStatus() == PlayerStatus.FALSE_START || EntityHolder.getPlayer().getStatus() == PlayerStatus.DEAD || EntityHolder
				.getPlayer().getStatus() == PlayerStatus.FINISHED);
	}

	public BaseLeagueScene getLeagueScene() {
		return leagueScene;
	}

	public void setLeagueScene(BaseLeagueScene leagueScene) {
		this.leagueScene = leagueScene;
	}

	public RoundStatus getStatus() {
		return status;
	}

	public void setStatus(RoundStatus status) {
		this.status = status;
	}

	public int getRoundIndex() {
		return roundIndex;
	}

	public void setRoundIndex(int roundIndex) {
		this.roundIndex = roundIndex;
	}
}
