package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.communication.*;
import eu.ydp.empiria.player.client.controller.data.events.AssessmentDataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.DataLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.events.ItemDataCollectionLoaderEventListener;
import eu.ydp.empiria.player.client.controller.data.library.LibraryExtension;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLoader;
import eu.ydp.empiria.player.client.controller.data.library.LibraryLoaderListener;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;
import eu.ydp.empiria.player.client.preloader.view.ProgressBundle;
import eu.ydp.empiria.player.client.style.StyleDocument;
import eu.ydp.empiria.player.client.util.file.DocumentLoadCallback;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.empiria.player.client.util.file.xml.XmlDocument;
import eu.ydp.gwtutil.client.PathUtil;

import java.util.ArrayList;
import java.util.List;

public class DataSourceManager implements AssessmentDataLoaderEventListener, ItemDataCollectionLoaderEventListener, DataSourceDataSupplier,
        LibraryLoaderListener {

    protected final String MAIN_PRELOADER_ID = "mainPreloader";

    private StyleDataSourceManager styleDataSourceManager;

    private int styleLoadCounter;

    private final AssessmentDataSourceManager assessmentDataManager;
    private final ItemDataSourceCollectionManager itemDataCollectionManager;
    private final LibraryLoader libraryLoader;
    private DataSourceManagerMode mode;
    private DataLoaderEventListener listener;
    private XmlData assesmentXML;
    private final StyleDataSourceLoader styleDataSourceLoader;
    private Image mainPreloader;

    @Inject
    public DataSourceManager(AssessmentDataSourceManager assessmentDataManager) {
        this.assessmentDataManager = assessmentDataManager;
        this.assessmentDataManager.setSkinListener(this);
        mode = DataSourceManagerMode.NONE;
        itemDataCollectionManager = new ItemDataSourceCollectionManager(this);
        libraryLoader = new LibraryLoader(this);
        styleDataSourceLoader = new StyleDataSourceLoader();
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

    public Image getMainPreloader() {
        return this.mainPreloader;
    }

    public void setMainPreloader(Image mainPreloader) {
        this.mainPreloader = mainPreloader;
    }

    public void loadMainDocument(String url) {
        if (RootPanel.get(MAIN_PRELOADER_ID) != null) {
            setMainPreloader(Image.wrap(RootPanel.get(MAIN_PRELOADER_ID)
                    .getElement()));
        } else {
            ProgressBundle progressBundle = GWT.create(ProgressBundle.class);
            setMainPreloader(new Image(progressBundle.getProgressImage()));
        }

        loadMainDocument(url, RootPanel.get(), getMainPreloader());
    }

    public void loadMainDocument(String url, ForIsWidget rootPanel, IsWidget mainPreloader) {
        rootPanel.add(mainPreloader);

        if (mode == DataSourceManagerMode.LOADING_ASSESSMENT || mode == DataSourceManagerMode.LOADING_ITEMS || url == "") {
            return;
        }

        centerMainPreloader(Window.getClientWidth() / 2, Window.getClientHeight() / 2, getMainPreloader().getElement());

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

    protected void centerMainPreloader(int x, int y, com.google.gwt.dom.client.Element preloaderElement) {
        preloaderElement.setId(MAIN_PRELOADER_ID);
        preloaderElement.getStyle()
                .setPosition(Position.ABSOLUTE);
        preloaderElement.getStyle()
                .setLeft(x - preloaderElement.getOffsetWidth() / 2, Unit.PX);
        preloaderElement.getStyle()
                .setTop(y - preloaderElement.getOffsetHeight() / 2, Unit.PX);
    }

    public void loadExtensionsLibrary() {
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

    public void loadItems() {
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
            return new ArrayList<String>();
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
            styleLoadCounter++;

            styleDataSourceLoader.load(styleURL, new DocumentLoadCallback<StyleDocument>() {

                @Override
                public void finishedLoading(StyleDocument value, String baseUrl) {
                    styleLoadCounter--;
                    getStyleDataSourceManager().addAssessmentStyle(value);
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
                        getStyleDataSourceManager().addItemStyle(ii, value);
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

    protected void checkLoadFinished() {
        if (styleLoadCounter == 0) {
            onLoadFinished();
        }
    }

    public void onLoadFinished() {
        ForIsWidget rootPanel = RootPanel.get();
        onLoadFinished(getMainPreloader(), rootPanel);
    }

    public void onLoadFinished(IsWidget mainPreloader, ForIsWidget rootPanel) {
        int preloaderIndex = rootPanel.getWidgetIndex(mainPreloader);

        if (preloaderIndex >= 0) {
            rootPanel.remove(preloaderIndex);
        }

        mode = DataSourceManagerMode.SERVING;
        // OperationLogManager.logEvent(OperationLogEvent.LOADING_FINISHED);

        getDataLoaderEventListener().onDataReady();
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

    public StyleDataSourceManager getStyleDataSourceManager() {
        return styleDataSourceManager;
    }

    public void setDataLoaderEventListener(DataLoaderEventListener listener) {
        this.listener = listener;
    }

    public DataLoaderEventListener getDataLoaderEventListener() {
        return listener;
    }

    public List<LibraryExtension> getExtensionCreators() {
        return libraryLoader.getExtensionCreators();
    }

}
