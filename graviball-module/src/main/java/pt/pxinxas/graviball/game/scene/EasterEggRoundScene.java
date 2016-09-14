package pt.pxinxas.graviball.game.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.Opponent;
import pt.pxinxas.graviball.game.entity.Player;
import pt.pxinxas.graviball.game.entity.RoundBackGround;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.entity.enumerator.RoundStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.ScoreHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import pt.pxinxas.graviball.game.scores.LeagueScore;
import android.view.MotionEvent;

public class EasterEggRoundScene extends BaseRoundScene {
	private ArrayList<Integer> positions = new ArrayList<Integer>();
	private LeagueScore leagueScore;

	public EasterEggRoundScene() {
		Player player = new Player(0, 0);
		EntityHolder.setPlayer(player);
		EntityHolder.getPlayer().setHealth(1);
		GameState.getInstance().setCurrentRound(this);
		setLeagueScore(new LeagueScore());
		positions.add(0);
		positions.add(1);
		positions.add(2);
		positions.add(3);
		createOpponents();
		initRound();
	}

	/**
	 * 
	 */
	private void initRound() {
		Collections.shuffle(positions);
		this.clearChildScene();
		this.detachChildren();

		ScoreHolder.init();
		this.setStatus(RoundStatus.STOPPED);
		this.delay = 0;
		this.playerDelay = 0;
		this.setRoundIndex(this.getRoundIndex() + 1);

		EntityHolder.resetPlayer(this);
		EntityHolder.resetCamera();
		EntityHolder.getPlayer().setPosition(Constants.INITIAL_WIDTH[positions.get(0)], Constants.INITIAL_HEIGHT);

		HudCameraScene.getInstance().init();
		attachChild(HudCameraScene.getInstance());
		attachEntities();
		initLayout();
		sortChildren();
		
	}

	private void createOpponents() {
		this.opponents = new ArrayList<Opponent>();
		for (int i = 0; i < Constants.OPPONENTS_COUNT; i++) {
			Opponent opponent = new Opponent(0, 0, TexturesHolder.getPlayerTexture().deepCopy());
			opponent.setStatus(PlayerStatus.STOPPED);
			opponent.setScale(0.7f);
			opponent.setName("Player");
			opponents.add(opponent);
		}
	}

	/**
	 * 
	 */
	private void attachEntities() {
		this.attachChild(EntityHolder.getPlayer());
		EntityHolder.getPlayer().setCurrentLeaguePoints(0);
		int i = 1;
		for (Opponent opponent : opponents) {
			opponent.setCurrentRoundScene(this);
			opponent.setCurrentLeaguePoints(0);
			opponent.setStatus(PlayerStatus.STOPPED);
			opponent.setPosition(Constants.INITIAL_WIDTH[positions.get(i++)], Constants.INITIAL_HEIGHT);
			opponent.clearEntityModifiers();
			this.attachChild(opponent);
		}
	}

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		BaseRoundScene cScene = this;
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if (cScene.getStatus() == RoundStatus.STOPPED) {
				EntityHolder.getPlayer().setStatus(PlayerStatus.FALSE_START);
				falseStart.setText("FALSE START!");
				falseStart.setPosition(Constants.SCREEN_WIDTH / 2 - falseStart.getWidth() / 2, (Constants.SCREEN_HEIGHT / 2) - 100);
				EntityHolder.getPlayer().setCurrentRoundPosition(4);
				return true;
			}
			if (cScene.getStatus() == RoundStatus.FINISHED) {
				if (cScene.getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE || EntityHolder.getPlayer().getStatus() == PlayerStatus.DEAD) {
					quitRound();
				} else {
					initRound();
				}
				return true;
			}
			if (EntityHolder.getPlayer().getStatus() == PlayerStatus.MOVING) {
				EntityHolder.getPlayer().setStatus(PlayerStatus.SLOWING);
			} else if (EntityHolder.getPlayer().getStatus() == PlayerStatus.STOPPED) {
				EntityHolder.getPlayer().setStatus(PlayerStatus.ACCEL);
			}
		}
		return true;
	}

	private void quitRound() {
		this.detachChildren();
		this.detachSelf();
		this.setStatus(RoundStatus.OVER);
		this.clearChildScene();
		EntityHolder.resetCamera();
		GameState.getInstance().resetGameState();
		EntityHolder.getEngine().setScene(EntityHolder.getGameMenuScene());
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.getStatus() != RoundStatus.OVER) {
			if (this.getStatus() == RoundStatus.RUNNING) {
				this.playerDelay += pSecondsElapsed;
			}
			delay += pSecondsElapsed;
			handleCountdown(delay);
			if (this.getStatus() != RoundStatus.FINISHED) {
				if (isRoundFinished()) {
					calculateScore();
					this.setStatus(RoundStatus.FINISHED);
					this.playerDelay = 0;
					List<Achievement> verifyAchievements = verifyAchievements();
					if (verifyAchievements != null && verifyAchievements.size() > 0) {
						attachAchievedPanel(verifyAchievements);
					}
				}
			}
		} else if (this.getStatus() == RoundStatus.OVER) {
			this.detachSelf();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	private void calculateScore() {
		if (EntityHolder.getPlayer().getStatus() != PlayerStatus.DEAD) {
			getLeagueScore().addEntityScore(EntityHolder.getPlayer(), EntityHolder.getPlayer().getCurrentRoundPosition());
		}
		for (Opponent opponent : opponents) {
			getLeagueScore().addEntityScore(opponent, opponent.getCurrentRoundPosition());
			opponent.setCurrentLeaguePoints(getLeagueScore().getEntityScore(opponent));
		}
		EntityHolder.getPlayer().setCurrentLeaguePoints(getLeagueScore().getEntityScore(EntityHolder.getPlayer()));
	}

	private void attachAchievedPanel(List<Achievement> verifyAchievements) {
		Sprite achieves = new Sprite(0, 0, TexturesHolder.getAchievesBackground(), EntityHolder.getEngine().getVertexBufferObjectManager());
		achieves.setPosition(Constants.SCREEN_WIDTH / 2 - achieves.getWidth() / 2, EntityHolder.getmCamera().getCenterY() - 400);
		this.attachChild(achieves);

		int offset = 0;
		for (Achievement achievement : verifyAchievements) {
			Text achievText = new Text(0, 0, TexturesHolder.getFontMicro(), achievement.getAchievementName().replace("\n", " "), 200, EntityHolder.getEngine()
					.getVertexBufferObjectManager());
			achievText.setPosition(achieves.getWidth() / 2 - achievText.getWidth() / 2, 50 + offset);
			achievText.setColor(Color.BLACK);
			achieves.attachChild(achievText);
			offset += 22;
		}
	}

	private void initLayout() {
		RoundBackGround leagueBackGround = new RoundBackGround(
				new Sprite(0, 0, TexturesHolder.getLeagueBackgroundFront(), EntityHolder.getEngine().getVertexBufferObjectManager()), new ParallaxEntity(-5f, new Sprite(0, 0,
						TexturesHolder.getRoundBackgroundBackKing(), EntityHolder.getEngine().getVertexBufferObjectManager())));

		this.addBackground(leagueBackGround);
		Sprite grass = new Sprite(0, 1620, TexturesHolder.getGrassBackground(), EntityHolder.getEngine().getVertexBufferObjectManager());
		grass.setZIndex(-1);

		countdown = new Text(0, 0, TexturesHolder.getFont(), "", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		countdown.setPosition(Constants.SCREEN_WIDTH / 2 - countdown.getWidth() / 2f, Constants.SCREEN_HEIGHT / 2);
		countdown.setColor(Color.BLACK);

		falseStart = new Text(0, 0, TexturesHolder.getFont(), "", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		falseStart.setColor(Color.BLACK);

		this.attachChild(grass);
		this.attachChild(falseStart);
		this.attachChild(countdown);
	}

	public LeagueScore getLeagueScore() {
		return leagueScore;
	}

	public void setLeagueScore(LeagueScore leagueScore) {
		this.leagueScore = leagueScore;
	}

}
