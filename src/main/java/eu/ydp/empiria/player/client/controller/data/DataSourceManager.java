package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.ContentPreloader;
import eu.ydp.empiria.player.client.controller.communication.*;
import eu.ydp.empiria.player.client.controller.data.events.AssessmentDataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.ItemDataCollectionLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.library.LibraryExtension;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLoader;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLoaderListener;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;
import eu.ydp.empiria.player.client.style.StyleDocument;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.file.xml.XmlDocument;
import eu.ydp.gwtutil.client.PathUtil;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class DataSourceManager implements AssessmentDataLoaderEventListener, ItemDataCollectionLoaderEventListener, DataSourceDataSupplier,
        LibraryLoaderListener {

    private StyleDataSourceManager styleDataSourceManager;

    private int styleLoadCounter;

    private final AssessmentDataSourceManager assessmentDataManager;
    private final ContentPreloader contentPreloader;
    private final ItemDataSourceCollectionManager itemDataCollectionManager;
    private final LibraryLoader libraryLoader;
    private DataSourceManagerMode mode;
    private DataLoaderEventListener listener;
    private XmlData assesmentXML;
    private final StyleDataSourceLoader styleDataSourceLoader;

    @Inject
    public DataSourceManager(AssessmentDataSourceManager assessmentDataManager, ItemDataSourceCollectionManager itemDataCollectionManager,
                             StyleDataSourceLoader styleDataSourceLoader, ContentPreloader contentPreloader) {
        this.assessmentDataManager = assessmentDataManager;
        this.assessmentDataManager.setSkinListener(this);
        this.itemDataCollectionManager = itemDataCollectionManager;
        this.styleDataSourceLoader = styleDataSourceLoader;
        this.contentPreloader = contentPreloader;

        mode = DataSourceManagerMode.NONE;
        itemDataCollectionManager.setLoaderEventListener(this);
        libraryLoader = new LibraryLoader(this);
    }

    public InitialData getInitialData() {
        InitialData initialData = new InitialData(itemDataCollectionManager.getItemsCount());
        for (int i = 0; i < itemDataCollectionManager.getItemsCount(); i++) {
            initialData.addItemInitialData(itemDataCollectionManager.getItemInitialData(i));
        }
        return initialData;
    }

    public AssessmentData getAssessmentData() {
        return assessmentDataManager.getAssessmentData();
    }

    public ItemData getItemData(int index) {
        return itemDataCollectionManager.getItemData(index);
    }

    @Override
    public int getItemsCount() {
        return assessmentDataManager.getItemsCount();
    }

    /**
     * Zwraca wezel assessmentItemRef o wskazanym id
     *
     * @param index index wezla
     * @return Element lub null gdy element o podanym indeksie nie istnieje
     */
    @Override
    public Element getItem(int itemIndex) {
        return assessmentDataManager.getItem(itemIndex);
    }

    public void loadMainDocument(String url) {
        if (mode == DataSourceManagerMode.LOADING_ASSESSMENT || mode == DataSourceManagerMode.LOADING_ITEMS || "".equals(url)) {
            return;
        }

        mode = DataSourceManagerMode.LOADING_ASSESSMENT;

        String resolvedURL;

        resolvedURL = PathUtil.resolvePath(url, GWT.getHostPageBaseURL());

        new eu.ydp.empiria.player.client.util.file.xml.XmlDocument(resolvedURL, new DocumentLoadCallback<Document>() {

            @Override
            public void finishedLoading(Document document, String baseURL) {
                assesmentXML = new XmlData(document, baseURL);
                assessmentDataManager.initializeAssessmentData(assesmentXML);
            }

            @Override
            public void loadingError(String error) {
                assessmentDataManager.setAssessmentLoadingError(error);
                onLoadFinished();
            }
        });
    }


    private void loadExtensionsLibrary() {
        String libraryUrl = assessmentDataManager.getLibraryLink();

        new XmlDocument(libraryUrl, new DocumentLoadCallback<Document>() {

            @Override
            public void finishedLoading(Document document, String baseURL) {
                libraryLoader.load(new XmlData(document, baseURL));
            }

            @Override
            public void loadingError(String error) {
                onExtensionsLoadFinished();
            }
        });

    }

    @Override
    public void onExtensionsLoadFinished() {
        loadItems();
    }

    private void loadItems() {
        loadItems(assessmentDataManager.getItemUrls());
    }

    private void loadItems(String[] urls) {

        mode = DataSourceManagerMode.LOADING_ITEMS;

        itemDataCollectionManager.initItemDataCollection(urls.length);

        if (urls.length == 0) {
            onItemCollectionLoaded();
        }

        for (int i = 0; i < urls.length; i++) {
            final int ii = i;

            new XmlDocument(urls[ii], new DocumentLoadCallback<Document>() {

                @Override
                public void finishedLoading(Document document, String baseURL) {
                    itemDataCollectionManager.setItemData(ii, new XmlData(document, baseURL));
                }

                @Override
                public void loadingError(String error) {
                    itemDataCollectionManager.setItemData(ii, error);
                }
            });
        }

    }

    private void loadSingleItemData(XmlData itemData) {
        itemDataCollectionManager.setItemDataCollection(new XmlData[]{itemData});
    }

    public void loadData(XmlData ad, XmlData[] ids) {
        assessmentDataManager.setAssessmentData(ad);
        itemDataCollectionManager.setItemDataCollection(ids);
    }

    public PageData generatePageData(PageReference ref) {
        PageData pd;

        if (assessmentDataManager.isError()) {
            pd = new PageDataError(assessmentDataManager.getErrorMessage());
            return pd;
        }

        if (ref.type == PageType.TOC) {
            pd = new PageDataToC(itemDataCollectionManager.getTitlesList());
        } else if (ref.type == PageType.SUMMARY) {
            pd = new PageDataSummary(itemDataCollectionManager.getTitlesList());
        } else {
            ItemData[] ids = new ItemData[ref.pageIndices.length];

            for (int i = 0; i < ref.pageIndices.length; i++) {
                ids[i] = itemDataCollectionManager.getItemData(ref.pageIndices[i]);
            }

            pd = new PageDataTest(ids, ref.flowOptions, ref.displayOptions);
        }

        return pd;
    }

    public List<String> getAssessmentStyleLinksForUserAgent(String userAgent) {
        return assessmentDataManager.getStyleLinksForUserAgent(userAgent);
    }

    public List<String> getPageStyleLinksForUserAgent(PageReference ref, String userAgent) {
        if (ref.pageIndices.length == 0) {
            return new ArrayList<>();
        }

        return itemDataCollectionManager.getStyleLinksForUserAgent(ref.pageIndices[0], userAgent);
    }

    @Override
    public void onAssessmentDataLoaded() {
        if (assessmentDataManager.isDefaultData()) {
            loadSingleItemData(assesmentXML);
        } else {
            if (assessmentDataManager.hasLibrary()) {
                loadExtensionsLibrary();
            } else {
                loadItems();
            }
        }

        listener.onAssessmentLoaded();
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
            styleLoadCounter++;

            styleDataSourceLoader.load(styleURL, new DocumentLoadCallback<StyleDocument>() {

                @Override
                public void finishedLoading(StyleDocument value, String baseUrl) {
                    styleLoadCounter--;
                    styleDataSourceManager.addAssessmentStyle(value);
                    checkLoadFinished();
                }

                @Override
                public void loadingError(String message) {
                    styleLoadCounter--;
                    checkLoadFinished();
                }
            });

        }
        // load item styles
        int itemCount = itemDataCollectionManager.getItemsCount();
        for (int i = itemCount - 1; i >= 0; i--) {
            List<String> iStyles = itemDataCollectionManager.getStyleLinksForUserAgent(i, userAgent);
            for (String styleURL : iStyles) {
                final int ii = i;

                styleLoadCounter++;

                styleDataSourceLoader.load(styleURL, new DocumentLoadCallback<StyleDocument>() {

                    @Override
                    public void finishedLoading(StyleDocument value, String baseUrl) {
                        styleLoadCounter--;
                        styleDataSourceManager.addItemStyle(ii, value);
                        checkLoadFinished();
                    }

                    @Override
                    public void loadingError(String message) {
                        styleLoadCounter--;
                        checkLoadFinished();
                    }
                });

            }
        }

        checkLoadFinished();
    }

    private void checkLoadFinished() {
        if (styleLoadCounter == 0) {
            onLoadFinished();
        }
    }

    private void onLoadFinished() {
        contentPreloader.removePreloader();

        mode = DataSourceManagerMode.SERVING;
        // OperationLogManager.logEvent(OperationLogEvent.LOADING_FINISHED);

        listener.onDataReady();
    }

    public DataSourceManagerMode getMode() {
        return mode;
    }

    @Override
    public String getAssessmentTitle() {
        return assessmentDataManager.getAssessmentTitle();
    }

    @Override
    public String getItemTitle(int index) {
        return itemDataCollectionManager.getTitlesList()[index];
    }

    @Override
    public ProgressToStringRangeMap getItemFeedbacks(int index) {
        return itemDataCollectionManager.getFeedbacks(index);
    }

    @Inject
    public void setStyleDataSourceManager(StyleDataSourceManager styleDataSourceManager) {
        this.styleDataSourceManager = styleDataSourceManager;
    }

    public void setDataLoaderEventListener(DataLoaderEventListener listener) {
        this.listener = listener;
    }

    public List<LibraryExtension> getExtensionCreators() {
        return libraryLoader.getExtensionCreators();
    }

}
