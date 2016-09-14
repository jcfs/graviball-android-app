package pt.pxinxas.graviball.game.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.conf.OpponentNames;
import pt.pxinxas.graviball.game.conf.TextConstants;
import pt.pxinxas.graviball.game.entity.HealthBar;
import pt.pxinxas.graviball.game.entity.NextButton;
import pt.pxinxas.graviball.game.entity.Opponent;
import pt.pxinxas.graviball.game.entity.enumerator.LeagueStatus;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.entity.enumerator.RoundStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import pt.pxinxas.graviball.game.scores.LeagueScore;

public class SurvivalLeagueScene extends BaseLeagueScene {
	private RoundScene currentRound;
	private NextButton nextButton;
	private HealthBar healthBar;
	private Sprite diamond;
	private Text gold;

	public SurvivalLeagueScene(int leagueLevel) {
		super(leagueLevel);
	}

	@Override
	public void init() {
		EntityHolder.resetCamera();
		this.detachChildren();
		this.unregisterTouchArea(nextButton);

		createOpponents(this.getLeagueLevel());
		this.setStatus(LeagueStatus.STOPPED);
		this.setLeagueScore(new LeagueScore());
		this.currentRound = null;
		this.leagueName = new Text(0, 0, TexturesHolder.getFont(), TextConstants.LEAGUE + this.getLeagueLevel(), 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		this.leagueName.setPosition(Constants.SCREEN_WIDTH/2 - this.leagueName.getWidth()/2, 40);
		
		this.attachChild(leagueName);
		this.attachChild(new Sprite(0, 0, TexturesHolder.getLeagueBackground(), EntityHolder.getEngine().getVertexBufferObjectManager()));

		initLayout();
	}

	private void initLayout() {
		playerText = new Text(0, 0, TexturesHolder.getFontMini(), "", 10, EntityHolder.getEngine().getVertexBufferObjectManager());
		playerText.setPosition(50, EntityHolder.getmCamera().getCenterY() - 200);
		playerText.setText(EntityHolder.getPlayer().getPlayerData());
		playerText.setColor(0f,0f,0f);
		healthBar = new HealthBar(playerText.getWidth() + 80, EntityHolder.getmCamera().getCenterY() - 260);
		healthBar.attachToScene(this);
		this.diamond = new Sprite(playerText.getWidth() + 80, EntityHolder.getmCamera().getCenterY() - 180, TexturesHolder.getDiamond(), EntityHolder.getEngine()
				.getVertexBufferObjectManager());
		this.gold = new Text(playerText.getWidth() + 80 + this.diamond.getWidth(), EntityHolder.getmCamera().getCenterY() - 170, TexturesHolder.getFontMini(), "", 20, EntityHolder
				.getEngine().getVertexBufferObjectManager());

		this.gold.setColor(0f,0f,0f);
		this.diamond.setScale(1.5f);
		this.attachChild(playerText);

		nextButton = new NextButton(40, Constants.SCREEN_HEIGHT - 120, TexturesHolder.getNextButton(), EntityHolder.getEngine().getVertexBufferObjectManager());
		printInfo();
		this.registerTouchArea(nextButton);
		this.setTouchAreaBindingOnActionDownEnabled(true);
		this.attachChild(diamond);
		this.attachChild(gold);
		this.attachChild(nextButton);
	}

	private void createOpponents(int leagueLevel) {
		this.opponents = new ArrayList<Opponent>();
		Collections.shuffle(OpponentNames.nameList);
		Collections.shuffle(TexturesHolder.getOpponentTextures());
		for (int i = 0; i < Constants.OPPONENTS_COUNT; i++) {
			Opponent opponent = new Opponent(i);
			opponent.setStatus(PlayerStatus.STOPPED);
			opponent.setName(OpponentNames.nameList.get(i));
			opponent.setScale(0.7f);
			opponents.add(opponent);
		}
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.getStatus() == LeagueStatus.READY) {
			if (this.currentRound == null) {
				System.gc();
				RoundScene roundScene = new RoundScene(this, this.opponents);
				this.currentRound = roundScene;
				EntityHolder.getEngine().setScene(roundScene);
			} else if (this.currentRound.getStatus() == RoundStatus.OVER) {
				this.setStatus(LeagueStatus.OVER);
				this.detachChildren();
				this.detachSelf();
			}
		}
		if (this.getStatus() == LeagueStatus.STOPPED || this.getStatus() == LeagueStatus.FINISHED) {
			healthBar.updateBar();
			playerText.setText(EntityHolder.getPlayer().getPlayerData());
			gold.setText(String.valueOf(EntityHolder.getPlayer().getGold()));
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	public List<Opponent> getOpponents() {
		return opponents;
	}

	public void setOpponents(List<Opponent> opponents) {
		this.opponents = opponents;
	}

	public RoundScene getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(RoundScene currentRound) {
		this.currentRound = currentRound;
	}

}
