package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.data.events.SkinDataLoaderListener;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.file.xml.XmlDocument;
import eu.ydp.gwtutil.client.collections.QueueSet;

public class SkinDataSourceManager {
	
	private SkinDataLoaderListener loadListener;
	
	private StyleLinkDeclaration styleDeclaration;
	
	private MetaDescriptionManager metaDescriptionManager;
	
	private XmlData skinData;
	
	public SkinDataSourceManager(SkinDataLoaderListener listener){
		loadListener = listener;
		metaDescriptionManager = new MetaDescriptionManager();
	}
	
	public void load(String url){
		new XmlDocument(url, new DocumentLoadCallback<Document>() {
			
			@Override
			public void loadingError(String error) {
				loadListener.onSkinLoadError();
			}
			
			@Override
			public void finishedLoading(Document document, String baseURL) {
				prepareSkin(new XmlData(document, baseURL));
			}
		});
	}
	
	public Node getBodyNode(){
		return skinData.getDocument().getElementsByTagName("itemBody").item(0);
	}
	
	public XmlData getSkinData(){
		return skinData;
	}
	
	public QueueSet<String> getStyleLinksForUserAgent(String userAgent){
		if(styleDeclaration != null)
			return styleDeclaration.getStyleLinksForUserAgent(userAgent);
		return new QueueSet<String>();
	}
	
	private void prepareSkin(XmlData data){
		Document document = data.getDocument();
		
		skinData = data;
		styleDeclaration = new StyleLinkDeclaration(document.getElementsByTagName("styleDeclaration"), data.getBaseURL());
		metaDescriptionManager.processDocument(document);
		
		loadListener.onSkinLoad();
	}
}
