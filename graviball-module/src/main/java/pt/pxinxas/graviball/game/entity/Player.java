package pt.pxinxas.graviball.game.entity;

import org.andengine.entity.modifier.JumpModifier;

import pt.pxinxas.graviball.game.conf.Constants;
import pt.pxinxas.graviball.game.entity.enumerator.PlayerStatus;
import pt.pxinxas.graviball.game.holder.EntityHolder;
import pt.pxinxas.graviball.game.holder.TexturesHolder;

public class Player extends FallingEntity {
	private String name = "Player";
	private Integer currentLeaguePoints;
	private Integer gold;

	public Player(float pX, float pY) {
		super(pX, pY, TexturesHolder.getPlayerTexture());
		this.setStatus(PlayerStatus.STOPPED);
		this.setHealth(100);
		this.name = "Player";
		this.setGold(0);
	}

	public String getPlayerData() {
		return this.name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public Integer getCurrentLeaguePoints() {
		return currentLeaguePoints;
	}

	public void setCurrentLeaguePoints(Integer currentLeaguePoints) {
		this.currentLeaguePoints = currentLeaguePoints;
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (this.getStatus() != PlayerStatus.FINISHED && this.getStatus() != PlayerStatus.DEAD) {
			if (this.getY() > 400) {
				if (this.getY() < Constants.LEAGUE_DEPTH - 400) {
					EntityHolder.getmCamera().setCenter(EntityHolder.getmCamera().getCenterX(), this.getY());
				} else {
					EntityHolder.getmCamera().setCenter(EntityHolder.getmCamera().getCenterX(), Constants.LEAGUE_DEPTH - 400);
				}
			}
		}

		if (this.getStatus() == PlayerStatus.DEAD) {
			JumpModifier pEntityModifier = new JumpModifier(1f, this.getX(), this.getX() + 100, this.getY(), this.getY() + 1000, 1000);
			pEntityModifier.setAutoUnregisterWhenFinished(true);
			this.registerEntityModifier(pEntityModifier);
		}
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

}
