package eu.ydp.empiria.player.client.module.media.progress;

public class ProgressUpdateLogic {

	public boolean isReadyToUpdate(double currentTime, int lastTime) {
		return currentTime > lastTime + 1 || currentTime < lastTime;
	}
}
