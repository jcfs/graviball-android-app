package pt.pxinxas.graviball.game.entity;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import pt.pxinxas.graviball.game.entity.enumerator.LeagueStatus;
import pt.pxinxas.graviball.game.scene.BaseLeagueScene;
import android.view.MotionEvent;

/**
 * @author JSalavisa
 * 
 */
public class NextButton extends Sprite {

	public NextButton(final float pX, final float pY, final ITextureRegion pTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		BaseLeagueScene cScene = (BaseLeagueScene) this.getParent();
		if (pSceneTouchEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if (cScene.getStatus() == LeagueStatus.STOPPED) {
				cScene.setStatus(LeagueStatus.READY);
				return true;
			} else if (cScene.getStatus() == LeagueStatus.FINISHED) {
				cScene.setStatus(LeagueStatus.OVER);
				cScene.detachChildren();
				cScene.detachSelf();
			}
		}
		return true;
	}

}
