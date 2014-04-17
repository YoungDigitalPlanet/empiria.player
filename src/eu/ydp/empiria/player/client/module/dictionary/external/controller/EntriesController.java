package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.scheduler.Scheduler;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

public class EntriesController implements FileRequestCallback {

	private static final String RELATIVE_EXPLANATIONS_DIR = "dictionary/explanations/";

	private final String EXPLANATIONS_DIR;

	@Inject
	private Provider<ExplanationListener> listenerProvider;
	@Inject
	private Scheduler scheduler;
	@Inject
	private Logger logger;

	private int lastIndex;
	private boolean lastPlaySound;

	@Inject
	public EntriesController(EmpiriaPaths empiriaPaths) {
		String commonsPath = empiriaPaths.getCommonsPath();
		EXPLANATIONS_DIR = commonsPath + RELATIVE_EXPLANATIONS_DIR;
	}

	public void loadEntry(String password, int index, boolean playSound) {
		lastIndex = index;
		lastPlaySound = playSound;
		final String path = getFilePathForIndex(index);

		scheduler.scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				FileRequest fileRequest = GWT.create(FileRequest.class);
				try {
					fileRequest.setUrl(path);
					fileRequest.send(null, EntriesController.this);
				} catch (FileRequestException exception) {
					logger.error(exception);
				}
			}
		});
	}

	private String getFilePathForIndex(int index) {
		return EXPLANATIONS_DIR + formatNumber(calculateOffset(index)) + ".xml";
	}

	private int calculateOffset(int index) {
		return new Integer(index / 50) * 50;
	}

	private String formatNumber(int num) {
		return NumberFormat.getFormat("00000").format(num);
	}

	@Override
	public void onResponseReceived(FileRequest request, FileResponse response) {
		Document document = XMLParser.parse(response.getText());
		Element element = (Element) document.getElementsByTagName("word").item(
				lastIndex % 50);

		Entry e = new Entry(element);

		listenerProvider.get().onEntryLoaded(e, lastPlaySound);
	}

	@Override
	public void onError(FileRequest request, Throwable exception) {
		logger.error(exception);
	}
}
