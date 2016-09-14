package pt.pxinxas.graviball.game.scene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;


import pt.pxinxas.graviball.activity.AchievementsActivity;
import pt.pxinxas.graviball.activity.HighScoresActivity;
import pt.pxinxas.graviball.activity.LoadingGameActivity;
import pt.pxinxas.graviball.activity.PositionalMenuAnimator;
import pt.pxinxas.graviball.game.achievements.AchievementManager;
import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.Toast;


public class GameMenuScene extends MenuScene implements IOnMenuItemClickListener {
	protected static final int MENU_CAMPAIGN = 0;
	protected static final int MENU_SURVIVAL = 1;
	protected static final int MENU_HIGHSCORES = 2;
	protected static final int MENU_ACHIVS = 3;
	protected static final int MENU_QUIT = 4;
	protected LoadingGameActivity activity;
	private boolean loaded = false;
	private boolean loading = false;

	public GameMenuScene(LoadingGameActivity activity) {
		this.activity = activity;
		this.setCamera(EntityHolder.getmCamera());
		final IMenuItem startCampaignMenuItem = new SpriteMenuItem(MENU_CAMPAIGN, TexturesHolder.getMenuItemBack().getTextureRegion(0), EntityHolder
				.getEngine().getVertexBufferObjectManager());
		this.addMenuItem(startCampaignMenuItem);

		final IMenuItem startSurvivalMenuItem = new SpriteMenuItem(MENU_SURVIVAL, TexturesHolder.getMenuItemBack().getTextureRegion(1), EntityHolder
				.getEngine().getVertexBufferObjectManager());
		this.addMenuItem(startSurvivalMenuItem);

		final IMenuItem highScoresMenuItem = new SpriteMenuItem(MENU_HIGHSCORES, TexturesHolder.getMenuItemBack().getTextureRegion(2), EntityHolder
				.getEngine().getVertexBufferObjectManager());
		this.addMenuItem(highScoresMenuItem);

		final IMenuItem achivesMenuItem = new SpriteMenuItem(MENU_ACHIVS, TexturesHolder.getMenuItemBack().getTextureRegion(3), EntityHolder
				.getEngine().getVertexBufferObjectManager());
		this.addMenuItem(achivesMenuItem);

		final IMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, TexturesHolder.getMenuItemBack().getTextureRegion(4), EntityHolder.getEngine()
				.getVertexBufferObjectManager());
		this.addMenuItem(quitMenuItem);

		this.setMenuAnimator(new PositionalMenuAnimator(105, 260, 0));
		this.buildAnimations();
		this.setBackgroundEnabled(true);
		this.setOnMenuItemClickListener(this);

		AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(1f, 1f, 1f, 45);
		Sprite sprite = new Sprite(0, 0, TexturesHolder.getLeagueBackgroundBack(), EntityHolder.getEngine().getVertexBufferObjectManager());
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(2f, sprite));

		this.setBackground(autoParallaxBackground);
		EntityHolder.resetCamera();

		attachEntities();
		this.sortChildren();

		final Rectangle left = new Rectangle(0, -1000, 1, Constants.SCREEN_HEIGHT + 3000, EntityHolder.getEngine().getVertexBufferObjectManager());
		final Rectangle right = new Rectangle(Constants.SCREEN_WIDTH - 2, -1000, 1, Constants.SCREEN_HEIGHT + 3000, EntityHolder.getEngine()
				.getVertexBufferObjectManager());
		left.setAlpha(0f);
		right.setAlpha(0f);

		this.attachChild(left);
		this.attachChild(right);

		Sprite logo = new Sprite(10, 10, TexturesHolder.getLogo(), EntityHolder.getEngine().getVertexBufferObjectManager());
		logo.setPosition(Constants.SCREEN_WIDTH / 2 - logo.getWidth() / 2, 10);
		this.attachChild(logo);

		Sprite help = new Sprite(400, 700, TexturesHolder.getHelp(), EntityHolder.getEngine().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(org.andengine.input.touch.TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
					EntityHolder.getEngine().setScene(new HelpScene());
					loaded = false;
				}
				return true;
			};
		};
		help.setScale(0.7f);
		help.setPosition(Constants.SCREEN_WIDTH - help.getWidthScaled() - 10, Constants.SCREEN_HEIGHT - help.getHeightScaled() - 10);

		this.registerTouchArea(help);
		this.attachChild(help);

	}

	private void attachEntities() {


	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
		if (loaded) {
			switch (pMenuItem.getID()) {
			case MENU_CAMPAIGN:

							EntityHolder.getEngine().setScene(new GameCampaignScene());

				break;
			case MENU_SURVIVAL:
				loaded = false;
				if (AchievementManager.getInstance().isGameBeaten()) {
					EntityHolder.getEngine().setScene(new GameSurvivalScene());

				} else {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(activity, "You must finish the Campaign before going to Survival Mode", Toast.LENGTH_LONG).show();
						}
					});
				}
				break;
			case MENU_HIGHSCORES:
				loaded = false;
				activity.startActivity(new Intent(activity, HighScoresActivity.class));
				break;
			case MENU_ACHIVS:
				loaded = false;
				activity.startActivity(new Intent(activity, AchievementsActivity.class));
				break;
			case MENU_QUIT:
				loaded = false;
				System.exit(0);
			}
		}
		return false;
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (!loaded && !loading) {
			loading = true;
			EntityHolder.getEngine().registerUpdateHandler(new TimerHandler(0.8f, new ITimerCallback() {
				@Override
				public void onTimePassed(TimerHandler pTimerHandler) {
					EntityHolder.getEngine().unregisterUpdateHandler(pTimerHandler);
					loaded = true;
					loading = false;

				}
			}));
		}

		super.onManagedUpdate(pSecondsElapsed);
	}
}
