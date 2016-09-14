package pt.pxinxas.graviball.game.entity;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.holder.RandomHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;
import pt.pxinxas.graviball.game.scene.BaseLeagueScene;

public class King extends Opponent {

	public King() {
		this(0, 0);
	}

	public King(float pX, float pY) {
		super(pX, pY, TexturesHolder.getKingTexture());
		this.setName("The King");
	}

	@Override
	protected boolean openChute() {
		return this.getY() > (Constants.LEAGUE_DEPTH - (Constants.LEAGUE_DEPTH / 3) + 100);
	}

	@Override
	protected boolean startFalling(BaseLeagueScene leagueScene, double delay) {
		double offset = 0.0;
		offset += (RandomHolder.getInstance().getNextFloat() / 5);
		return delay > offset;
	}
	
	@Override
	protected void actionOnStart() {
	}
	
	@Override
	protected void actionOnFall() {
	}

}
