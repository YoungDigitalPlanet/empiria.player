package eu.ydp.empiria.player.client.module.video;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.video.model.VideoModel;
import eu.ydp.empiria.player.client.module.video.model.VideoXmlParser;
import eu.ydp.empiria.player.client.module.video.presenter.VideoPresenter;

public class VideoModule extends SimpleModuleBase implements ISimpleModule {

	@Inject
	private VideoPresenter presenter;
	@Inject
	private VideoXmlParser videoJsXmlParser;

	@Override
	public Widget getView() {
		return presenter.getView();
	}

	@Override
	protected void initModule(Element element) {
		VideoModel videoJsModel = videoJsXmlParser.parse(element);
		presenter.start(videoJsModel);
	}

}
