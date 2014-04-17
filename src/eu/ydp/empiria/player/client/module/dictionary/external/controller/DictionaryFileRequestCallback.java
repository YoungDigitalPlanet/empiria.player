package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.xml.IXMLParser;
import eu.ydp.jsfilerequest.client.FileRequest;
import eu.ydp.jsfilerequest.client.FileRequestCallback;
import eu.ydp.jsfilerequest.client.FileResponse;

public class DictionaryFileRequestCallback implements FileRequestCallback {

	private final int index;
	private final boolean playSound;
	private final Provider<ExplanationListener> listenerProvider;
	private final IXMLParser xmlParser;
	private final Logger logger;

	@Inject
	public DictionaryFileRequestCallback(@Assisted int index,
			@Assisted boolean playSound,
			Provider<ExplanationListener> listenerProvider,
			IXMLParser xmlParser, Logger logger) {

		this.index = index;
		this.playSound = playSound;
		this.listenerProvider = listenerProvider;
		this.xmlParser = xmlParser;
		this.logger = logger;
	}

	@Override
	public void onResponseReceived(FileRequest request, FileResponse response) {
		String responseText = response.getText();
		Entry entry = createElement(responseText);

		listenerProvider.get().onEntryLoaded(entry, playSound);
	}

	private Entry createElement(String response) {
		Document document = xmlParser.parse(response);
		Element element = (Element) document.getElementsByTagName("word").item(
				index % 50);
		return new Entry(element);
	}

	@Override
	public void onError(FileRequest request, Throwable exception) {
		logger.error(exception);
	}

}
