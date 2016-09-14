package pt.pxinxas.graviball.game.scores;

import org.andengine.entity.text.Text;

import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;

public class ScoreText {

	private static final ScoreText instance = new ScoreText();
	private Text one;
	private Text two;
	private Text three;
	private Text four;

	public void init() {
		this.one = new Text(0, 0, TexturesHolder.getFont(), "1", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		this.one.setScale(1.5f);
		this.two = new Text(0, 0, TexturesHolder.getFont(), "2", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		this.two.setScale(1.5f);
		this.three = new Text(0, 0, TexturesHolder.getFont(), "3", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		this.three.setScale(1.5f);
		this.four = new Text(0, 0, TexturesHolder.getFont(), "4", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		this.four.setScale(1.5f);
	}

	public Text getTextFromNumber(int n) {
		switch (n) {
		case 1:
			one.detachSelf();
			return one;
		case 2:
			two.detachSelf();
			return two;
		case 3:
			three.detachSelf();
			return three;
		case 4:
			four.detachSelf();
			return four;
		}
		return null;
	}

	public static ScoreText getInstance() {
		return instance;
	}

}
