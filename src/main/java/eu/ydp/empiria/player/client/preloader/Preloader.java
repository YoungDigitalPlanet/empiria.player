package eu.ydp.empiria.player.client.preloader;

import javax.annotation.PostConstruct;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.preloader.view.ProgressView;

public class Preloader implements IsWidget {

	@Inject
	private ProgressView progressView;

	public void setPreloaderSize(int width, int height) {
		Widget containerWidget = asWidget();
		containerWidget.setWidth(width + "px");
		containerWidget.setHeight(height + "px");
	}

	@PostConstruct
	public void postConstruct() {
		hide();
	}

	public void show() {
		asWidget().setVisible(true);
	}

	public void hide() {
		asWidget().setVisible(false);
	}

	@Override
	public Widget asWidget() {
		return progressView.asWidget();
	}

}
