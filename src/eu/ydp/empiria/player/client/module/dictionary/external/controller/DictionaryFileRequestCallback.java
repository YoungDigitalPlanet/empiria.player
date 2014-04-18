package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.model.EntryFactory;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.xml.IXMLParser;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileResponse;

public class DictionaryFileRequestCallback implements FileRequestCallback {

	private static final int ENTRIES_PER_FILE = 50;
	private final int index;
	private final boolean playSound;
	private final ExplanationListener explanationListener;
	private final Logger logger;
	private final EntryFactory entryFactory;

	@Inject
	public DictionaryFileRequestCallback(@Assisted int index, @Assisted boolean playSound, ExplanationListener explanationListener, IXMLParser xmlParser,
			EntryFactory entryFactory, Logger logger) {

		this.index = index;
		this.playSound = playSound;
		this.explanationListener = explanationListener;
		this.entryFactory = entryFactory;
		this.logger = logger;
	}

	@Override
	public void onResponseReceived(FileRequest request, FileResponse response) {
		Entry entry = createEntryFromResponse(response);

		explanationListener.onEntryLoaded(entry, playSound);
	}

	private Entry createEntryFromResponse(FileResponse response) {
		String responseText = response.getText();
		int positionInFile = index % ENTRIES_PER_FILE;

		return entryFactory.createEntryFromXMLString(responseText, positionInFile);
	}

	@Override
	public void onError(FileRequest request, Throwable exception) {
		logger.error(exception);
	}
}
