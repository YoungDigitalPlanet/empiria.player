package eu.ydp.empiria.player.client.module.video.view;

public interface VideoPlayerCommon {

	void play();

	void pause();

	void setCurrentTime(float position);
	
	float getCurrentTime();

	void addPlayHandler(VideoPlayerHandler handler);

	void addPauseHandler(VideoPlayerHandler handler);

	void addEndedHandler(VideoPlayerHandler handler);

	void addTimeUpdateHandler(VideoPlayerHandler handler);

	void addLoadStartHandler(VideoPlayerHandler handler);

	void addLoadedMetadataHandler(VideoPlayerHandler handler);

	void addLoadedDataHandler(VideoPlayerHandler handler);

	void addLoadedAllDataHandler(VideoPlayerHandler handler);

	void addDurationChangeHandler(VideoPlayerHandler handler);
}
