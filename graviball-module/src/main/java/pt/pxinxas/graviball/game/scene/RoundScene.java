package pt.pxinxas.graviball.game.scene;

import java.util.List;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

import pt.pxinxas.graviball.game.achievements.Achievement;
import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.Opponent;
import pt.pxinxas.graviball.game.entity.RoundBackGround;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.entity.enumerator.RoundStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.ScoreHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import android.view.MotionEvent;

public class RoundScene extends BaseRoundScene {

	public RoundScene(BaseLeagueScene lScene, List<Opponent> opponents) {
		this.setLeagueScene(lScene);
		this.opponents = opponents;
		initRound();
	}

	private void initRound() {
		ScoreHolder.init();
		this.setStatus(RoundStatus.STOPPED);
		this.delay = 0;
		this.playerDelay = 0;
		this.setRoundIndex(this.getRoundIndex() + 1);

		EntityHolder.resetPlayer(this);
		EntityHolder.resetCamera();
		HudCameraScene.getInstance().init();
		this.attachChild(HudCameraScene.getInstance());

		attachEntities();
		initLayout();
		this.sortChildren();

	}

	private void attachEntities() {
		this.attachChild(EntityHolder.getPlayer());
		EntityHolder.getPlayer().setCurrentLeaguePoints(0);
		int i = 1;
		for (Opponent opponent : opponents) {
			opponent.setCurrentRoundScene(this);
			opponent.setStatus(PlayerStatus.STOPPED);
			opponent.setPosition(Constants.INITIAL_WIDTH[i++], Constants.INITIAL_HEIGHT);
			opponent.setCurrentLeaguePoints(0);
			opponent.clearEntityModifiers();
			this.attachChild(opponent);
		}
	}

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		RoundScene cScene = this;
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if (cScene.getStatus() == RoundStatus.STOPPED) {
				EntityHolder.getPlayer().setStatus(PlayerStatus.FALSE_START);
				falseStart.setText("FALSE START!");
				falseStart.setPosition(Constants.SCREEN_WIDTH / 2 - falseStart.getWidth() / 2, (Constants.SCREEN_HEIGHT / 2) - 100);
				EntityHolder.getPlayer().setCurrentRoundPosition(4);
				GameState.getInstance().setFalseStart(true);
				return true;
			}
			if (cScene.getStatus() == RoundStatus.FINISHED) {
				if (cScene.getRoundIndex() == Constants.MAX_ROUNDS_PER_LEAGUE || EntityHolder.getPlayer().getStatus() == PlayerStatus.DEAD) {
					quitRound();
				} else {
					cScene.clearChildScene();
					cScene.detachChildren();
					initRound();
				}
				return true;
			}
			if (EntityHolder.getPlayer().getStatus() == PlayerStatus.MOVING) {
				EntityHolder.getPlayer().setStatus(PlayerStatus.SLOWING);
			} else if (EntityHolder.getPlayer().getStatus() == PlayerStatus.STOPPED) {
				EntityHolder.getPlayer().setStatus(PlayerStatus.ACCEL);
				GameState.getInstance().addReactionTime(EntityHolder.getPlayer(), this.playerDelay);
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
		EntityHolder.getEngine().setScene(EntityHolder.getGameScene());
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
					this.setChildScene(new ScoreScene(leagueScene.getLeagueScore()));
					showPromotionText();
					this.countdown.setVisible(false);
					this.falseStart.setVisible(false);

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

	private void showPromotionText() {
		String text = null;
		Color textColor = null;
		
		if (EntityHolder.getPlayer().getStatus() == PlayerStatus.DEAD) {
			text = "You are dead";
			textColor = new Color(0.7f,0.7f,0.7f);
		} else if (this.roundIndex == 3 && getLeagueScene().getLeagueScore().getMainPlayerPosition() == 1) {
			text = "You've been promoted";
			textColor = Color.GREEN;
		} else if (this.roundIndex == 3 && getLeagueScene().getLeagueScore().getMainPlayerPosition() == 4) {
			text = "You've been demoted";
			textColor = Color.RED;
		}
		
		if (text != null) {
			this.promotionText = new Text(0, 0, TexturesHolder.getFontMini(), "", 100, EntityHolder.getEngine().getVertexBufferObjectManager());
			this.promotionText.setText(text);
			this.promotionText.setPosition(EntityHolder.getmCamera().getCenterX() - (this.promotionText.getWidth() / 2), EntityHolder.getmCamera()
					.getCenterY()-330);
			this.promotionText.setColor(textColor);
			this.attachChild(this.promotionText);
		}
	}

	private void attachAchievedPanel(List<Achievement> verifyAchievements) {
		Sprite achieves = new Sprite(0, 0, TexturesHolder.getAchievesBackground(), EntityHolder.getEngine().getVertexBufferObjectManager());
		achieves.setPosition(Constants.SCREEN_WIDTH / 2 - achieves.getWidth() / 2, EntityHolder.getmCamera().getCenterY() - 300);
		this.attachChild(achieves);

		int offset = 0;
		for (Achievement achievement : verifyAchievements) {
			Text achievText = new Text(0, 0, TexturesHolder.getFontMicro(), achievement.getAchievementName().replace("\n", " "), 200, EntityHolder
					.getEngine().getVertexBufferObjectManager());
			achievText.setPosition(achieves.getWidth() / 2 - achievText.getWidth() / 2, 30 + offset);
			achievText.setColor(Color.WHITE);
			achieves.attachChild(achievText);
			offset += 22;
		}
		AlphaModifier alphaPlus = new AlphaModifier(4f, 0f, 1f);
		alphaPlus.setAutoUnregisterWhenFinished(true);
		achieves.registerEntityModifier(alphaPlus);
	}

	private void calculateScore() {
		if (EntityHolder.getPlayer().getStatus() != PlayerStatus.DEAD) {
			leagueScene.getLeagueScore().addEntityScore(EntityHolder.getPlayer(), EntityHolder.getPlayer().getCurrentRoundPosition());
			EntityHolder.getPlayer().setGold(
					EntityHolder.getPlayer().getGold() + Constants.GOLD_PER_PLACE[EntityHolder.getPlayer().getCurrentRoundPosition() - 1]);
		}
		for (Opponent opponent : opponents) {
			leagueScene.getLeagueScore().addEntityScore(opponent, opponent.getCurrentRoundPosition());
			opponent.setCurrentLeaguePoints(leagueScene.getLeagueScore().getEntityScore(opponent));
		}
		EntityHolder.getPlayer().setCurrentLeaguePoints(leagueScene.getLeagueScore().getEntityScore(EntityHolder.getPlayer()));
	}

	private void initLayout() {
		RoundBackGround leagueBackGround = new RoundBackGround(new Sprite(0, 0, TexturesHolder.getLeagueBackgroundFront(), EntityHolder.getEngine()
				.getVertexBufferObjectManager()), new ParallaxEntity(3f, new Sprite(0, 0, TexturesHolder.getLeagueBackgroundBack(), EntityHolder
				.getEngine().getVertexBufferObjectManager())));
		this.addBackground(leagueBackGround);
		Sprite grass = new Sprite(0, 1620, TexturesHolder.getGrassBackground(), EntityHolder.getEngine().getVertexBufferObjectManager());
		Sprite tree = new Sprite(0, 1330, TexturesHolder.getTreeBackground(), EntityHolder.getEngine().getVertexBufferObjectManager());

		grass.setZIndex(-1);
		tree.setZIndex(-2);

		countdown = new Text(0, 0, TexturesHolder.getFont(), "", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		countdown.setPosition(Constants.SCREEN_WIDTH / 2 - countdown.getWidth() / 2f, Constants.SCREEN_HEIGHT / 2);
		countdown.setColor(Color.BLACK);
		countdown.setVisible(true);

		falseStart = new Text(0, 0, TexturesHolder.getFont(), "", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		falseStart.setColor(Color.BLACK);
		falseStart.setVisible(true);
		this.attachChild(grass);
		this.attachChild(tree);
		this.attachChild(falseStart);
		this.attachChild(countdown);
	}

}
