package eu.ydp.empiria.player.client.controller.data;

import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.communication.AssessmentData;
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
import eu.ydp.empiria.player.client.controller.data.library.LibraryLoader;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLoaderListener;
import eu.ydp.empiria.player.client.controller.log.OperationLogEvent;
import eu.ydp.empiria.player.client.controller.log.OperationLogManager;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.file.TextDocument;
import eu.ydp.empiria.player.client.util.file.TextDocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.file.xml.XmlDocument;
import eu.ydp.empiria.player.client.util.file.xml.XmlDocumentLoadCallback;

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
	private XmlData assesmentXML;

	public InitialData getInitialData(){
		InitialData initialData = new InitialData(itemDataCollectionManager.getItemsCount());
		for (int i = 0 ; i < itemDataCollectionManager.getItemsCount() ; i ++){
			initialData.addItemInitialData(itemDataCollectionManager.getItemInitialData(i));
		}
		return initialData;
	}

	public AssessmentData getAssessmentData(){
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

	/**
	 * Zwraca wezel assessmentItemRef o wskazanym id
	 * @param index index wezla
	 * @return Element lub null gdy element o podanym indeksie nie istnieje
	 */
	@Override
	public Element getItem(int itemIndex) {
		return assessmentDataManager.getItem(itemIndex);
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

		new eu.ydp.empiria.player.client.util.file.xml.XmlDocument(resolvedURL, new XmlDocumentLoadCallback(){

			public void finishedLoading(Document document, String baseURL) {
				assesmentXML = new XmlData(document, baseURL);
				assessmentDataManager.setAssessmentData(assesmentXML);
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

		new XmlDocument(libraryUrl, new XmlDocumentLoadCallback() {

			@Override
			public void finishedLoading(Document document, String baseURL) {
				libraryLoader.load(new XmlData(document, baseURL));
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

			new XmlDocument(urls[ii], new XmlDocumentLoadCallback(){

				public void finishedLoading(Document document, String baseURL) {
					itemDataCollectionManager.setItemData(ii, new XmlData(document, baseURL));
				}

				@Override
				public void loadingErrorHandler(String error) {
					itemDataCollectionManager.setItemData(ii, error);
				}
			});
		}

	}

	private void loadSingleItemData(XmlData itemData){
		itemDataCollectionManager.setItemDataCollection(new XmlData[] {itemData});
	}

	public void loadData(XmlData ad, XmlData[] ids){
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
		if(assessmentDataManager.isDefaultData()){
			loadSingleItemData(assesmentXML);
		}else{
			if (assessmentDataManager.hasLibrary())
				loadExtensionsLibrary();
			else
				loadItems();
		}

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
		// load assesment styles
		List<String> aStyles = assessmentDataManager.getStyleLinksForUserAgent(userAgent);
		for (String styleURL : aStyles) {
			final String styleURL2 = styleURL;
			
			styleLoadCounter++;
			
			new TextDocument(styleURL2, new TextDocumentLoadCallback() {
				
				@Override
				public void loadingError(String message) {
					styleLoadCounter--;
					checkLoadFinished();
				}
				
				@Override
				public void finishedLoading(String text, String baseUrl) {
					getStyleDataSourceManager().addAssessmentStyle( text , styleURL2 );
					styleLoadCounter--;
					checkLoadFinished();
				}
			});
		}
		// load item styles
		int itemCount = itemDataCollectionManager.getItemsCount();
		for (int i=itemCount-1;i>=0;i--) {
			List<String> iStyles = itemDataCollectionManager.getStyleLinksForUserAgent(i, userAgent);
			for (String styleURL : iStyles) {
				final int ii = i;
				final String styleURL2 = styleURL;

				styleLoadCounter++;

				new TextDocument(styleURL2, new TextDocumentLoadCallback() {
					
					@Override
					public void loadingError(String message) {
						styleLoadCounter--;
						checkLoadFinished();
					}
					
					@Override
					public void finishedLoading(String text, String baseUrl) {
						styleLoadCounter--;
						getStyleDataSourceManager().addItemStyle(ii, text , styleURL2);
						checkLoadFinished();
					}
				});
				
			}
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

	public List<LibraryExtension> getExtensionCreators(){
		return libraryLoader.getExtensionCreators();
	}

}