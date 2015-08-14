package eu.ydp.empiria.player.client.controller.flow;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.communication.*;
import eu.ydp.empiria.player.client.controller.flow.processing.commands.FlowCommandsListener;
import eu.ydp.empiria.player.client.controller.flow.processing.events.ActivityProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEventType;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.util.config.OptionsReader;
import eu.ydp.empiria.player.client.util.events.external.ExternalEventDispatcher;
import eu.ydp.empiria.player.client.util.events.external.PageChangedEvent;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.page.PageEvent;
import eu.ydp.empiria.player.client.util.events.internal.page.PageEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

@Singleton
public class MainFlowProcessor implements FlowCommandsListener, FlowDataSupplier, PlayerEventHandler {

    @Inject
    public MainFlowProcessor(EventsBus eventsBus, ExternalEventDispatcher externalEventDispatcher, PageScopeFactory pageScopeFactory) {
        this.eventsBus = eventsBus;
        this.externalEventDispatcher = externalEventDispatcher;
        this.pageScopeFactory = pageScopeFactory;
        // flowExecutionEventsListener = feel;
        flowOptions = OptionsReader.getFlowOptions();
        displayOptions = new DisplayOptions();
        isCheck = false;
        isShowAnswers = false;
        isInitalized = false;
    }

    // private final FlowProcessingEventsListener flowExecutionEventsListener;
    // // NOPMD
    private ItemParametersSocket itemParametersSocket; // NOPMD
    private final EventsBus eventsBus;
    private final ExternalEventDispatcher externalEventDispatcher;
    private final PageScopeFactory pageScopeFactory;
    private int currentPageIndex;
    private PageType currentPageType;
    private int itemsCount;
    private FlowOptions flowOptions;
    private DisplayOptions displayOptions;
    private boolean isCheck;
    private boolean isShowAnswers;
    private boolean isLock;
    private boolean isInitalized;

    public void init(int _itemsCount) {
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_VIEW_LOADED), this);
        itemsCount = _itemsCount;
        currentPageIndex = 0;
        if (flowOptions.showToC) {
            currentPageType = PageType.TOC;
        } else {
            currentPageType = PageType.TEST;
        }
    }

    public void initFlow() {
        if (!isInitalized) {
            isInitalized = true;
            doFlow();
        }
    }

    public void deinitFlow() {
        isInitalized = false;
    }

    private void doFlow() {
        if (isInitalized) {
            eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.LOAD_PAGE_VIEW, currentPageIndex, this));
        }
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (event.getType() == PlayerEventTypes.PAGE_VIEW_LOADED) {
            if (isInitalized) {
                eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_CHANGE, currentPageIndex, this));
                eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED, new FlowProcessingEvent(FlowProcessingEventType.PAGE_LOADED), this));
                externalEventDispatcher.dispatch(new PageChangedEvent(currentPageIndex));
            }
        }
    }

    public void setFlowOptions(FlowOptions options) {
        flowOptions = options;
        if (currentPageType == PageType.TOC && !flowOptions.showToC) {
            currentPageType = PageType.TEST;
            currentPageIndex = 0;
        } else if (currentPageType == PageType.SUMMARY && !flowOptions.showSummary) {
            currentPageType = PageType.TEST;
            currentPageIndex = 0;
        }
    }

    @Override
    public FlowOptions getFlowOptions() {
        return flowOptions;
    }

    public void setDisplayOptions(DisplayOptions options) {
        displayOptions = options;
    }

    public DisplayOptions getDisplayOptions() {
        return displayOptions;
    }

    @Override
    public void gotoPage(int index) {
        if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL) {
            if (index == 0) {
                onPageChange();
                currentPageType = PageType.TEST;
                currentPageIndex = index;
                doFlow();
            }
        } else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE) {
            if (index >= 0 && index < itemsCount) {// NOPMD
                onPageChange();
                currentPageType = PageType.TEST;
                currentPageIndex = index;
                doFlow();
            }
        }

    }

    @Override
    public void gotoSummary() {
        if (currentPageType != PageType.SUMMARY && flowOptions.showSummary) {
            onPageChange();
            flowOptions.activityMode = ActivityMode.NORMAL;
            // displayOptions.setPreviewMode(false);
            currentPageType = PageType.SUMMARY;
            currentPageIndex = 0;
            doFlow();
        }

    }

    @Override
    public void gotoToc() {
        if (currentPageType != PageType.TOC && flowOptions.showToC) {
            onPageChange();
            currentPageType = PageType.TOC;
            currentPageIndex = 0;
            doFlow();
        }

    }

    @Override
    public void gotoTest() {
        if (currentPageType != PageType.TEST && itemsCount > 0) {
            onPageChange();
            gotoPage(0);
        }
    }

    @Override
    public void nextPage() {
        if (currentPageType == PageType.TOC) {
            if (0 < itemsCount) {
                gotoPage(0);
            }
        } else if (currentPageType == PageType.TEST) {
            if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE) {// NOPMD
                if (currentPageIndex + 1 >= 0 && currentPageIndex + 1 < itemsCount) {// NOPMD
                    gotoPage(currentPageIndex + 1);
                }
            }
        }

    }

    @Override
    public void previousPage() {
        if (currentPageType == PageType.TEST) {
            if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE) {// NOPMD
                if (currentPageIndex - 1 >= 0 && currentPageIndex - 1 < itemsCount) {
                    gotoPage(currentPageIndex - 1);
                } else if (currentPageIndex == 0) {
                    gotoToc();
                }
            }
        }

    }

    @Override
    public void gotoFirstPage() {
        if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL) {
            if (0 < itemsCount) {// NOPMD
                gotoPage(0);
            }
        } else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE) {
            if (0 < itemsCount) {// NOPMD
                gotoPage(0);
            }
        }
    }

    @Override
    public void gotoLastPage() {
        if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL) {
            if (0 < itemsCount) {// NOPMD
                gotoPage(0);
            }
        } else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE) {
            if (itemsCount > 0) {// NOPMD
                gotoPage(itemsCount - 1);
            }
        }

    }

    @Override
    public void checkPage() {
        if (!isCheck) {
            if (isShowAnswers) {
                continuePage();
            }
            isCheck = true;
            eventsBus.fireEvent(new PageEvent(PageEventTypes.CHECK, new FlowProcessingEvent(FlowProcessingEventType.CHECK), this), pageScopeFactory.getCurrentPageScope());
        }
    }

    @Override
    public void showAnswersPage() {
        if (!isShowAnswers) {
            if (isCheck) {
                continuePage();
            }
            isShowAnswers = true;
            eventsBus.fireEvent(new PageEvent(PageEventTypes.SHOW_ANSWERS, new FlowProcessingEvent(FlowProcessingEventType.SHOW_ANSWERS), this),
                    pageScopeFactory.getCurrentPageScope());
        }

    }

    @Override
    public void continuePage() {
        if (isCheck || isShowAnswers) {
            isCheck = false;
            isShowAnswers = false;
            eventsBus
                    .fireEvent(new PageEvent(PageEventTypes.CONTINUE, new FlowProcessingEvent(FlowProcessingEventType.CONTINUE), this), pageScopeFactory.getCurrentPageScope());
        }

    }

    @Override
    public void lockPage() {
        if (!isLock) {
            isLock = true;
            eventsBus.fireEvent(new PageEvent(PageEventTypes.LOCK, new FlowProcessingEvent(FlowProcessingEventType.LOCK), this), pageScopeFactory.getCurrentPageScope());
        }
    }

    @Override
    public void unlockPage() {
        if (isLock) {
            isLock = false;
            eventsBus.fireEvent(new PageEvent(PageEventTypes.UNLOCK, new FlowProcessingEvent(FlowProcessingEventType.UNLOCK), this), pageScopeFactory.getCurrentPageScope());
        }
    }

    @Override
    public void resetPage() {
        isCheck = false;
        isShowAnswers = false;
        eventsBus.fireEvent(new PageEvent(PageEventTypes.RESET, new FlowProcessingEvent(FlowProcessingEventType.RESET), this), pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public void checkGroup(GroupIdentifier groupId) {
        eventsBus.fireEvent(new PageEvent(PageEventTypes.CHECK, new ActivityProcessingEvent(FlowProcessingEventType.CHECK, groupId), this),
                pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public void showAnswersGroup(GroupIdentifier groupId) {
        eventsBus.fireEvent(new PageEvent(PageEventTypes.SHOW_ANSWERS, new ActivityProcessingEvent(FlowProcessingEventType.SHOW_ANSWERS, groupId), this),
                pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public void continueGroup(GroupIdentifier groupId) {
        eventsBus.fireEvent(new PageEvent(PageEventTypes.CONTINUE, new ActivityProcessingEvent(FlowProcessingEventType.CONTINUE, groupId), this),
                pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public void resetGroup(GroupIdentifier groupId) {
        eventsBus.fireEvent(new PageEvent(PageEventTypes.RESET, new ActivityProcessingEvent(FlowProcessingEventType.RESET, groupId), this),
                pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public void lockGroup(GroupIdentifier groupId) {
        eventsBus.fireEvent(new PageEvent(PageEventTypes.LOCK, new ActivityProcessingEvent(FlowProcessingEventType.LOCK, groupId), this),
                pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public void unlockGroup(GroupIdentifier groupId) {
        eventsBus.fireEvent(new PageEvent(PageEventTypes.UNLOCK, new ActivityProcessingEvent(FlowProcessingEventType.UNLOCK, groupId), this),
                pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public void previewPage(int index) {
        onPageChange();
        flowOptions.activityMode = ActivityMode.CHECK;
        gotoPage(index);
    }

    public void onPageChange() {
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_UNLOADED));
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_CHANGING, new FlowProcessingEvent(FlowProcessingEventType.PAGE_CHANGING), this));
        isCheck = false;
        isShowAnswers = false;
        isLock = false;
    }

    @Override
    public boolean getFlowFlagCheck() {// NOPMD
        return isCheck;
    }

    @Override
    public boolean getFlowFlagShowAnswers() {// NOPMD
        return isShowAnswers;
    }

    @Override
    public boolean getFlowFlagLock() {// NOPMD
        return isLock;
    }

    public PageReference getPageReference() {
        int[] currentPageItemsIndices = null; // NOPMD
        if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ONE) {
            currentPageItemsIndices = new int[1];
            currentPageItemsIndices[0] = currentPageIndex;
        } else if (flowOptions.itemsDisplayMode == PageItemsDisplayMode.ALL) {
            currentPageItemsIndices = new int[itemsCount];
            for (int i = 0; i < itemsCount; i++) {
                currentPageItemsIndices[i] = i;
            }
        }
        return new PageReference(currentPageType, currentPageItemsIndices, flowOptions, displayOptions);
    }

    public PageType getPageType() {
        return currentPageType;
    }

    @Override
    public ActivityMode getActivityMode() {
        return flowOptions.activityMode;
    }

    @Override
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    @Override
    public int getPageCount() {
        return itemsCount;
    }

    @Override
    public PageType getCurrentPageType() {
        return currentPageType;
    }

    public ItemParameters getItemParamters() {
        return (itemParametersSocket == null) ? new ItemParameters() : itemParametersSocket.getItemParameters();
    }

}
