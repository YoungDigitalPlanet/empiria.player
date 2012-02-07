package eu.ydp.empiria.player.client.controller.data;

import java.util.Vector;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.data.events.AssessmentDataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLink;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class AssessmentDataSourceManager {

	public AssessmentDataSourceManager(AssessmentDataLoaderEventListener l){
		listener = l;
		itemsCount = -1;
		errorMessage = "";
	}
	
	private XMLData data;
	private AssessmentDataLoaderEventListener listener;
	private StyleLinkDeclaration styleDeclaration;
	private int itemsCount;
	private String errorMessage;
	private LibraryLink libraryLink;
	
	public void setAssessmentData(XMLData d){
		data = d;
		itemsCount = -1;
		styleDeclaration = new StyleLinkDeclaration(data.getDocument().getElementsByTagName("styleDeclaration"), data.getBaseURL());
		libraryLink = new LibraryLink(data.getDocument().getElementsByTagName("extensionsLibrary"), data.getBaseURL());
		listener.onAssessmentDataLoaded();
	}
	
	public void setAssessmentLoadingError(String err){
		String detail = "";
		if (err.indexOf(":") != -1)
			detail = err.substring(0, err.indexOf(":"));
		errorMessage = LocalePublisher.getText(LocaleVariable.ERROR_ASSESSMENT_FAILED_TO_LOAD) + detail;
	}
	
	public void setAssessmentDefaultData(){
		data = new XMLData(XMLParser.parse("<assessmentTest title=\"\"/>"), "");
		itemsCount = 1;
		styleDeclaration = new StyleLinkDeclaration(data.getDocument().getElementsByTagName("styleDeclaration"), data.getBaseURL());
		listener.onAssessmentDataLoaded();
	}
	
	public XMLData getAssessmentData(){
		return data;
	}
	
	public boolean isError(){
		return errorMessage.length() > 0;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}
	
	public boolean hasLibrary(){
		return libraryLink.hasLink();
	}
	
	public String getLibraryLink(){
		return libraryLink.getLink();
	}
	
	public String getAssessmentTitle(){
		String title = "";
		if (data != null){
			try{
				Node rootNode = data.getDocument().getElementsByTagName("assessmentTest").item(0);
				title = ((Element)rootNode).getAttribute("title");
			} catch (Exception e) {
			}
			
		}
		return title;
	}
	
	public String[] getItemUrls(){
		
		String[] itemUrls = new String[0];
		
		if (data != null){
			try {
				NodeList nodes = data.getDocument().getElementsByTagName("assessmentItemRef");
				String[] tmpItemUrls = new String[nodes.getLength()];
				for(int i = 0; i < nodes.getLength(); i++){
					Node itemRefNode = nodes.item(i);
					
					if (((Element)itemRefNode).getAttribute("href").startsWith("http"))
						tmpItemUrls[i] = ((Element)itemRefNode).getAttribute("href");
					else
						tmpItemUrls[i] = data.getBaseURL() + ((Element)itemRefNode).getAttribute("href");
			    }
				itemUrls = tmpItemUrls;
			} catch (Exception e) {	}
		}
		
		//TODO
		return itemUrls;
	}
	
	public int getItemsCount(){
		if (itemsCount == -1)
			itemsCount = getItemUrls().length;
		
		return itemsCount;
	}
	
	public Vector<String> getStyleLinksForUserAgent(String userAgent){
		if (styleDeclaration != null)
			return styleDeclaration.getStyleLinksForUserAgent(userAgent);
		return new Vector<String>();
	}
}
