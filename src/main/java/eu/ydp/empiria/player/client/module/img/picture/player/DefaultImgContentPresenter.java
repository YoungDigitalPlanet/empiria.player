package eu.ydp.empiria.player.client.module.img.picture.player;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.ConsoleLog;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.media.button.PicturePlayerFullScreenMediaButton;

public class DefaultImgContentPresenter {

	private PicturePlayerFullScreenMediaButton fullScreenMediaButton;
	private DefaultImgContentView view;
	private FullScreenButton fullScreenButton;

	@Inject
	public DefaultImgContentPresenter(PicturePlayerFullScreenMediaButton fullScreenMediaButton, DefaultImgContentView view, FullScreenButton fullScreenButton) {
		this.fullScreenMediaButton = fullScreenMediaButton;
		this.view = view;
		this.fullScreenButton = fullScreenButton;
	}

	public void init(PicturePlayerBean bean) {

		ConsoleLog.alert(bean.getFullscreenMode());
		ConsoleLog.alert(bean.getSrc());
		ConsoleLog.alert(bean.getSrcFullScreen());

		String title = "";
		if (bean.getImgTitleBean() != null) {
			title = bean.getImgTitleBean().getTitleName().getValue().toString();
		}

		view.setImage(title, bean.getSrc());
		initFullScreenMediaButton(element);
	}

	private void initFullScreenMediaButton(Element element) {
		if (PicturePlayerFullScreenMediaButton.isSupported(element) && !template) {
			fullScreenMediaButton.init(element);
			view.addToContainer(fullScreenMediaButton);
		}
	}

	public Widget getView() {
		return view.asWidget();
	}

}
