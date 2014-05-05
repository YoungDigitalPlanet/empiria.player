package eu.ydp.empiria.player.client.module.dictionary.external.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.media.SoundPlayer;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.ExplanationListener;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;

public class ExplanationView extends Composite implements MediaEventHandler {

	private static ExplanationViewUiBinder uiBinder = GWT.create(ExplanationViewUiBinder.class);

	interface ExplanationViewUiBinder extends UiBinder<Widget, ExplanationView> {
	}

	@Inject
	private StyleNameConstants styleNameConstants;

	@Inject
	private EmpiriaPaths empiriaPaths;

	@Inject
	private SoundPlayer soundPlayer;

	@UiField
	Panel typePanel;
	@UiField
	Panel entryPanel;
	@UiField
	Panel entryDescriptionPanel;
	@UiField
	Panel entryExamplePanel;

	@UiField
	Label typeLabel;
	@UiField
	Label entryLabel;
	@UiField
	InlineHTML entryDescriptionLabel;
	@UiField
	Label entryExampleLabel;

	@UiField
	PushButton backButton;
	@UiField
	PushButton playButton;

	private String descrSound;
	private boolean playingDescr;

	@Inject
	private Provider<ExplanationListener> listenerProvider;

	public ExplanationView() {
		initWidget(uiBinder.createAndBindUi(this));
		playingDescr = false;
		// soundPlayer.addExternalHandler(this);
	}

	@UiHandler("backButton")
	public void backButtonClick(ClickEvent event) {
		listenerProvider.get().onBackClick();
	}

	@UiHandler("entryExamplePanel")
	public void descPanelMouseUp(MouseUpEvent event) {
		onPlayDescrClick();
	}

	@UiHandler("playButton")
	public void playButtonClick(ClickEvent event) {
		onPlayDescrClick();
	}

	public void displayEntry(Entry entry, boolean isPlaySound) {
		if (entry != null) {
			typeLabel.setText(entry.getType());
			entryLabel.setText(entry.getEntry());
			entryDescriptionLabel.setHTML(entry.getEntryDescription());
			entryExampleLabel.setText(entry.getEntryExample());
			descrSound = entry.getEntryExampleSound();
			if (isPlaySound) {
				play(entry.getEntrySound());
			}
		}
	}

	public void show() {
		Style style = getElement().getStyle();
		style.setDisplay(Display.BLOCK);
	}

	public void hide() {
		Style style = getElement().getStyle();
		style.setDisplay(Display.NONE);
		stopDescrSound();
	}

	public void hideAndStopSound() {
		hide();
		stopDescrSound();
	}

	private void onPlayDescrClick() {
		if (playingDescr) {
			stopDescrSound();
		} else {
			playDescrSound();
		}
	}

	private void playDescrSound() {
		if (descrSound != null && !descrSound.equals("")) {
			play(descrSound);
			playButton.setStylePrimaryName(styleNameConstants.QP_DICTIONARY_EXPLANATION_PLAY_BUTTON_PLAYING());
			playingDescr = true;
		}
	}

	public void stopDescrSound() {
		stop();
		playButton.setStylePrimaryName(styleNameConstants.QP_DICTIONARY_EXPLANATION_PLAY_BUTTON());
		playingDescr = false;
	}

	private void play(String file) {
		String path = empiriaPaths.getCommonsFilePath("dictionary") + "/media/" + "audio.mp3";
		soundPlayer.play(path);
	}

	private void stop() {
		soundPlayer.stop();
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		switch (event.getType()) {
		case ON_END:
			playButton.setStylePrimaryName(styleNameConstants.QP_DICTIONARY_EXPLANATION_PLAY_BUTTON());
			playingDescr = false;
		default:
			break;
		}
	}

}
