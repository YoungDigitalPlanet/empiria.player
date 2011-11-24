package eu.ydp.empiria.player.client.controller.data;

import java.util.Vector;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class ItemDataSource {

	public ItemDataSource(XMLData d){
		data = d;
		styleDeclaration = new StyleLinkDeclaration(data.getDocument().getElementsByTagName("styleDeclaration"), data.getBaseURL());
		Node rootNode = data.getDocument().getElementsByTagName("assessmentItem").item(0);
		title = ((Element)rootNode).getAttribute("title");
		errorMessage = "";
	}
	
	public ItemDataSource(String err){
		String detail = "";
		if (err.indexOf(":") != -1)
			detail = err.substring(0, err.indexOf(":"));
		errorMessage = LocalePublisher.getText(LocaleVariable.ERROR_ITEM_FAILED_TO_LOAD) + detail;
	}
	
	private XMLData data;
	private StyleLinkDeclaration styleDeclaration;
	private String title;
	private String errorMessage;

	public XMLData getItemData(){
		return data;
	}
	
	public Vector<String> getStyleLinksForUserAgent(String userAgent){
		if (styleDeclaration != null)
			return styleDeclaration.getStyleLinksForUserAgent(userAgent);
		return new Vector<String>();
	}

	public boolean isError(){
		return errorMessage.length() > 0;
	}

	public String getErrorMessage(){
		return errorMessage;
	}
	
	public String getTitle(){
		if (title != null)
			return title;
		else
			return "";
	}

}

