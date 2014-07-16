package eu.ydp.empiria.player.client.controller.multiview;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.multiview.animation.Animation;
import eu.ydp.empiria.player.client.controller.multiview.animation.AnimationEndCallback;
import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeType;
import eu.ydp.empiria.player.client.controller.multiview.touch.MultiPageTouchHandler;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.inject.Instance;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.util.dom.redraw.ForceRedrawHack;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;
import eu.ydp.gwtutil.client.scheduler.Scheduler;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiPageController extends InternalExtension implements FlowRequestSocketUserExtension, IMultiPageController {

	@Inject
	private EventsBus eventsBus;
	@Inject
	private Scheduler scheduler;
	@Inject
	private PanelCache panelsCache;
	@Inject
	private Page page;
	@Inject
	private MultiPageView view;
	@Inject
	private Instance<Animation> animation;
	@Inject
	private TouchRecognitionFactory touchRecognitionFactory;
	@Inject
	private PageScopeFactory pageScopeFactory;
	@Inject
	private MultiPageTouchHandler multiPageTouchHandler;
	@Inject
	private ForceRedrawHack forceRedrawHack;
	@Inject
	private VisiblePagesManager visiblePagesManager;
	@Inject
	@Named("multiPageControllerMainPanel")
	private FlowPanel mainPanel;
	@Inject
	private SwipeType swipeType;
	@Inject
	private MultiPageControllerStyleManager multiPageControllerStyleManager;
	@Inject
	private WindowDelegate windowDelegate;

	private static final int activePageCount = 3;
	private int currentVisiblePage = -1;
	private final static int WIDTH = 100;
	private final static int DEFAULT_ANIMATION_TIME = 500;
	private final static int QUICK_ANIMATION_TIME = 150;
	public final static int TOUCH_END_TIMER_TIME = 800;
	private float currentPosition;

	private FlowRequestInvoker flowRequestInvoker;
	private int swipeLength = 220;

	private final Set<Integer> loadedPages = new HashSet<>();
	private int pageProgressBar = -1;

	private ResizeTimer resizeTimer;
	private AnimationEndCallback animationCallback = null;

	private final Set<HandlerRegistration> touchHandlers = new HashSet<>();
	private boolean focusDroped;

	private void clearPagesStyles() {
		Function<KeyValue<FlowPanel, FlowPanel>, FlowPanel> keyPanelExtractor = new Function<KeyValue<FlowPanel, FlowPanel>, FlowPanel>() {
			@Override public FlowPanel apply(KeyValue<FlowPanel, FlowPanel> keyValue) {
				return keyValue.getKey();
			}
		};
		Collection<KeyValue<FlowPanel, FlowPanel>> cacheValues = panelsCache.getCache().values();
		Collection<FlowPanel> keyPanels = Collections2.transform(cacheValues, keyPanelExtractor);
		multiPageControllerStyleManager.clearPagesStyles(keyPanels);
	}

	protected int getHeightForPage(int pageNumber) {
		FlowPanel flowPanel = (FlowPanel) getViewForPage(pageNumber).getParent();
		return flowPanel == null ? 0 : flowPanel.getOffsetHeight();
	}

	protected void setHeight(int height) {
		if (isSwipeDisabled()) {
			view.setHeight("auto");
		} else {
			view.setHeight(height + "px");
		}
	}

	@Override
	public void setVisiblePage(int pageNumber) {
		showProgressBarForPage(pageNumber);
		resizeTimer.cancelAndReset();
		resizeTimer.schedule(350);
		if (currentVisiblePage != pageNumber && pageNumber >= 0) {
			this.currentVisiblePage = pageNumber;
			if (activePageCount > 1) {
				animatePageSwitch(getPositionLeft(), -pageNumber * WIDTH, null, DEFAULT_ANIMATION_TIME, false);
			}
		} else {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
		}
		hideProgressBarForPage(pageNumber);
	}

	public FlowPanel getViewForPage(Integer pageNumber) {
		boolean pageWasCreated = panelsCache.isPresent(pageNumber);
		KeyValue<FlowPanel, FlowPanel> panel = panelsCache.getOrCreateAndPut(pageNumber);

		if (!pageWasCreated) {
			mainPanel.add(panel.getKey());
			applyStylesToPanelOnIndex(pageNumber);
			showProgressBarForPage(pageNumber);
		}
		loadedPages.add(pageNumber);
		return panel.getValue();
	}

	private void applyStylesToPanelOnIndex(Integer pageNumber) {
		KeyValue<FlowPanel, FlowPanel> panelsPair = panelsCache.getOrCreateAndPut(pageNumber);
		FlowPanel panel = panelsPair.getKey();

		boolean isChangeToNextPage = isChangeToNextPage(pageNumber);
		multiPageControllerStyleManager.sth(panel, isChangeToNextPage);
	}

	private boolean isChangeToNextPage(Integer pageNumber) {
		return pageNumber > currentVisiblePage;
	}

	private void scheduleDeferredRemoveFromParent(final int page) {
		scheduler.scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				panelsCache.getOrCreateAndPut(page).getValue().removeFromParent();
			}
		});
	}

	private void scheduleDeferredAttachToParent(final KeyValue<FlowPanel, FlowPanel> pair, final int pageNumber) {
		scheduler.scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				FlowPanel placeHolderPanel = pair.getKey();
				FlowPanel pageContentPanel = pair.getValue();
				placeHolderPanel.add(pageContentPanel);
				if (pageNumber == currentVisiblePage) {
					setHeight(getHeightForPage(currentVisiblePage));
				}
			}
		});
	}

	@Override
	public void detachAttachPanels() {
		Set<Integer> pagesToDetach = visiblePagesManager.getPagesToDetach(currentVisiblePage);
		for (final Integer pageIndex : pagesToDetach) {
			scheduleDeferredRemoveFromParent(pageIndex);
		}

		List<Integer> pagesToAttache = visiblePagesManager.getPagesToAttache(currentVisiblePage);

		for (Integer pageIndex : pagesToAttache) {
			final KeyValue<FlowPanel, FlowPanel> pair = panelsCache.getOrCreateAndPut(pageIndex);
			scheduleDeferredAttachToParent(pair, pageIndex);
		}
	}

	@Override
	public void animatePageSwitch() {
		animatePageSwitch(getPositionLeft(), getCurrentPosition(), null, MultiPageController.QUICK_ANIMATION_TIME, true);
	}

	private void animatePageSwitch(float from, final float to, final NavigationButtonDirection direction, int duration, final boolean onlyPositionReset) {
		if (Math.abs(from - to) > 1) {
			if (!onlyPositionReset) {
				windowDelegate.scrollTo(0, 0);
			}
			animation.get().removeAnimationEndCallback(animationCallback);
			animationCallback = new AnimationEndCallback() {
				@Override
				public void onComplate(int position) {
					scheduler.scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							clearPagesStyles();
							if (direction != null) {
								flowRequestInvoker.invokeRequest(NavigationButtonDirection.getRequest(direction));
							}
							if (!onlyPositionReset) {
								eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
							}
							currentPosition = to;
						}
					});
				}
			};
			animation.get().addAnimationEndCallback(animationCallback);
			animation.get().goTo(mainPanel, Math.round((to / WIDTH)) * WIDTH, duration);
		} else if (!onlyPositionReset) {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
		}
	}

	private float getPositionLeft() {
		Style style = getStyle();
		return NumberUtils.tryParseFloat(style.getLeft().replaceAll("[a-z%]+$", ""));
	}

	/**
	 * Na androidzie podczas swipe gapy zle sie rysowaly nachodzac na siebie. Pozbycie sie z nich focusa na czas swipe rozwiazalo problem
	 */
	private void dropFocus() {
		focusDroped = true;
		NodeList<com.google.gwt.dom.client.Element> elementsByTagName = RootPanel.getBodyElement().getElementsByTagName("input");
		for (int x = 0; x < elementsByTagName.getLength(); ++x) {
			elementsByTagName.getItem(x).blur();
		}
	}

	@Override
	public void move(boolean swipeRight, float length) {
		if (!focusDroped && UserAgentChecker.isStackAndroidBrowser()) {
			dropFocus();
		}
		if (swipeRight) {
			showProgressBarForPage(currentVisiblePage + 1);
		} else {
			showProgressBarForPage(currentVisiblePage - 1);
		}
		Style style = getStyle();
		float position = getPositionLeft();
		if (swipeRight) {
			style.setLeft(position - length, Unit.PCT);
		} else {
			style.setLeft(position + length, Unit.PCT);
		}
	}

	private Style getStyle() {
		return mainPanel.getElement().getStyle();
	}

	private void showProgressBarForPage(int pageIndex) {
		if (!loadedPages.contains(pageIndex) && pageProgressBar != pageIndex && pageIndex < page.getPageCount() - 1 && pageIndex >= 0) {
			Panel panel = getViewForPage(pageIndex);
			panel.add(new ProgressPanel());
			pageProgressBar = pageIndex;
		}
	}

	protected void hideProgressBarForPage(int pageIndex) {
		if (pageIndex < page.getPageCount() - 1) {
			FlowPanel panel = getViewForPage(pageIndex);
			for (int x = 0; x < panel.getWidgetCount(); ++x) {
				if (panel.getWidget(x) instanceof ProgressPanel) {
					panel.getWidget(x).removeFromParent();
					break;
				}
			}
		}
	}

	@Override
	public void reset() {
		multiPageTouchHandler.resetModelAndTimer();
		resetFocusAndStyles();
		multiPageTouchHandler.setTouchReservation(true);
	}

	@Override
	public void resetFocusAndStyles() {
		this.focusDroped = false;
		clearPagesStyles();
	}

	public native int getInnerWidth()/*-{
        return $wnd.innerWidth;
    }-*/;

	@Override
	public boolean isZoomed() {
		int clientWidth = windowDelegate.getClientWidth();
		return getInnerWidth() != clientWidth && getInnerWidth() - 1 != clientWidth;
	}

	@Override
	public void switchPage() {
		if (multiPageTouchHandler.getTouchReservation()) {
			animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
			return;
		}

		NavigationButtonDirection direction = multiPageTouchHandler.getDirection();
		float percent = (float) swipeLength / RootPanel.get().getOffsetWidth() * 100;
		if (direction != null) {
			if (direction == NavigationButtonDirection.PREVIOUS && page.getCurrentPageNumber() > 0) {
				animatePageSwitch(getPositionLeft(), getPositionLeft() + (WIDTH - percent), direction, DEFAULT_ANIMATION_TIME, true);
				eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_CHANGE_STARTED));
			} else if (direction == NavigationButtonDirection.NEXT && page.getCurrentPageNumber() < page.getPageCount() - 1) {
				animatePageSwitch(getPositionLeft(), getPositionLeft() - (WIDTH - percent), direction, DEFAULT_ANIMATION_TIME, true);
				eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_CHANGE_STARTED));
			} else {
				animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
			}
		}
	}

	@Override
	public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
		flowRequestInvoker = fri;
	}

	@Override
	public void init() {
		multiPageTouchHandler.setMultiPageController(this);
		view.setController(this);
		configureSwipe();

		LoadPageViewHandler loadPageViewHandler = new LoadPageViewHandler(this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.LOAD_PAGE_VIEW), loadPageViewHandler);
		TouchReservationHandler touchReservationHandler = new TouchReservationHandler(this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TOUCH_EVENT_RESERVATION), touchReservationHandler);
		PageViewLoadedHandler pageViewLoadedHandler = new PageViewLoadedHandler(this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_VIEW_LOADED), pageViewLoadedHandler);

		ResizeContinousUpdater resizeContinousUpdater = new ResizeContinousUpdater(pageScopeFactory, eventsBus, this, forceRedrawHack);
		resizeTimer = new ResizeTimer(resizeContinousUpdater);
		view.add(mainPanel);
	}

	public boolean isSwipeDisabled() {
		return swipeType == SwipeType.DISABLED;
	}

	private void configureSwipe() {
		if (swipeType == SwipeType.DISABLED) {
			for (HandlerRegistration registration : touchHandlers) {
				registration.removeHandler();
			}
			touchHandlers.clear();
			setVisiblePageCount(1);
		} else {
			HasTouchHandlers touchHandler = touchRecognitionFactory.getTouchRecognition(RootPanel.get(), false);
			touchHandlers.add(touchHandler.addTouchHandler(multiPageTouchHandler, TouchEvent.getType(TouchTypes.TOUCH_START)));
			touchHandlers.add(touchHandler.addTouchHandler(multiPageTouchHandler, TouchEvent.getType(TouchTypes.TOUCH_MOVE)));
			touchHandlers.add(touchHandler.addTouchHandler(multiPageTouchHandler, TouchEvent.getType(TouchTypes.TOUCH_END)));
			setVisiblePageCount(3);
		}
		panelsCache.setSwipeType(swipeType);
	}

	private void setVisiblePageCount(int count) {
		visiblePagesManager.setVisiblePageCount(count);
	}

	public int getCurrentVisiblePage() {
		return currentVisiblePage;
	}

	public FlowPanel getMainPanel() {
		return mainPanel;
	}

	public float getWidth() {
		return WIDTH;
	}

	public MultiPageView getView() {
		return view;
	}

	@Override
	public boolean isAnimationRunning() {
		return animation.get().isRunning();
	}

	private float getCurrentPosition() {
		return currentPosition;
	}

	public void setSwipeLength(int swipeLength) {
		this.swipeLength = swipeLength;
	}
}
