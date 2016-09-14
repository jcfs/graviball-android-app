package pt.pxinxas.graviball.game.entity;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import android.view.MotionEvent;

/**
 * @author JSalavisa
 * 
 */
public class MedKit extends Sprite {

	public MedKit(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if (EntityHolder.getPlayer().getHealth() >= 100 || EntityHolder.getPlayer().getHealth() <= 0) {
				this.setAlpha(0.3f);
			} else 	if (EntityHolder.getPlayer().getGold() >= Constants.MEDKIT_COST) {
				EntityHolder.getPlayer().setGold(EntityHolder.getPlayer().getGold() - Constants.MEDKIT_COST);
				EntityHolder.getPlayer().setHealth(EntityHolder.getPlayer().getHealth() + Constants.MEDKIT_HEALTH_INCREASE);
				if (EntityHolder.getPlayer().getHealth() > 100) {
					EntityHolder.getPlayer().setHealth(100);
				}
			} 
			
			if (EntityHolder.getPlayer().getHealth() == 100 || EntityHolder.getPlayer().getGold() < 50) {
				this.setAlpha(0.3f);
			}
		}
		return true;
	}
}
