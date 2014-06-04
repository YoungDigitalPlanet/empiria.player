package eu.ydp.empiria.player.client.util.time;

import com.google.gwt.user.client.Timer;

public class TemporaryFlag {

	private boolean flag = false;

	public void setFlagFor(int milliSeconds) {
		flag = true;

		Timer timer = new Timer() {
			@Override
			public void run() {
				flag = false;
			}
		};
		timer.schedule(milliSeconds);
	}

	public boolean isFlag() {
		return flag;
	}
}
