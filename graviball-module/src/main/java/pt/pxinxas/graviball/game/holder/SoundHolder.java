package pt.pxinxas.graviball.game.holder;

import org.andengine.audio.sound.Sound;

public class SoundHolder {

	private Sound alarm;
	
	private static final SoundHolder instance = new SoundHolder();

	public static SoundHolder getInstance() {
		return instance;
	}

	public Sound getAlarm() {
		return alarm;
	}

	public void setAlarm(Sound alarm) {
		this.alarm = alarm;
	}
	
}
