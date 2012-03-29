package eu.ydp.empiria.player.client.controller.data;

import java.util.Vector;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.data.events.SkinDataLoaderListener;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.util.xml.XMLDocument;
import eu.ydp.empiria.player.client.util.xml.document.IDocumentLoaded;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class SkinDataSourceManager {
	
	private SkinDataLoaderListener loadListener;
	
	private StyleLinkDeclaration styleDeclaration;
	
	private XMLData skinData;
	
	public SkinDataSourceManager(SkinDataLoaderListener listener){
		loadListener = listener;
	}
	
	public void load(String url){
		new XMLDocument(url, new IDocumentLoaded() {
			
			@Override
			public void loadingErrorHandler(String error) {
				loadListener.onSkinLoadError();
			}
			
			@Override
			public void finishedLoading(Document document, String baseURL) {
				prepareSkin(new XMLData(document, baseURL));
			}
		});
	}
	
	public Node getBodyNode(){
		return skinData.getDocument().getElementsByTagName("itemBody").item(0);
	}
	
	public XMLData getSkinData(){
		return skinData;
	}
	
	public Vector<String> getStyleLinksForUserAgent(String userAgent){
		if(styleDeclaration != null)
			return styleDeclaration.getStyleLinksForUserAgent(userAgent);
		return new Vector<String>();
	}
	
	private void prepareSkin(XMLData data){
		Document document = data.getDocument();
		
		skinData = data;
		styleDeclaration = new StyleLinkDeclaration(document.getElementsByTagName("styleDeclaration"), data.getBaseURL());
		
		loadListener.onSkinLoad();
	}
}
