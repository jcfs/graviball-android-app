package pt.pxinxas.graviball.game.holder;

import org.andengine.entity.text.Text;

public class TextHolder {

	private static final TextHolder instance = new TextHolder();

	//Countdown text
	public Text COUNTDOWN_READY;
	public Text COUNTDOWN_SET;
	public Text COUNTDOWN_GO;
	public Text FALSE_START;
	
	
	public void init() {
		COUNTDOWN_READY = new Text(0, 0, TexturesHolder.getFont(), "Ready", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		COUNTDOWN_SET = new Text(0, 0, TexturesHolder.getFont(), "Set...", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		COUNTDOWN_GO = new Text(0, 0, TexturesHolder.getFont(), "GO!!!!", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
		FALSE_START = new Text(0, 0, TexturesHolder.getFont(), "FALSE START", 20, EntityHolder.getEngine().getVertexBufferObjectManager());
	}
	
	public static TextHolder getInstance() {
		return instance;
	}
	
}
