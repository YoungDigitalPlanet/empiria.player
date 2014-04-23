package eu.ydp.empiria.player.client.module.dictionary.external.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.dictionary.external.controller.Options;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.ViewType;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;

public class MainView extends SimplePanel {

	private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}

	@Inject
	@UiField(provided = true)
	MenuView menuView;
	@Inject
	@UiField(provided = true)
	ExplanationView explanationView;
	@UiField
	FlowPanel containerView;
	@UiField
	PushButton exitButton;

	public void init() {
		if (Options.getViewType() == ViewType.HALF) {
			explanationView.hide();
		}
		setWidget(uiBinder.createAndBindUi(this));
		menuView.init();
	}

	@UiHandler("exitButton")
	public void onClick(ClickEvent event) {
		exitJs();
	}

	public void showExplanation(Entry e, boolean playSound) {
		explanationView.displayEntry(e, playSound);
		if (Options.getViewType() == ViewType.HALF) {
			menuView.hide();
			Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

				@Override
				public void execute() {
					explanationView.show();
				}
			});
		}
	}

	public void hideExplanation() {
		if (Options.getViewType() == ViewType.HALF) {
			explanationView.hideAndStopSound();
			menuView.show();
		}
	}

	private native void exitJs()/*-{
		if (typeof $wnd.dictionaryExit == 'function') {
			$wnd.dictionaryExit();
		}
	}-*/;
}
