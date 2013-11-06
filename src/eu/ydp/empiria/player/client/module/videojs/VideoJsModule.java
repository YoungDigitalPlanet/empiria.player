package eu.ydp.empiria.player.client.module.videojs;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.videojs.model.VideoJsModel;
import eu.ydp.empiria.player.client.module.videojs.model.VideoJsXmlParser;
import eu.ydp.empiria.player.client.module.videojs.presenter.VideoJsPresenter;

public class VideoJsModule extends SimpleModuleBase implements ISimpleModule {

	@Inject
	private VideoJsPresenter presenter;
	@Inject
	private VideoJsXmlParser videoJsXmlParser;

	@Override
	public Widget getView() {
		return presenter.getView();
	}

	@Override
	protected void initModule(Element element) {
		VideoJsModel videoJsModel = videoJsXmlParser.parse(element);
		presenter.start(videoJsModel);
	}

}
