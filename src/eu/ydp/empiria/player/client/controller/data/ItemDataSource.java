package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;
import eu.ydp.empiria.player.client.module.item.ReportFeedbacksParser;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;

import java.util.ArrayList;
import java.util.List;

public class ItemDataSource {

	private XmlData data;
	private StyleLinkDeclaration styleDeclaration;
	private String title;
	private ReportFeedbacksParser reportFeedbacksParser = new ReportFeedbacksParser();
	private ProgressToStringRangeMap reportFeedbacks;
	private final String errorMessage;

	public ItemDataSource(XmlData d) {
		data = d;
		styleDeclaration = new StyleLinkDeclaration(data.getDocument().getElementsByTagName("styleDeclaration"), data.getBaseURL());
		Node rootNode = data.getDocument().getElementsByTagName("assessmentItem").item(0);
		Element rootElement = (Element) rootNode;
		title = rootElement.getAttribute("title");
		NodeList feedbacksNodeList = rootElement.getElementsByTagName("reportFeedbackText");
		this.reportFeedbacks = reportFeedbacksParser.parse(feedbacksNodeList);
		errorMessage = "";
	}

	public ItemDataSource(String err) {
		String detail = "";
		if (err.indexOf(":") != -1) {
			detail = err.substring(0, err.indexOf(":"));
		}
		errorMessage = LocalePublisher.getText(LocaleVariable.ERROR_ITEM_FAILED_TO_LOAD) + detail;
	}

	public XmlData getItemData() {
		return data;
	}

	public List<String> getStyleLinksForUserAgent(String userAgent) {
		if (styleDeclaration != null) {
			return styleDeclaration.getStyleLinksForUserAgent(userAgent);
		}
		return new ArrayList<String>();
	}

	public boolean isError() {
		return errorMessage.length() > 0;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getTitle() {
		if (title != null) {
			return title;
		} else {
			return "";
		}
	}

	public ProgressToStringRangeMap getFeedbacks() {
		return reportFeedbacks;
	}
}
