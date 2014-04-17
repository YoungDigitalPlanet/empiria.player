package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import java.util.Arrays;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.util.DocumentLoader;
import eu.ydp.empiria.player.client.module.dictionary.external.util.DocumentLoadingListener;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;

public class EntriesController implements EntriesSocket, DocumentLoadingListener {

	private static final String RELATIVE_EXPLANATIONS_DIR = "dictionary/explanations/";

	private final String EXPLANATIONS_DIR;

	@Inject
	private Provider<ExplanationListener> listenerProvider;

	private int lastIndex;
	private boolean lastPlaySound;

	@Inject
	public EntriesController(EmpiriaPaths empiriaPaths) {
		String commonsPath = empiriaPaths.getCommonsPath();
		EXPLANATIONS_DIR = commonsPath + RELATIVE_EXPLANATIONS_DIR;
	}

	@Override
	public void loadEntry(String password, int index, boolean playSound) {
		lastIndex = index;
		lastPlaySound = playSound;
		final String path = EXPLANATIONS_DIR + intToString(new Integer(index / 50) * 50, 5) + ".xml";
		Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

			@Override
			public void execute() {
				DocumentLoader.load(path, EntriesController.this);
			}
		});
	}

	@Override
	public void onDocumentLoaded(String text) {
		Document document = XMLParser.parse(text);
		Element element = (Element) document.getElementsByTagName("word").item(lastIndex % 50);

		Entry e = new Entry(element);

		listenerProvider.get().onEntryLoaded(e, lastPlaySound);
	}

	@Override
	public void onDocumentLoadError(String error) {
		Window.alert("Error loading entry:\n" + error);
	}

	private static String intToString(int num, int digits) {
		char[] zeros = new char[digits];
		Arrays.fill(zeros, '0');
		NumberFormat df = NumberFormat.getFormat(String.valueOf(zeros));
		return df.format(num);
	}
}
