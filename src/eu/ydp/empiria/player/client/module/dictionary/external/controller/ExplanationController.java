package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.IsWidget;

import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;

public class ExplanationController {

	private final ExplanationView explanationView;

	private String descrSound;
	private final ExplanationEntrySoundController explanationEntrySoundController;
	private final ExplanationDescriptionSoundController explanationDescriptionSoundController;

	@Inject
	public ExplanationController(ExplanationView explanationView, ExplanationEntrySoundController explanationEntrySoundController,
			DictionaryModuleFactory dictionaryModuleFactory) {
		this.explanationView = explanationView;
		this.explanationEntrySoundController = explanationEntrySoundController;
		this.explanationDescriptionSoundController = dictionaryModuleFactory.getExplanationDescriptionSoundController(explanationView);
	}

	public void init() {
		addPlayButtonHandler();
		addEntryExamplePanelHandler();
	}

	public void processEntryAndPlaySound(Entry entry) {
		processEntry(entry);
		explanationEntrySoundController.playEntrySound(entry.getEntrySound());
	}

	public void processEntry(Entry entry) {
		explanationView.processEntry(entry);
		descrSound = entry.getEntryExampleSound();
	}

	public void show() {
		explanationView.show();
	}

	public void hide() {
		explanationDescriptionSoundController.stop();
		explanationView.hide();
	}

	public IsWidget getView() {
		return explanationView;
	}

	private void addEntryExamplePanelHandler() {
		this.explanationView.addEntryExamplePanelHandler(new MouseUpHandler() {

			@Override
			public void onMouseUp(MouseUpEvent event) {
				explanationDescriptionSoundController.playOrStopDescriptionSound(descrSound);
			}
		});
	}

	private void addPlayButtonHandler() {
		this.explanationView.addPlayButtonHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				explanationDescriptionSoundController.playOrStopDescriptionSound(descrSound);
			}
		});
	}
}
