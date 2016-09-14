package pt.pxinxas.graviball.game.scene;

import java.util.Collections;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.conf.OpponentNames;
import pt.pxinxas.graviball.game.entity.FallingEntity;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import pt.pxinxas.graviball.game.scores.LeagueScore;
import pt.pxinxas.graviball.game.scores.Score;

public class ScoreScene extends Scene {

	private Sprite scoreboard;

	public ScoreScene(LeagueScore leagueScore) {
		scoreboard = new Sprite(Constants.SCREEN_WIDTH / 2 - 200, EntityHolder.getmCamera().getCenterY() - 150,
				TexturesHolder.getScoreBoardBackground(), EntityHolder.getEngine().getVertexBufferObjectManager());
		this.attachChild(scoreboard);
		this.setBackgroundEnabled(false);

		int offset = 10;
		Collections.sort(leagueScore.getScores());
		for (Score score : leagueScore.getScores()) {
			Sprite entititySprite = new Sprite(Constants.SCREEN_WIDTH / 2 - 230, EntityHolder.getmCamera().getCenterY() - 180 + offset,
					((Sprite) score.getPlayer()).getTextureRegion().deepCopy(), EntityHolder.getEngine().getVertexBufferObjectManager());
			entititySprite.setScale(0.5f);
			
			Text playerNameText = OpponentNames.getInstance().getTextByName(((FallingEntity) score.getPlayer()).getName());
			playerNameText.setPosition(Constants.SCREEN_WIDTH / 2 - 200 + 70, EntityHolder.getmCamera().getCenterY() - 146 + offset);
			playerNameText.setColor(1f,1f,1f);
			
			Text scoreText = new Text(0, 0, TexturesHolder.getFontMini(), "", 10, EntityHolder.getEngine().getVertexBufferObjectManager());
			scoreText.setPosition(Constants.SCREEN_WIDTH / 2 + 150, EntityHolder.getmCamera().getCenterY() - 146 + offset);
			scoreText.setText(Math.round(score.getScore())+"");
			scoreText.setColor(0f,0f,0f);

			offset += entititySprite.getHeight() - 33;
			
			this.attachChild(playerNameText);
			this.attachChild(scoreText);
			this.attachChild(entititySprite);
		}
	}

	@Override
	public void onDetached() {
		super.onDetached();
		this.detachChildren();
	}
}
