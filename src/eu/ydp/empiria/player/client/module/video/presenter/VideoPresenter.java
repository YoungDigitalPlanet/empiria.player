package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class VideoPresenter {

	private final VideoPlayerFactory videoPlayerFactory;
	private final VideoBean videoBean;
	private final VideoView view;
	private final EventsBus eventsBus;

	private boolean isLoaded = false;

	@Inject
	public VideoPresenter(VideoPlayerFactory videoPlayerFactory, @ModuleScoped VideoBean videoBean, @ModuleScoped VideoView view, EventsBus eventsBus) {
		this.videoPlayerFactory = videoPlayerFactory;
		this.videoBean = videoBean;
		this.view = view;
		this.eventsBus = eventsBus;
	}

	public void start() {
		view.createView();
		createAndAttachVideoPlayer();
		addOnLoadHandler();
	}

	private void addOnLoadHandler() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if (isLoaded) {
					createAndAttachVideoPlayer();
				} else {
					isLoaded = true;
				}
			}
		}, new CurrentPageScope());

	}

	private void createAndAttachVideoPlayer() {
		VideoPlayer videoPlayer = videoPlayerFactory.create(videoBean);
		view.attachVideoPlayer(videoPlayer);
	}

	public Widget getView() {
		return view.asWidget();
	}
}
