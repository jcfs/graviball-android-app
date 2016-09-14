package pt.pxinxas.graviball.game.entity;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;

public class RoundBackGround {

	private final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(1f,1f,1f,  25);
	private Sprite backgroundSprites;

	public RoundBackGround(Sprite background, ParallaxEntity... entities) {
		if (background != null) {
			this.setBackgroundSprites(background);
		}
		for (ParallaxEntity parallaxEntity : entities) {
			this.attachEntity(parallaxEntity);
		}
	}

	public void attachEntity(ParallaxEntity entity) {
		getAutoParallaxBackground().attachParallaxEntity(entity);
	}

	public Sprite getBackgroundSprites() {
		return backgroundSprites;
	}

	public void setBackgroundSprites(Sprite backgroundSprites) {
		this.backgroundSprites = backgroundSprites;
	}

	public AutoParallaxBackground getAutoParallaxBackground() {
		return autoParallaxBackground;
	}

}
