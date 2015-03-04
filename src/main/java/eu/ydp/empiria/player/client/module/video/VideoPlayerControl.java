package eu.ydp.empiria.player.client.module.video;

public interface VideoPlayerControl {

	void play();

	void pause();

	void setCurrentTime(float position);

	void reset();

	float getCurrentTime();

	int getWidth();

	void addPlayHandler(VideoPlayerControlHandler handler);

	void addPauseHandler(VideoPlayerControlHandler handler);

	void addEndVideoListener(VideoEndedListener handler);

	void addTimeUpdateHandler(VideoPlayerControlHandler handler);

	void addLoadStartHandler(VideoPlayerControlHandler handler);

	void addLoadedMetadataHandler(VideoPlayerControlHandler handler);

	void addLoadedDataHandler(VideoPlayerControlHandler handler);

	void addLoadedAllDataHandler(VideoPlayerControlHandler handler);

	void addDurationChangeHandler(VideoPlayerControlHandler handler);

	void addFullscreenListener(VideoFullscreenListener videoFullscreenListener);
}
