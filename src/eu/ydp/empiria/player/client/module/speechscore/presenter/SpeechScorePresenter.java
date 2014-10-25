package eu.ydp.empiria.player.client.module.speechscore.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.assets.URLOpenService;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SpeechScorePresenter {

	@Inject
	@ModuleScoped
	private SpeechScoreLinkView view;

	@Inject
	private URLOpenService urlOpenService;

	@Inject
	private SpeechScoreProtocolProvider protocolProvider;

	public Widget getView() {
		return view.asWidget();
	}

	public void bindUi() {
		view.addHandler(new Command() {
			@Override
			public void execute(NativeEvent nativeEvent) {
				runSpeechScore();
			}
		});
	}

	private void runSpeechScore() {
		String urlWithProtocol = protocolProvider.get() + view.getUrl();
		urlOpenService.open(urlWithProtocol);
	}
}
