package pt.pxinxas.graviball.game.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import android.view.MotionEvent;

public class HelpScene extends Scene {
	
	private String helpText = "Help";
	private String campaign = "Campaign Mode\n\n1 - You are the Purple one!\n2 - Five Leagues, 3 rounds each.\n3 - Be the first to React on GO!\n4 - Land safe. Open parachute on time\n5 - Win diamonds, buy your health back.\n6 - Beat the King in a best of 5.\n\n";
	private String survival = "Survival Mode\n\n1 - You are the Purple one!\n2 - Unlimited leagues, 3 rounds each.\n3 - Be the first to React!\n4 - Land safe.\n5 - Win diamonds but... no health back!\n6 - Do your best!\n";

	public HelpScene() {
		Text helpT = new Text(0, 0, TexturesHolder.getFont(), helpText, 1000, EntityHolder.getEngine().getVertexBufferObjectManager());
		helpT.setPosition((Constants.SCREEN_WIDTH/2)-(helpT.getWidth()/2), 50);
		this.attachChild(helpT);
		
		Text campaignT = new Text(0, 0, TexturesHolder.getFontMicro(), campaign, 1000, EntityHolder.getEngine().getVertexBufferObjectManager());
		campaignT.setPosition(30, 150);
		this.attachChild(campaignT);
		
		Text survivalT = new Text(0, 0, TexturesHolder.getFontMicro(), survival, 1000, EntityHolder.getEngine().getVertexBufferObjectManager());
		survivalT.setPosition(30, 400);
		this.attachChild(survivalT);
		
		AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(1f,1f,1f, 45);
		Sprite sprite = new Sprite(0, 0, TexturesHolder.getLeagueBackgroundBack(), EntityHolder
				.getEngine().getVertexBufferObjectManager());
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(2f, sprite));

		this.setBackground(autoParallaxBackground);
	}
	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			EntityHolder.getEngine().setScene(EntityHolder.getGameMenuScene());
		}
		return true;		
	}
	
}
