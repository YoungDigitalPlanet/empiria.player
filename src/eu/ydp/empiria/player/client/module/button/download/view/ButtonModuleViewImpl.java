package eu.ydp.empiria.player.client.module.button.download.view;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ButtonModuleViewImpl extends Composite implements ButtonModuleView {

	private static ButtonModuleViewUiBinder uiBinder = GWT.create(ButtonModuleViewUiBinder.class);

	interface ButtonModuleViewUiBinder extends UiBinder<Widget, ButtonModuleViewImpl> {}

	@UiField FlowPanel description;
	@UiField Anchor anchor;

	@PostConstruct
	public void postConstruct() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setUrl(String url){
		anchor.setHref(url);

	}

	@Override
	public void setDescription(String description) {
		this.description.getElement().setInnerText(description);
	}

	@Override
	public void setId(String id) {
		getElement().setId(id);
	}

}
