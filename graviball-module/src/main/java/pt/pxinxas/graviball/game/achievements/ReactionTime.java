package pt.pxinxas.graviball.game.achievements;

public class ReactionTime implements Comparable<ReactionTime> {
	private EntityType type;
	private double reactionTime;

	public EntityType getType() {
		return type;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public double getReactionTime() {
		return reactionTime;
	}

	public void setReactionTime(double reactionTime) {
		this.reactionTime = reactionTime;
	}

	@Override
	public int compareTo(ReactionTime o) {
		return o.getReactionTime() > this.reactionTime ? -1 : o.getReactionTime() < this.reactionTime ? 1 : 0;
	}
}
