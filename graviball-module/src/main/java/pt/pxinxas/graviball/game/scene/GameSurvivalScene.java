package pt.pxinxas.graviball.game.scene;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.db.DBTables;
import pt.pxinxas.graviball.game.entity.Player;
import pt.pxinxas.graviball.game.entity.enumerator.GameStatus;
import pt.pxinxas.graviball.game.entity.enumerator.LeagueStatus;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import android.content.ContentValues;

public class GameSurvivalScene extends GameScene {

	private SurvivalLeagueScene currentLeagueScene;
	private GameStatus status;
	private int leagueIndex = 1;

	public GameSurvivalScene() {
		EntityHolder.setGameScene(this);
		createAndInitPlayer();
		GameState.getInstance().resetGameState();
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.getStatus() == GameStatus.LOST) {
			EntityHolder.getEngine().setScene(EntityHolder.getGameMenuScene());
		}
		if (this.currentLeagueScene == null) {
			this.currentLeagueScene = new SurvivalLeagueScene(1);
			this.status = GameStatus.ONGOING;
			this.currentLeagueScene.init();
			this.setChildScene(this.currentLeagueScene);
		} else if (this.currentLeagueScene.getStatus() == LeagueStatus.OVER) {
			this.clearChildScene();
			if (EntityHolder.getPlayer().getStatus() == PlayerStatus.DEAD) {
				this.status = GameStatus.LOST;
				insertHighScore();
				return;
			}
			handleUpDown();
			if (EntityHolder.getPlayer().getStatus() == PlayerStatus.DEAD) {
				this.status = GameStatus.LOST;
				insertHighScore();
				return;
			}
			this.currentLeagueScene.init();
			this.setChildScene(this.currentLeagueScene);
		}

		super.onManagedUpdate(pSecondsElapsed);
	}

	private void handleUpDown() {
		int mainPlayerPosition = this.currentLeagueScene.getLeagueScore().getMainPlayerPosition();
		if (mainPlayerPosition == 1) {
			this.currentLeagueScene.setLeagueLevel(++leagueIndex);
		} else if (mainPlayerPosition == 4) {
			EntityHolder.getPlayer().setStatus(PlayerStatus.DEAD);
		}
	}

	private void insertHighScore() {
		ContentValues values = new ContentValues();
		values.put(DBTables.TABLE_HIGHSCORES_LEAGUE, this.currentLeagueScene.getLeagueLevel());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		values.put(DBTables.TABLE_HIGHSCORES_DATE, formatter.format(Calendar.getInstance().getTime()));
		EntityHolder.getDbAdapter().insertHighScore(values);
	}

	private void createAndInitPlayer() {
		Player player = new Player(0, 0);
		EntityHolder.setPlayer(player);
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

}
