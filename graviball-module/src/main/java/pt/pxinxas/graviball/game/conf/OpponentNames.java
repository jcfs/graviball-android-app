package pt.pxinxas.graviball.game.conf;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.text.Text;

import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;

public class OpponentNames {

	public static final List<String> nameList = new ArrayList<String>();
	private static final OpponentNames instance = new OpponentNames();
	private List<Text> nameListText = new ArrayList<Text>();

	static {
		nameList.add("Charlie");
		nameList.add("Jason");
		nameList.add("Adrian");

		nameList.add("George");
		nameList.add("Jacob");
		nameList.add("Mark");

		nameList.add("Patrick");
		nameList.add("Ricky");
		nameList.add("Seth");

		nameList.add("Kain");
		nameList.add("Frank");
		nameList.add("Dave");
	}

	public void init() {
		for (String name : nameList) {
			nameListText.add(new Text(0, 0, TexturesHolder.getFontMini(), name, EntityHolder.getEngine().getVertexBufferObjectManager()));	
		}
		nameListText.add(new Text(0, 0, TexturesHolder.getFontMini(), "Player", EntityHolder.getEngine().getVertexBufferObjectManager()));
		nameListText.add(new Text(0, 0, TexturesHolder.getFontMini(), "The King", EntityHolder.getEngine().getVertexBufferObjectManager()));
	}
	
	public Text getTextByName(String name) {
		for (Text text : nameListText) {
			if (text.getText().equals(name)) {
				if (text.hasParent()) {
					text.detachSelf();
				}
				return text;
			}
		}
		return null;
	}
	
	
	public static OpponentNames getInstance() {
		return instance;
	}

}
