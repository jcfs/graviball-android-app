package pt.pxinxas.graviball.game.scene;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;

public class HudCameraScene extends HUD {

	private Text gold;
	private Sprite heart;
	private Sprite diamond;
	private Sprite healthBarBack;
	private Sprite healthBarFront;
	private double currentPlayerHealth;

	private static final HudCameraScene instance = new HudCameraScene();
	
	public void init() {
		this.detachChildren();
		VertexBufferObjectManager vertexBufferObjectManager = EntityHolder.getEngine().getVertexBufferObjectManager();
		this.heart = new Sprite(0, 0, TexturesHolder.getHeart(), vertexBufferObjectManager);
		this.heart.setScale(0.6f);

		this.diamond = new Sprite(320, 10, TexturesHolder.getDiamond(), EntityHolder.getEngine().getVertexBufferObjectManager());
		this.gold = new Text(320 + this.diamond.getWidthScaled(), this.heart.getScaleCenterY() - 13, TexturesHolder.getFontMini(), "", 20,
				vertexBufferObjectManager);
		this.gold.setColor(0f,0f,0f);

		this.diamond.setScale(1.5f);

		this.healthBarBack = new Sprite(this.heart.getWidth(), this.heart.getScaleCenterY() - 13, TexturesHolder.getHealthBack(), vertexBufferObjectManager);
		this.healthBarFront = new Sprite(this.heart.getWidth() + 1, this.heart.getScaleCenterY() - 12, TexturesHolder.getHealthFront(), vertexBufferObjectManager);
		this.healthBarFront.setSize((float) (EntityHolder.getPlayer().getHealth() * 150) / 100, 18);

		this.setBackgroundEnabled(false);
		this.attachChild(this.diamond);
		this.attachChild(this.heart);
		this.attachChild(this.gold);
		this.attachChild(this.healthBarBack);
		this.attachChild(this.healthBarFront);

		this.setCamera(EntityHolder.getmCamera());
		this.currentPlayerHealth = EntityHolder.getPlayer().getHealth();
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		double cHealth = EntityHolder.getPlayer().getHealth();

		if (cHealth != this.currentPlayerHealth) {
			this.healthBarFront.setScaleCenter(0, 0);
			ScaleModifier pEntityModifier = new ScaleModifier(0.2f, 1f, (float) (cHealth / this.currentPlayerHealth), 1f, 1f);
			pEntityModifier.setAutoUnregisterWhenFinished(true);
			this.healthBarFront.registerEntityModifier(pEntityModifier);
			this.currentPlayerHealth = cHealth;
		}

		this.gold.setText(String.valueOf(EntityHolder.getPlayer().getGold()));
		super.onManagedUpdate(pSecondsElapsed);
	}

	public static HudCameraScene getInstance() {
		return instance;
	}

}
