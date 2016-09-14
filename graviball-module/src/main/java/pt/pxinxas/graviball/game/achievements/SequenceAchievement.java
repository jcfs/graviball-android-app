package pt.pxinxas.graviball.game.achievements;

public abstract class SequenceAchievement extends Achievement {
	protected Integer counter;

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}
}
