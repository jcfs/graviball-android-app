package pt.pxinxas.graviball.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.conf.OpponentNames;
import pt.pxinxas.graviball.game.db.DBAdapter;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TextHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import pt.pxinxas.graviball.game.scene.GameMenuScene;
import pt.pxinxas.graviball.game.scores.ScoreText;


public class LoadingGameActivity extends BaseGameActivity implements IAccelerationListener {

	private ITextureRegion mSplashBackgroundTextureRegion;
	private BitmapTextureAtlas mSplashBackgroundTextureAtlas;

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		EntityHolder.setmCamera(pEngineOptions.getCamera());
		EntityHolder.setEngine(new LimitedFPSEngine(pEngineOptions, Constants.FPS_CAP));
		EntityHolder.setLoaded(false);
		EntityHolder.getEngine().enableVibrator(this);
		
		return EntityHolder.getEngine();
	}

	@Override
	public EngineOptions onCreateEngineOptions() {

		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(Constants.SCREEN_WIDTH,
				Constants.SCREEN_HEIGHT), new Camera(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT));
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		mSplashBackgroundTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.DEFAULT);
		mSplashBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSplashBackgroundTextureAtlas, this,
				"badge_large.png", 0, 0);
		mEngine.getTextureManager().loadTexture(mSplashBackgroundTextureAtlas);
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

		Scene mSplashScene = new Scene();
		mSplashScene.setBackgroundEnabled(true);
		Sprite pEntity = new Sprite(0, 0, mSplashBackgroundTextureRegion, mEngine.getVertexBufferObjectManager());
		pEntity.setPosition(Constants.SCREEN_WIDTH / 2 - pEntity.getWidth() / 2, Constants.SCREEN_HEIGHT / 2 - pEntity.getHeight() / 2);
		mSplashScene.attachChild(pEntity);

		pOnCreateSceneCallback.onCreateSceneFinished(mSplashScene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		this.enableAccelerationSensor(this);
		mEngine.registerUpdateHandler(new TimerHandler(0.01f, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				mEngine.unregisterUpdateHandler(pTimerHandler);
				mSplashBackgroundTextureAtlas.unload();
				mSplashBackgroundTextureAtlas.clearTextureAtlasSources();
				mSplashBackgroundTextureRegion = null;
				try {
					loadResources();

				} catch (IOException e) {
					e.printStackTrace();
				} catch (TexturePackParseException e) {
					e.printStackTrace();
				}
				
				EntityHolder.setDbAdapter(new DBAdapter(getApplicationContext()));

				GameMenuScene gameMenuScene = new GameMenuScene(LoadingGameActivity.this);
				EntityHolder.setGameMenuScene(gameMenuScene);

				loadSingletons();
				mEngine.setScene(gameMenuScene);
			}

			private void loadSingletons() {
				ScoreText.getInstance().init();
				TextHolder.getInstance().init();
				OpponentNames.getInstance().init();
			}
		}));

		pOnPopulateSceneCallback.onPopulateSceneFinished();

	}

	private void loadResources() throws IOException, TexturePackParseException {
		FontFactory.setAssetBasePath("fonts/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		BitmapTextureAtlas mFontTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.DEFAULT);
		BitmapTextureAtlas mFontMiniTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.DEFAULT);
		BitmapTextureAtlas mFontMicroTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.DEFAULT);

		TexturesHolder.setFont(FontFactory.createFromAsset(this.getFontManager(), mFontTextureAtlas, this.getAssets(), "novafont.ttf", 52, true,
				Color.BLACK_ABGR_PACKED_INT));
		TexturesHolder.setFontMini(FontFactory.createFromAsset(this.getFontManager(), mFontMiniTextureAtlas, this.getAssets(), "novafont.ttf", 30,
				true, Color.WHITE_ABGR_PACKED_INT));
		TexturesHolder.setFontMicro(FontFactory.createFromAsset(this.getFontManager(), mFontMicroTextureAtlas, this.getAssets(), "monkey.ttf", 30,
				true, Color.WHITE_ABGR_PACKED_INT));

		final TexturePack spritesheetTexturePack = new TexturePackLoader(this.getTextureManager(), "gfx/").loadFromAsset(this.getAssets(),
				"textures2.xml");
		spritesheetTexturePack.loadTexture();
		TexturePackTextureRegionLibrary textureLib = spritesheetTexturePack.getTexturePackTextureRegionLibrary();
		TexturesHolder.setRoundBackgroundBackKing(textureLib.get(TexturePacker.BACKGROUND_BACK_KING_ID));
		TexturesHolder.setLeagueBackgroundFront(textureLib.get(TexturePacker.BACKGROUND_FRONT_ID));
		TexturesHolder.setLeagueBackgroundBack(textureLib.get(TexturePacker.BACKGROUND_BACK_ID));
		
		final TexturePack spritesheetTexturePack2 = new TexturePackLoader(this.getTextureManager(), "gfx/").loadFromAsset(this.getAssets(),
				"textures.xml");
		spritesheetTexturePack2.loadTexture();
		textureLib = spritesheetTexturePack2.getTexturePackTextureRegionLibrary();

		TexturesHolder.setOpponentTextures(new ArrayList<ITextureRegion>());
		
		TexturesHolder.setLeagueBackground(textureLib.get(TexturePacker.BACKGROUND_LEAGUE_ID));
		TexturesHolder.setScoreBoardBackground(textureLib.get(TexturePacker.SCOREBOARD_ID));
		
		TexturesHolder.setGrassBackground(textureLib.get(TexturePacker.GRASS_ID));
		TexturesHolder.setTreeBackground(textureLib.get(TexturePacker.TREE_ID));
		TexturesHolder.setAchievesBackground(textureLib.get(TexturePacker.ACHIEVES_ID));

		TexturesHolder.setMedKit(textureLib.get(TexturePacker.MEDKIT_ID));
		TexturesHolder.setNextButton(textureLib.get(TexturePacker.NEXT_ID));
		TexturesHolder.setChute(textureLib.get(TexturePacker.CHUTE_ID));
		TexturesHolder.setHeart(textureLib.get(TexturePacker.RED_HEART_ID));
		TexturesHolder.setDiamond(textureLib.get(TexturePacker.DIAMOND_ID));
		TexturesHolder.setHealthBack(textureLib.get(TexturePacker.HEALTHBARBACK_ID));
		TexturesHolder.setHealthFront(textureLib.get(TexturePacker.HEALTHBARFRONT_ID));
		
		BitmapTextureAtlas mMenuAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 512, TextureOptions.DEFAULT);
		TexturesHolder.setMenuItemBack(BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mMenuAtlas, this, "menu.png", 0, 0, 1, 5));
		TexturesHolder.setBubble(textureLib.get(TexturePacker.BUBBLE_ID));
		TexturesHolder.setLogo(textureLib.get(TexturePacker.GRAVIBALL_ID));
		TexturesHolder.setHelp(textureLib.get(TexturePacker.HELP_ID));
		
		
		for (int i = 0; i < 12; i++) {
			TexturesHolder.getOpponentTextures().add(textureLib.get(TexturePacker.OPPONENTS_1_ID + i));
		}

		TexturesHolder.setKingTexture(textureLib.get(TexturePacker.KING_ID));
		TexturesHolder.setPlayerTexture(textureLib.get(TexturePacker.PLAYER_ID));

		TexturesHolder.getFont().load();
		TexturesHolder.getFontMini().load();
		TexturesHolder.getFontMicro().load();
		mMenuAtlas.load();

		EntityHolder.setLoaded(true);
	}

	@Override
	public void onBackPressed() {
		if (EntityHolder.getGameMenuScene() != null) {
			EntityHolder.getGameMenuScene().clearChildScene();
			this.mEngine.setScene(EntityHolder.getGameMenuScene());
		} else {
			EntityHolder.setGameMenuScene(new GameMenuScene(this));
			this.mEngine.setScene(EntityHolder.getGameMenuScene());
		}

	}

	@Override
	public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAccelerationChanged(AccelerationData pAccelerationData) {

	}

}
