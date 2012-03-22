package eu.ydp.empiria.player.client.controller.data;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.communication.InitialData;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.communication.PageData;
import eu.ydp.empiria.player.client.controller.communication.PageDataError;
import eu.ydp.empiria.player.client.controller.communication.PageDataSummary;
import eu.ydp.empiria.player.client.controller.communication.PageDataTest;
import eu.ydp.empiria.player.client.controller.communication.PageDataToC;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.PageType;
import eu.ydp.empiria.player.client.controller.data.events.AssessmentDataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.ItemDataCollectionLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.library.LibraryExtension;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLoaderListener;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLoader;
import eu.ydp.empiria.player.client.controller.log.OperationLogEvent;
import eu.ydp.empiria.player.client.controller.log.OperationLogManager;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.xml.XMLDocument;
import eu.ydp.empiria.player.client.util.xml.document.IDocumentLoaded;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class DataSourceManager implements AssessmentDataLoaderEventListener, ItemDataCollectionLoaderEventListener, DataSourceDataSupplier, LibraryLoaderListener {
	
	public DataSourceManager(){
		mode = DataSourceManagerMode.NONE;
		assessmentDataManager = new AssessmentDataSourceManager(this);
		itemDataCollectionManager = new ItemDataSourceCollectionManager(this);
		libraryLoader = new LibraryLoader(this);
	}
	
	private StyleDataSourceManager styleDataSourceManager;
	private int styleLoadCounter;
	
	private AssessmentDataSourceManager assessmentDataManager;
	private ItemDataSourceCollectionManager itemDataCollectionManager;
	private LibraryLoader libraryLoader;
	private DataSourceManagerMode mode;
	private DataLoaderEventListener listener;
	
	public InitialData getInitialData(){
		InitialData initialData = new InitialData(itemDataCollectionManager.getItemsCount());
		for (int i = 0 ; i < itemDataCollectionManager.getItemsCount() ; i ++){
			initialData.addItemInitialData(itemDataCollectionManager.getItemInitialData(i));
		}
		return initialData;
	}
	
	public XMLData getAssessmentData(){
		return assessmentDataManager.getAssessmentData();
	}

	public ItemData getItemData(int index){
		return itemDataCollectionManager.getItemData(index);
	}
	
	public StyleSocket getStyleProvider() {
		return getStyleDataSourceManager();
	}
	
	public int getItemsCount(){
		return assessmentDataManager.getItemsCount();
	}
	
	public void loadMainDocument(String url){
		
		if (mode == DataSourceManagerMode.LOADING_ASSESSMENT  ||  mode == DataSourceManagerMode.LOADING_ITEMS)
			return;
		
		OperationLogManager.logEvent(OperationLogEvent.LOADING_STARTED);
		
		mode = DataSourceManagerMode.LOADING_ASSESSMENT;

		String resolvedURL;

		if( GWT.getHostPageBaseURL().startsWith("file://") ){

			String localURL = URL.decode( GWT.getHostPageBaseURL() );
			resolvedURL = localURL + url;
		}
		else if( url.contains("://") || url.startsWith("/") ){
			resolvedURL = url;
		}
		else{
			resolvedURL = GWT.getHostPageBaseURL() + url;
		}

		new eu.ydp.empiria.player.client.util.xml.XMLDocument(resolvedURL, new IDocumentLoaded(){

			public void finishedLoading(Document document, String baseURL) {
				if (!isItemDocument(document)){
					assessmentDataManager.setAssessmentData(new XMLData(document, baseURL));
					if (assessmentDataManager.hasLibrary())
						loadExtensionsLibrary();
					else
						loadItems();
				} else {
					assessmentDataManager.setAssessmentDefaultData();
					loadSingleItemData(new XMLData(document, baseURL));
				}
			}

			@Override
			public void loadingErrorHandler(String error) {
				assessmentDataManager.setAssessmentLoadingError(error);
				onLoadFinished();
			}
		});
	}
	
	public void loadExtensionsLibrary(){
		String libraryUrl = assessmentDataManager.getLibraryLink();
		
		new XMLDocument(libraryUrl, new IDocumentLoaded() {
			
			@Override
			public void finishedLoading(Document document, String baseURL) {
				libraryLoader.load(new XMLData(document, baseURL));
			}
			
			@Override
			public void loadingErrorHandler(String error) {
				onExtensionsLoadFinished();
			}
		});
				
	}
	
	public void onExtensionsLoadFinished(){
		loadItems();
	}
	
	public void loadItems(){
		loadItems(assessmentDataManager.getItemUrls());		
	}
	
	private void loadItems(String[] urls){
		
		mode = DataSourceManagerMode.LOADING_ITEMS;
		
		itemDataCollectionManager.initItemDataCollection(urls.length);
		
		if (urls.length == 0)
			onItemCollectionLoaded();
		
		for (int i = 0 ; i < urls.length ; i ++){
			final int ii = i;
			
			new XMLDocument(urls[ii], new IDocumentLoaded(){

				public void finishedLoading(Document document, String baseURL) {
					itemDataCollectionManager.setItemData(ii, new XMLData(document, baseURL));
				}

				@Override
				public void loadingErrorHandler(String error) {
					itemDataCollectionManager.setItemData(ii, error);
				}
			});
		}
		
	}
	
	private void loadSingleItemData(XMLData itemData){
		itemDataCollectionManager.setItemDataCollection(new XMLData[] {itemData});
	}
	
	public void loadData(XMLData ad, XMLData[] ids){
		assessmentDataManager.setAssessmentData(ad);
		itemDataCollectionManager.setItemDataCollection(ids);
	}
	
	
	public PageData generatePageData(PageReference ref){
		PageData pd;
		
		if (assessmentDataManager.isError()){
			pd = new PageDataError(assessmentDataManager.getErrorMessage());
			return pd;
		}
		
		if (ref.type == PageType.TOC){
			pd = new PageDataToC(itemDataCollectionManager.getTitlesList());
		} else if (ref.type == PageType.SUMMARY){
			pd = new PageDataSummary(itemDataCollectionManager.getTitlesList());
		} else {
			ItemData[] ids = new ItemData[ref.pageIndices.length];
			
			for (int i = 0 ; i < ref.pageIndices.length ; i ++){
				ids[i] = itemDataCollectionManager.getItemData(ref.pageIndices[i]);
			}
			
			pd = new PageDataTest(ids, ref.flowOptions, ref.displayOptions);
		}
		
		return pd;
	}
	
	public Vector<String> getAssessmentStyleLinksForUserAgent(String userAgent){
		return assessmentDataManager.getStyleLinksForUserAgent(userAgent);
	}
	
	public Vector<String> getPageStyleLinksForUserAgent(PageReference ref, String userAgent){
		if (ref.pageIndices.length == 0)
			return new Vector<String>();
		
		return itemDataCollectionManager.getStyleLinksForUserAgent(ref.pageIndices[0], userAgent);
	}

	@Override
	public void onAssessmentDataLoaded() {
		getDataLoaderEventListener().onAssessmentLoaded();
		mode = DataSourceManagerMode.SERVING;		
	}
	
	@Override
	public void onItemCollectionLoaded() {
		// load item styles
		loadStyles();
	}
	
	private void loadStyles() {
		mode = DataSourceManagerMode.LOADING_STYLES;
		String userAgent = Navigator.getUserAgent();
		try {
			// load assesment styles
			List<String> aStyles = assessmentDataManager.getStyleLinksForUserAgent(userAgent);
			for (String styleURL : aStyles) {
				final String styleURL2 = styleURL;
				RequestBuilder builder = new RequestBuilder( RequestBuilder.GET, styleURL);
				styleLoadCounter++;
				builder.setCallback( new RequestCallback() {
					@Override
					public void onResponseReceived(Request request, Response response) {
						styleLoadCounter--; 
						try {
							if (response.getStatusCode() == Response.SC_OK || response.getStatusCode() == 0) {
								getStyleDataSourceManager().addAssessmentStyle( response.getText() , styleURL2 );
							} else {
								// TODO add error handling
							}
						} finally {
							checkLoadFinished();
						}
					}
					
					@Override
					public void onError(Request request, Throwable exception) {
						// TODO Add error handling
						styleLoadCounter--;
						checkLoadFinished();
					}
				});
				builder.send();
			}
			// load item styles
			int itemCount = itemDataCollectionManager.getItemsCount();
			for (int i=itemCount-1;i>=0;i--) {
				List<String> iStyles = itemDataCollectionManager.getStyleLinksForUserAgent(i, userAgent);
				for (String styleURL : iStyles) {
					final int ii = i;
					final String styleURL2 = styleURL;
					RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, styleURL);
					styleLoadCounter++;
					builder.setCallback( new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							styleLoadCounter--;
							try {
								if (response.getStatusCode()==Response.SC_OK || response.getStatusCode() == 0) {
									getStyleDataSourceManager().addItemStyle(ii, response.getText() , styleURL2);
								} else {
									// TODO add error handling
								}
							} finally {
								checkLoadFinished();
							}
						}
						
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO add error handling
							styleLoadCounter--;
							checkLoadFinished();
						}
					});
					builder.send();
				}
			}
		} catch (RequestException e) {
			e.printStackTrace();
		}
		
		checkLoadFinished();
	}
	
	protected void checkLoadFinished(){
		if (styleLoadCounter==0)
			onLoadFinished();		
	}
	
	public void onLoadFinished(){
		mode = DataSourceManagerMode.SERVING;		
		OperationLogManager.logEvent(OperationLogEvent.LOADING_FINISHED);
		getDataLoaderEventListener().onDataReady();		
	}
	
	public DataSourceManagerMode getMode(){
		return mode;
	}
	
	public String getAssessmentTitle(){
		return assessmentDataManager.getAssessmentTitle();
	}
	
	public String getItemTitle(int index){
		return itemDataCollectionManager.getTitlesList()[index];
	}

	@Inject
	public void setStyleDataSourceManager(StyleDataSourceManager styleDataSourceManager) {
		this.styleDataSourceManager = styleDataSourceManager;
	}
	
	public StyleDataSourceManager getStyleDataSourceManager() {
		return styleDataSourceManager;
	}

	public void setDataLoaderEventListener(DataLoaderEventListener listener) {
		this.listener = listener;
	}

	public DataLoaderEventListener getDataLoaderEventListener() {
		return listener;
	}

	private boolean isItemDocument(Document doc){
		try {
			return doc.getDocumentElement().getNodeName().equals("assessmentItem");
		} catch (Exception e) {
		}
		return true;
	}
	
	public List<LibraryExtension> getExtensionCreators(){
		return libraryLoader.getExtensionCreators();
	}
	
}