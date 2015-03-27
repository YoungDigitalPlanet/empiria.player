package eu.ydp.empiria.player.client.module.speechscore.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class SpeechScoreLinkViewImpl extends Composite implements SpeechScoreLinkView {

	@UiTemplate("SpeechScoreLinkView.ui.xml")
	interface SpeechScoreViewUiBinder extends UiBinder<FlowPanel, SpeechScoreLinkViewImpl> {
	}

	private static SpeechScoreViewUiBinder uiBinder = GWT.create(SpeechScoreViewUiBinder.class);

	public SpeechScoreLinkViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Anchor link;

	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;

	@Override
	public void addHandler(Command command) {
		userInteractionHandlerFactory.createUserClickHandler(command)
		                             .apply(link);
	}

	@Override
	public void buildLink(String linkText, String href) {
		link.setText(linkText);
		link.setHref(href);
	}
}