package eu.ydp.empiria.player.client.module.button.download.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.assets.AssetOpenDelegatorService;
import eu.ydp.empiria.player.client.module.button.download.structure.ButtonBean;
import eu.ydp.empiria.player.client.module.button.download.view.ButtonModuleView;

public class ButtonModulePresenter implements IsWidget {

	@Inject
	private ButtonModuleView view;
	@Inject
	private AssetOpenDelegatorService assetOpenDelegatorService;
	private ButtonBean buttonBean;

	public void init() {
		view.setUrl(buttonBean.getHref());
		view.setDescription(buttonBean.getAlt());
		view.setId(buttonBean.getId());
		addClickHandler();
	}

	private void addClickHandler() {
		view.addAnchorClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String path = buttonBean.getHref();
				assetOpenDelegatorService.open(path);
			}
		});
	}

	public void setBean(ButtonBean buttonBean) {
		this.buttonBean = buttonBean;
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}
}
