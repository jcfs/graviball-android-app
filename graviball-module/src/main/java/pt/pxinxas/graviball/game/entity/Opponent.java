package pt.pxinxas.graviball.game.entity;

import org.andengine.opengl.texture.region.ITextureRegion;

import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.entity.enumerator.RoundStatus;
import pt.pxinxas.graviball.game.holder.RandomHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import pt.pxinxas.graviball.game.scene.BaseLeagueScene;
import pt.pxinxas.graviball.game.scene.CampaignLeagueScene;
import pt.pxinxas.graviball.game.scene.SurvivalLeagueScene;

/**
 * @author JSalavisa
 * 
 */
public class Opponent extends FallingEntity {
	// Points in the currentLeague
	private Integer currentLeaguePoints;
	private float delay = 0;

	public Opponent(int i) {
		this(0, 0, i);
	}

	public Opponent(float pX, float pY, int i) {
		super(pX, pY, TexturesHolder.getOpponentTextures().get(i).deepCopy());
		this.setHealth(100);
	}

	public Opponent(float pX, float pY, ITextureRegion kingTexture) {
		super(pX, pY, kingTexture);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.getStatus() != PlayerStatus.FINISHED && this.getStatus() != PlayerStatus.BOUNCING) {
			if (openChute()) {
				this.setStatus(PlayerStatus.SLOWING);
			}
			if (this.getStatus() == PlayerStatus.STOPPED && this.getCurrentRoundScene().getStatus() == RoundStatus.RUNNING) {
				delay += pSecondsElapsed;
				if (startFalling(getCurrentRoundScene().getLeagueScene(), delay)) {
					this.setStatus(PlayerStatus.ACCEL);
					GameState.getInstance().addReactionTime(this, delay);
				}
			}
		} else {
			delay = 0;
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	protected boolean openChute() {
		return this.getY() > (Constants.LEAGUE_DEPTH - (Constants.LEAGUE_DEPTH / 3) + RandomHolder.getInstance().getNextInt(100));
	}

	protected boolean startFalling(BaseLeagueScene leagueScene, double latency) {
		int leagueLevel = 0;
		if (leagueScene == null) {
			leagueLevel = 5;
		} else if (leagueScene instanceof SurvivalLeagueScene) {
			leagueLevel = 4;
		} else if (leagueScene instanceof CampaignLeagueScene) {
			leagueLevel = leagueScene.getLeagueLevel();
		}

		double offset = Constants.LEAGUE_OFFSTES[leagueLevel - 1];
		offset += (RandomHolder.getInstance().getNextFloat());
		return latency >= offset;
	}

	public Integer getCurrentLeaguePoints() {
		return currentLeaguePoints;
	}

	public void setCurrentLeaguePoints(Integer currentLeaguePoints) {
		this.currentLeaguePoints = currentLeaguePoints;
	}

}
