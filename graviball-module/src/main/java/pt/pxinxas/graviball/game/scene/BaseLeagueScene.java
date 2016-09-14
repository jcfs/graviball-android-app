package pt.pxinxas.graviball.game.scene;

import java.util.List;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.Opponent;
import pt.pxinxas.graviball.game.entity.enumerator.LeagueStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import pt.pxinxas.graviball.game.scores.LeagueScore;

public abstract class BaseLeagueScene extends Scene {
	protected int leagueLevel;
	protected LeagueStatus status;
	protected LeagueScore leagueScore;
	protected List<Opponent> opponents;
	protected Text leagueName;
	protected Text playerText;

	public BaseLeagueScene(int leagueLevel) {
		this.setLeagueLevel(leagueLevel);
		
		AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(1f,1f,1f,  25);
		ParallaxEntity parallaxEntity = new ParallaxEntity(4f, new Sprite(0, 0, TexturesHolder.getLeagueBackgroundBack(), EntityHolder.getEngine().getVertexBufferObjectManager()));
		autoParallaxBackground.attachParallaxEntity(parallaxEntity);
		this.setBackground(autoParallaxBackground);	
		
	}
	
	public abstract void init();

	public int getPlayerCurrentPosition() {
		return leagueScore.getMainPlayerPosition();
	}
	
	public int getLeagueLevel() {
		return leagueLevel;
	}

	public void setLeagueLevel(int leagueLevel) {
		this.leagueLevel = leagueLevel;
	}

	public LeagueStatus getStatus() {
		return status;
	}

	public void setStatus(LeagueStatus status) {
		this.status = status;
	}

	public LeagueScore getLeagueScore() {
		return leagueScore;
	}

	public void setLeagueScore(LeagueScore leagueScore) {
		this.leagueScore = leagueScore;
	}

	/**
	 * 
	 */
	protected void printInfo() {
		int sOffset = 0;
		int textOffset = 40;
		Sprite pSprite = new Sprite(30, Constants.SCREEN_HEIGHT / 2 + sOffset - 100, EntityHolder.getPlayer().getTextureRegion().deepCopy(), EntityHolder.getEngine()
					.getVertexBufferObjectManager());
		pSprite.setScale(0.6f);
		Text pText = new Text(0, 0, TexturesHolder.getFontMini(), "", 10, EntityHolder.getEngine().getVertexBufferObjectManager());
		pText.setColor(0f,0f,0f);
		pText.setPosition(140, Constants.SCREEN_HEIGHT / 2 + textOffset - 100);
		pText.setText("Player");
		
		this.attachChild(pSprite);
		this.attachChild(pText);
		for (Opponent opponent : opponents) {
			Sprite opSprite = new Sprite(0, 0, opponent.getTextureRegion().deepCopy(), EntityHolder.getEngine()
					.getVertexBufferObjectManager());
			sOffset += opSprite.getHeight() / 2 + 30;
			textOffset += opSprite.getHeight() / 2 + 30;
			
			opSprite.setPosition(30, Constants.SCREEN_HEIGHT / 2 + sOffset - 100);
			opSprite.setScale(0.6f);
			Text opText = new Text(0, 0, TexturesHolder.getFontMini(), "", 10, EntityHolder.getEngine().getVertexBufferObjectManager());
			opText.setColor(0f,0f,0f);
			opText.setPosition(140, Constants.SCREEN_HEIGHT / 2 + textOffset - 100);
			opText.setText(opponent.getName() + " "	+ (opponent.getCurrentLeaguePoints() != null ? opponent.getCurrentLeaguePoints() : ""));
			
			this.attachChild(opSprite);
			this.attachChild(opText);
		}
	}

}
