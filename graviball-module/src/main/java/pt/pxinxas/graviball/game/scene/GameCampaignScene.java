package pt.pxinxas.graviball.game.scene;

import pt.pxinxas.graviball.game.achievements.GameState;
import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.Player;
import pt.pxinxas.graviball.game.entity.enumerator.GameStatus;
import pt.pxinxas.graviball.game.entity.enumerator.LeagueStatus;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;

public class GameCampaignScene extends GameScene {

	private BaseLeagueScene currentLeagueScene;
	private GameStatus status;
	private int leagueIndex = 1;

	public GameCampaignScene() {
		EntityHolder.setGameScene(this);
		createAndInitPlayer();
		GameState.getInstance().resetGameState();
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.getStatus() == GameStatus.LOST || this.getStatus() == GameStatus.WON) {
			EntityHolder.getEngine().setScene(EntityHolder.getGameMenuScene());
		}
		if (this.currentLeagueScene == null) {
			this.currentLeagueScene = getNewLeagueScene(leagueIndex);
			this.status = GameStatus.ONGOING;
			this.currentLeagueScene.init();
			this.setChildScene(this.currentLeagueScene);
		} else if (this.currentLeagueScene.getStatus() == LeagueStatus.OVER) {
			this.clearChildScene();
			if (EntityHolder.getPlayer().getStatus() == PlayerStatus.DEAD) {
				this.status = GameStatus.LOST;
				return;
			}
			handleUpDown();
			this.currentLeagueScene.init();
			this.setChildScene(this.currentLeagueScene);
		}

		super.onManagedUpdate(pSecondsElapsed);
	}

	private void handleUpDown() {
		int mainPlayerPosition = this.currentLeagueScene.getLeagueScore().getMainPlayerPosition();
		if (mainPlayerPosition == 1) {
			if (leagueIndex == Constants.MAX_LEAGUES_PER_GAME) {
				this.status = GameStatus.WON;
			} else {
				this.currentLeagueScene = getNewLeagueScene(++leagueIndex);
			}
		} else if (this.currentLeagueScene instanceof CampaignKingLeagueScene && mainPlayerPosition == 2) {
			this.currentLeagueScene = getNewLeagueScene(--leagueIndex);
		} else if (mainPlayerPosition == 4) {
			if (leagueIndex > 1) {
				this.currentLeagueScene = getNewLeagueScene(--leagueIndex);
			}
		}
	}

	private BaseLeagueScene getNewLeagueScene(int leagueIndex) {
		if (leagueIndex == 5) {
			return new CampaignKingLeagueScene(5);
		} else {
			return new CampaignLeagueScene(leagueIndex);
		}
	}
	
	private void createAndInitPlayer() {
		Player player = new Player(0, 0);
		EntityHolder.setPlayer(player);
	}

	public BaseLeagueScene getCurrentLeagueScene() {
		return currentLeagueScene;
	}

	public void setCurrentLeagueScene(BaseLeagueScene currentLeagueScene) {
		this.currentLeagueScene = currentLeagueScene;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

}
