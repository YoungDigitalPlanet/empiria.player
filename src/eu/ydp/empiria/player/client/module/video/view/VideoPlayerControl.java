package eu.ydp.empiria.player.client.module.video.view;

public interface VideoPlayerControl {

	void play();

	void pause();

	void setCurrentTime(float position);
	
	float getCurrentTime();

	void addPlayHandler(VideoPlayerControlHandler handler);

	void addPauseHandler(VideoPlayerControlHandler handler);

	void addEndedHandler(VideoPlayerControlHandler handler);

	void addTimeUpdateHandler(VideoPlayerControlHandler handler);

	void addLoadStartHandler(VideoPlayerControlHandler handler);

	void addLoadedMetadataHandler(VideoPlayerControlHandler handler);

	void addLoadedDataHandler(VideoPlayerControlHandler handler);

	void addLoadedAllDataHandler(VideoPlayerControlHandler handler);

	void addDurationChangeHandler(VideoPlayerControlHandler handler);
}
