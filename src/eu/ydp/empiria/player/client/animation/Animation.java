package eu.ydp.empiria.player.client.animation;

public interface Animation {

	void start(AnimationEndHandler handler);
	void terminate();
}
