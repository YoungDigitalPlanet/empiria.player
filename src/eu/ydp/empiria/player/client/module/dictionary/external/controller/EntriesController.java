package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.dictionary.external.controller.filename.DictionaryFilenameProvider;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.scheduler.Scheduler;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileRequestException;
import eu.ydp.jsfilerequest.client.FileResponse;

public class EntriesController implements FileRequestCallback {

	@Inject
	private Provider<ExplanationListener> listenerProvider;
	@Inject
	private Scheduler scheduler;
	@Inject
	private DictionaryFilenameProvider dictionaryFilenameProvider;
	@Inject
	private Logger logger;

	private int lastIndex;
	private boolean lastPlaySound;

	public void loadEntry(String password, int index, boolean playSound) {
		lastIndex = index;
		lastPlaySound = playSound;
		final String path = dictionaryFilenameProvider
				.getFilePathForIndex(index);

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
