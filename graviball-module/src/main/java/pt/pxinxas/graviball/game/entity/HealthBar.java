package pt.pxinxas.graviball.game.entity;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;

import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;

public class HealthBar {
	private Sprite heart;
	private Sprite healthBarBack;
	private Sprite healthBarFront;
	
	public HealthBar(float px, float py) {
		this.heart = new Sprite(px, py, TexturesHolder.getHeart(), EntityHolder
				.getEngine().getVertexBufferObjectManager());
		this.heart.setScale(0.6f);
		this.healthBarBack = new Sprite(px+this.heart.getWidth(), py+this.heart.getScaleCenterY() - 13, TexturesHolder.getHealthBack(), EntityHolder
				.getEngine().getVertexBufferObjectManager());
		this.healthBarFront = new Sprite(px+this.heart.getWidth() + 1, py+this.heart.getScaleCenterY() - 12, TexturesHolder.getHealthFront(), EntityHolder
				.getEngine().getVertexBufferObjectManager());
		this.healthBarFront.setSize((float) (EntityHolder.getPlayer().getHealth() * 150) / 100, 18);
	}
	
	public void updateBar() {
		this.healthBarFront.setScaleCenter(0, 0);
		this.healthBarFront.setSize((float) (EntityHolder.getPlayer().getHealth() * 150) / 100, 18);
	}
	
	public void attachToScene(Scene scene) {
		scene.attachChild(heart);
		scene.attachChild(healthBarBack);
		scene.attachChild(healthBarFront);
	}
	
}
