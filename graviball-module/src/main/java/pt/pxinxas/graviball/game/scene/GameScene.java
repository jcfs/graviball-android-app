package pt.pxinxas.graviball.game.scene;

import org.andengine.entity.scene.Scene;

import pt.pxinxas.graviball.game.achievements.AchievementManager;

public class GameScene extends Scene {
	
	{
		AchievementManager.getInstance().init();
	}

}
