package pt.pxinxas.graviball.game.holder;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.db.DBAdapter;
import pt.pxinxas.graviball.game.entity.Player;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.scene.BaseRoundScene;
import pt.pxinxas.graviball.game.scene.GameMenuScene;
import pt.pxinxas.graviball.game.scene.GameScene;

public class EntityHolder {
	private static Engine engine;
	private static Camera mCamera;
	private static Player player;
	private static DBAdapter dbAdapter;
	private static Boolean loaded = false;
	private static GameScene gameScene;
	private static GameMenuScene gameMenuScene;

	public static void resetCamera() {
		EntityHolder.getmCamera().set(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
	}
	
	public static void resetPlayer(BaseRoundScene scene) {
		if (player != null) {
			EntityHolder.getPlayer().clearEntityModifiers();
			EntityHolder.getPlayer().setPosition(Constants.INITIAL_WIDTH[0], Constants.INITIAL_HEIGHT);
			EntityHolder.getPlayer().setCurrentRoundScene(scene);
			EntityHolder.getPlayer().setStatus(PlayerStatus.STOPPED);
			EntityHolder.getPlayer().setScale(0.7f);
		}
	}

	public static Engine getEngine() {
		return engine;
	}

	public static void setEngine(Engine engine) {
		EntityHolder.engine = engine;
	}

	public static Camera getmCamera() {
		return mCamera;
	}

	public static void setmCamera(Camera mCamera) {
		EntityHolder.mCamera = mCamera;
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		EntityHolder.player = player;
	}

	public static DBAdapter getDbAdapter() {
		return dbAdapter;
	}

	public static void setDbAdapter(DBAdapter dbAdapter) {
		EntityHolder.dbAdapter = dbAdapter;
	}

	public static Boolean getLoaded() {
		return loaded;
	}

	public static void setLoaded(Boolean loaded) {
		EntityHolder.loaded = loaded;
	}

	public static GameMenuScene getGameMenuScene() {
		return gameMenuScene;
	}

	public static void setGameMenuScene(GameMenuScene gameMenuScene) {
		EntityHolder.gameMenuScene = gameMenuScene;
	}


	public static GameScene getGameScene() {
		return gameScene;
	}

	public static void setGameScene(GameScene gameScene) {
		EntityHolder.gameScene = gameScene;
	}
}
