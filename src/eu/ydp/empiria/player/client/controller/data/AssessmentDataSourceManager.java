package eu.ydp.empiria.player.client.controller.data;

import com.google.common.base.Optional;
import com.google.gwt.xml.client.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
import eu.ydp.empiria.player.client.controller.data.events.AssessmentDataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.SkinDataLoaderListener;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLink;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.extensions.internal.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDataSourceManager implements SkinDataLoaderListener {

	public AssessmentDataSourceManager() {
		itemsCount = -1;
		errorMessage = "";
		skinData = new SkinDataSourceManager(this);
	}

	private XmlData data;
	private AssessmentData assessmentData;
	private AssessmentDataLoaderEventListener listener;
	private StyleLinkDeclaration styleDeclaration;
	private int itemsCount;
	private String errorMessage;
	private LibraryLink libraryLink;
	private final SkinDataSourceManager skinData;
	private boolean isDefaultData;
	private List<Element> items = null;

	@Inject
	private WorkModeParserForAssessment workModeParser;
	@Inject
	private PlayerWorkModeService playerWorkModeService;

	public void setSkinListener(AssessmentDataLoaderEventListener listener) {
		this.listener = listener;
	}

	public void initializeAssessmentData(XmlData data) {
		if (isItemDocument(data.getDocument())) {
			isDefaultData = true;
			initializeDefaultData();
		} else {
			isDefaultData = false;
			initializeData(data);
		}
	}

	public void setAssessmentData(XmlData data) {
		this.data = data;
		this.assessmentData = new AssessmentData(data, null);
	}

	public void setAssessmentLoadingError(String err) {
		String detail = "";
		if (err != null && err.indexOf(':') != -1) {
			detail = err.substring(0, err.indexOf(':'));
		}
		errorMessage = LocalePublisher.getText(LocaleVariable.ERROR_ASSESSMENT_FAILED_TO_LOAD) + detail;
	}

	private void initializeDefaultData() {
		data = new XmlData(XMLParser.parse("<assessmentTest title=\"\"/>"), "");
		itemsCount = 1;
		styleDeclaration = new StyleLinkDeclaration(data.getDocument()
		                                                .getElementsByTagName("styleDeclaration"), data.getBaseURL());
		listener.onAssessmentDataLoaded();
	}

	private void initializeData(XmlData xmlData) {
		String skinUrl = getSkinUrl(xmlData.getDocument());

		data = xmlData;
		itemsCount = -1;
		styleDeclaration = new StyleLinkDeclaration(data.getDocument()
		                                                .getElementsByTagName("styleDeclaration"), data.getBaseURL());
		libraryLink = new LibraryLink(data.getDocument()
		                                  .getElementsByTagName("extensionsLibrary"), data.getBaseURL());
		setWorkMode();

		if (skinUrl == null) {
			assessmentData = new AssessmentData(data, null);
			listener.onAssessmentDataLoaded();
		} else {
			skinUrl = data.getBaseURL()
			              .concat(skinUrl);

			skinData.load(skinUrl);
		}
	}

	private void setWorkMode() {
		Optional<PlayerWorkMode> workMode = workModeParser.parse(data);
		if (workMode.isPresent()) {
			playerWorkModeService.updateWorkMode(workMode.get());
		}
	}

	public XmlData getAssessmentXMLData() {
		return data;
	}

	public AssessmentData getAssessmentData() {
		return assessmentData;
	}

	public boolean isError() {
		return errorMessage.length() > 0;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public boolean hasLibrary() {
		return libraryLink.hasLink();
	}

	public String getLibraryLink() {
		return libraryLink.getLink();
	}

	public boolean isDefaultData() {
		return isDefaultData;
	}

	public String getAssessmentTitle() {
		String title = "";
		if (data != null) {
			try {
				Node rootNode = data.getDocument()
				                    .getElementsByTagName("assessmentTest")
				                    .item(0);
				title = ((Element) rootNode).getAttribute("title");
			} catch (Exception e) {
			}

		}
		return title;
	}

	public String[] getItemUrls() {

		String[] itemUrls = new String[0];

		if (data != null) {
			try {
				NodeList nodes = data.getDocument()
				                     .getElementsByTagName("assessmentItemRef");
				String[] tmpItemUrls = new String[nodes.getLength()];
				for (int i = 0; i < nodes.getLength(); i++) {
					Node itemRefNode = nodes.item(i);

					String name = "href";
					if (((Element) itemRefNode).getAttribute(name)
					                           .startsWith("http")) {
						tmpItemUrls[i] = ((Element) itemRefNode).getAttribute(name);
					} else {
						tmpItemUrls[i] = data.getBaseURL() + ((Element) itemRefNode).getAttribute(name);
					}
				}
				itemUrls = tmpItemUrls;
			} catch (Exception e) {// NOPMD
			}
		}

		return itemUrls;
	}

	/**
	 * Zwraca wezel assessmentItemRef o wskazanym id
	 *
	 * @param index index wezla
	 * @return Element lub null gdy element o podanym indeksie nie istnieje
	 */
	public Element getItem(int index) {
		if (data != null && items == null) {
			try {
				items = new ArrayList<Element>();
				NodeList nodes = data.getDocument()
				                     .getElementsByTagName("assessmentItemRef");
				for (int x = 0; x < nodes.getLength(); ++x) {
					Node n = nodes.item(x);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						items.add((Element) n);
					}
				}
			} catch (Exception e) {

			}
		}
		if (items != null && index < items.size() && index > -1) {
			return items.get(index);
		}
		return null;
	}

	public int getItemsCount() {
		if (itemsCount == -1) {
			itemsCount = getItemUrls().length;
		}

		return itemsCount;
	}

	public List<String> getStyleLinksForUserAgent(String userAgent) {
		List<String> declarations = new ArrayList<String>();

		if (styleDeclaration != null) {
			declarations.addAll(styleDeclaration.getStyleLinksForUserAgent(userAgent));
		}

		declarations.addAll(skinData.getStyleLinksForUserAgent(userAgent));

		return declarations;
	}

	@Override
	public void onSkinLoad() {
		assessmentData = new AssessmentData(data, skinData.getSkinData());
		listener.onAssessmentDataLoaded();
	}

	@Override
	public void onSkinLoadError() {
		assessmentData = new AssessmentData(data, null);
		listener.onAssessmentDataLoaded();
	}

	private String getSkinUrl(Document document) {
		String url = null;

		try {
			Node testViewNode = document.getElementsByTagName("testView")
			                            .item(0);
			url = testViewNode.getAttributes()
			                  .getNamedItem("href")
			                  .getNodeValue();
		} catch (Exception e) {
		}

		return url;
	}

	private boolean isItemDocument(Document doc) {
		try {
			return doc.getDocumentElement()
			          .getNodeName()
			          .equals("assessmentItem");
		} catch (Exception e) {
		}
		return true;
	}

}
