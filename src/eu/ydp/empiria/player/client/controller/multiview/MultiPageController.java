package eu.ydp.empiria.player.client.controller.multiview;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.multiview.animation.Animation;
import eu.ydp.empiria.player.client.controller.multiview.animation.AnimationEndCallback;
import eu.ydp.empiria.player.client.controller.multiview.touch.MultiPageTouchHandler;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchEvent;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.scheduler.Scheduler;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class MultiPageController extends InternalExtension implements PlayerEventHandler, FlowRequestSocketUserExtension, FlowDataSocketUserExtension,
		Extension, IMultiPageController {

	@Inject
	private EventsBus eventsBus;
	@Inject
	private StyleNameConstants styleNames;
	@Inject
	private Scheduler scheduler;
	@Inject
	private PanelCache panelsCache;
	@Inject
	private Page page;
	@Inject
	private MultiPageView view;
	@Inject
	private StyleSocket styleSocket;
	@Inject
	private Animation animation;
	@Inject
	private TouchRecognitionFactory touchRecognitionFactory;
	@Inject
	private PageScopeFactory pageScopeFactory;
	@Inject
	private MultiPageTouchHandler multiPageTouchHandler;

	@Inject
	@Named("multiPageControllerMainPanel")
	private FlowPanel mainPanel;
	@Inject
	private PagePlaceHolderPanelCreator pagePlaceHolderPanelCreator;

	private static int activePageCount = 3;
	private int currentVisiblePage = -1;
	private final static int WIDTH = 100;
	private final static int DEFAULT_ANIMATION_TIME = 500;
	private final static int QUICK_ANIMATION_TIME = 150;
	public final static int TOUCH_END_TIMER_TIME = 800;
	private float currentPosition;

	private FlowRequestInvoker flowRequestInvoker;
	private final Set<Integer> measuredPanels = new HashSet<Integer>();
	private final Set<Integer> detachedPages = new HashSet<Integer>();
	private int swipeLength = 220;

	/**
	 * ktore strony zostaly zaladowane
	 */
	private final Set<Integer> loadedPages = new HashSet<Integer>();
	private static int visblePageCount = 3;
	private int pageProgressBar = -1;

	private ResizeTimer resizeTimer;
	private AnimationEndCallback animationCallback = null;
	private boolean swipeDisabled = false;
	private FlowDataSupplier flowDataSupplier;

	private final Set<HandlerRegistration> touchHandlers = new HashSet<HandlerRegistration>();
	private boolean focusDroped;

	private void setStylesForPages(boolean swipeStarted) {
		if (swipeStarted) {
			Panel selectedPanel = panelsCache.getOrCreateAndPut(currentVisiblePage).getKey();
			selectedPanel.setStyleName(styleNames.QP_PAGE_SELECTED());
			for (Map.Entry<Integer, KeyValue<FlowPanel, FlowPanel>> panel : panelsCache.getCache().entrySet()) {
				if (panel.getKey() != currentVisiblePage) {
					panel.getValue().getKey().addStyleName(styleNames.QP_PAGE_UNSELECTED());
					if (panel.getKey() == currentVisiblePage - 1) {
						panel.getValue().getKey().addStyleName(styleNames.QP_PAGE_PREV());
					} else if (panel.getKey() == currentVisiblePage + 1) {
						panel.getValue().getKey().addStyleName(styleNames.QP_PAGE_NEXT());
					}
				}
			}
		} else {
			for (Map.Entry<Integer, KeyValue<FlowPanel, FlowPanel>> panel : panelsCache.getCache().entrySet()) {
				FlowPanel flowPanel = panel.getValue().getKey();
				flowPanel.removeStyleName(styleNames.QP_PAGE_UNSELECTED());
				flowPanel.removeStyleName(styleNames.QP_PAGE_SELECTED());
				flowPanel.removeStyleName(styleNames.QP_PAGE_PREV());
				flowPanel.removeStyleName(styleNames.QP_PAGE_NEXT());
			}
		}
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

	private void setVisiblePanels(int pageNumber) {
		showProgressBarForPage(pageNumber);
		if (!measuredPanels.contains(pageNumber)) {
			resizeTimer.cancelAndReset();
			resizeTimer.schedule(350);
		}
		if (currentVisiblePage != pageNumber && pageNumber >= 0) {
			this.currentVisiblePage = pageNumber;
			if (activePageCount > 1) {
				if (measuredPanels.contains(pageNumber)) {
					setHeight(getHeightForPage(pageNumber));
				}
				animatePageSwitch(getPositionLeft(), -pageNumber * WIDTH, null, DEFAULT_ANIMATION_TIME, false);
			}
		} else {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
		}
		hideProgressBarForPage(pageNumber);
	}

	/**
	 * zwraca Panel bdacy kontenerem dla widgetow ze strony pageNumber
	 * 
	 * @param pageNumber
	 * @return
	 */
	public FlowPanel getViewForPage(Integer pageNumber) {
		if (!panelsCache.isPresent(pageNumber)) {
			pagePlaceHolderPanelCreator.createPanelsUntilIndex(pageNumber);
			applyStylesToPanelOnIndex(pageNumber);
			showProgressBarForPage(pageNumber);
		}

		KeyValue<FlowPanel, FlowPanel> panel = panelsCache.getOrCreateAndPut(pageNumber);

		loadedPages.add(pageNumber);
		return panel.getValue();
	}

	private void applyStylesToPanelOnIndex(Integer pageNumber) {
		KeyValue<FlowPanel, FlowPanel> panelsPair = panelsCache.getOrCreateAndPut(pageNumber);
		FlowPanel panel = panelsPair.getKey();

		panel.addStyleName(styleNames.QP_PAGE_UNSELECTED());

		String pageDirectionChangeStyle = findPageDirectionChangeStyle(pageNumber);
		panel.addStyleName(pageDirectionChangeStyle);
	}

	private String findPageDirectionChangeStyle(Integer pageNumber) {
		String pageDirectionChangeStyle;
		if (isChangeToNextPage(pageNumber)) {
			pageDirectionChangeStyle = styleNames.QP_PAGE_NEXT();
		} else {
			pageDirectionChangeStyle = styleNames.QP_PAGE_PREV();
		}
		return pageDirectionChangeStyle;
	}

	private boolean isChangeToNextPage(Integer pageNumber) {
		return pageNumber > currentVisiblePage;
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		switch (event.getType()) {
		case TOUCH_EVENT_RESERVATION:
			reset();
			multiPageTouchHandler.setTouchReservation(true);
			break;
		case LOAD_PAGE_VIEW:
			setVisiblePanels((Integer) (event.getValue() == null ? 0 : event.getValue()));
			break;
		case PAGE_VIEW_LOADED:
			detachAttachPanels();
			break;
		default:
			break;
		}
	}

	private void scheduleDeferedRemoveFromParent(final int page) {
		scheduler.scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				panelsCache.getOrCreateAndPut(page).getValue().removeFromParent();
			}
		});
	}

	private void scheduleDeferedAttachToParent(final KeyValue<FlowPanel, FlowPanel> pair, final int pageNumber) {
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

	private void detachAttachPanels() {
		Set<Integer> activePanels = new HashSet<Integer>(panelsCache.getCache().keySet());
		activePanels.removeAll(detachedPages);
		int min = (int) Math.ceil(visblePageCount / 2);
		int max = (int) Math.floor(visblePageCount / 2);
		for (int page = currentVisiblePage - min; page <= currentVisiblePage + max; ++page) {
			activePanels.remove(page);
		}

		for (final Integer page : activePanels) {
			scheduleDeferedRemoveFromParent(page);
			detachedPages.add(page);
		}

		activePanels.clear();
		for (int page = currentVisiblePage - min; page <= currentVisiblePage + max; ++page) {
			activePanels.add(page);
		}
		for (Integer page : activePanels) {
			final int pageNumber = page;
			if (panelsCache.isPresent(page) && detachedPages.contains(page)) {
				final KeyValue<FlowPanel, FlowPanel> pair = panelsCache.getOrCreateAndPut(page);
				scheduleDeferedAttachToParent(pair, pageNumber);
				detachedPages.remove(page);
			}
		}
	}

	/**
	 * animacja
	 * 
	 * @param from
	 *            absolutna pozycja elementu
	 * @param to
	 *            absolutna pozycja elementu cel
	 * @param direction
	 */

	@Override
	public void animatePageSwitch() {
		animatePageSwitch(getPositionLeft(), getCurrentPosition(), null, MultiPageController.QUICK_ANIMATION_TIME, true);
	}

	private void animatePageSwitch(float from, final float to, final NavigationButtonDirection direction, int duration, final boolean onlyPositionReset) {// NOPMD
		if (Math.abs(from - to) > 1) {
			if (!onlyPositionReset) {
				Window.scrollTo(0, 0);
			}
			animation.removeAnimationEndCallback(animationCallback);
			animationCallback = new AnimationEndCallback() {
				@Override
				public void onComplate(int position) {

					scheduler.scheduleDeferred(new ScheduledCommand() {
						@Override
						public void execute() {
							setStylesForPages(false);
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
			animation.addAnimationEndCallback(animationCallback);
			animation.goTo(mainPanel, Math.round((to / WIDTH)) * WIDTH, duration);
		} else if (!onlyPositionReset) {
			eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.PAGE_VIEW_LOADED));
		}
	}

	public float getPositionLeft() {
		Style style = mainPanel.getElement().getStyle();
		return NumberUtils.tryParseFloat(style.getLeft().replaceAll("[a-z%]+$", ""));

	}

	/**
	 * Na androidzie podczas swipe gapy zle sie rysowaly nachodzac na siebie.
	 * Pozbycie sie z nich focusa na czas swipe rozwiazalo problem
	 */
	private void dropFocus() {
		focusDroped = true;
		NodeList<com.google.gwt.dom.client.Element> elementsByTagName = RootPanel.getBodyElement().getElementsByTagName("input");
		for (int x = 0; x < elementsByTagName.getLength(); ++x) {
			elementsByTagName.getItem(x).blur();
		}
	};

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
		Style style = mainPanel.getElement().getStyle();
		float position = getPositionLeft();
		if (swipeRight) {
			style.setLeft(position - length, Unit.PCT);
		} else {
			style.setLeft(position + length, Unit.PCT);
		}

	}

	private void showProgressBarForPage(int pageIndex) {
		if (!loadedPages.contains(pageIndex) && pageProgressBar != pageIndex && pageIndex < page.getPageCount() - 1 && pageIndex >= 0) {
			Panel panel = getViewForPage(Integer.valueOf(pageIndex));
			panel.add(new ProgressPanel());
			pageProgressBar = pageIndex;
		}
	}

	protected void hideProgressBarForPage(int pageIndex) {
		if (pageIndex < page.getPageCount() - 1) {
			FlowPanel panel = getViewForPage(Integer.valueOf(pageIndex));
			for (int x = 0; x < panel.getWidgetCount(); ++x) {
				if (panel.getWidget(x) instanceof ProgressPanel) {
					panel.getWidget(x).removeFromParent();
					break;
				}
			}
		}
	}

	private void reset() {
		multiPageTouchHandler.resetModelAndTimer();
		resetFocusAndStyles();
	}

	@Override
	public void resetFocusAndStyles() {
		this.focusDroped = false;
		setStylesForPages(false);
	}

	public native int getInnerWidth()/*-{
		return $wnd.innerWidth;
	}-*/;

	@Override
	public boolean isZoomed() {
		boolean zoomed = false;
		if (UserAgentChecker.isMobileUserAgent(MobileUserAgent.FIREFOX)) {
			zoomed = Window.getScrollLeft() != 0;
		} else {
			zoomed = getInnerWidth() != Window.getClientWidth() && getInnerWidth() - 1 != Window.getClientWidth();
		}
		return zoomed;
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
	public void setFlowDataSupplier(FlowDataSupplier supplier) {
		flowDataSupplier = supplier;
	}

	private void configure() {
		String xml = "<root><swipeoptions class=\"qp-swipe-options\"/></root>";
		Map<String, String> styles = styleSocket.getStyles((Element) XMLParser.parse(xml).getDocumentElement().getFirstChild());
		if (styles.get(EmpiriaStyleNameConstants.EMPIRIA_SWIPE_DISABLE_ANIMATION) != null) {
			setSwipeDisabled(true);
		} else {
			setSwipeDisabled(false);
		}
	}

	@Override
	public void init() {
		multiPageTouchHandler.setMultiPageController(this);
		view.setController(this);
		configure();
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.LOAD_PAGE_VIEW), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TOUCH_EVENT_RESERVATION), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_VIEW_LOADED), this);

		ResizeContinousUpdater resizeContinousUpdater = new ResizeContinousUpdater(pageScopeFactory, eventsBus, this);
		resizeTimer = new ResizeTimer(resizeContinousUpdater);
		view.add(mainPanel);

	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.MULTITYPE;
	}

	public boolean isSwipeDisabled() {
		return swipeDisabled;
	}

	public void setSwipeDisabled(boolean swipeDisabled) {
		this.swipeDisabled = swipeDisabled;
		if (swipeDisabled) {
			for (HandlerRegistration registration : touchHandlers) {
				registration.removeHandler();
			}
			touchHandlers.clear();
			visblePageCount = 1;
		} else {
			HasTouchHandlers touchHandler = touchRecognitionFactory.getTouchRecognition(RootPanel.get(), false);
			touchHandlers.add(touchHandler.addTouchHandler(multiPageTouchHandler, TouchEvent.getType(TouchTypes.TOUCH_START)));
			touchHandlers.add(touchHandler.addTouchHandler(multiPageTouchHandler, TouchEvent.getType(TouchTypes.TOUCH_MOVE)));
			touchHandlers.add(touchHandler.addTouchHandler(multiPageTouchHandler, TouchEvent.getType(TouchTypes.TOUCH_END)));
		}
		panelsCache.setSwipeDisabled(swipeDisabled);
	}

	public int getCurrentVisiblePage() {
		return currentVisiblePage;
	}

	public Set<Integer> getMeasuredPanels() {
		return measuredPanels;
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
		return animation.isRunning();
	}

	private float getCurrentPosition() {
		return currentPosition;
	}

	public void setSwipeLength(int swipeLength) {
		this.swipeLength = swipeLength;

	}

}
