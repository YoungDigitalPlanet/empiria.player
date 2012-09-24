package eu.ydp.empiria.player.client.controller.multiview;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.multiview.animation.Animation;
import eu.ydp.empiria.player.client.controller.multiview.animation.AnimationEndCallback;
import eu.ydp.empiria.player.client.module.button.NavigationButtonDirection;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.scheduler.Scheduler;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class MultiPageController implements PlayerEventHandler, FlowRequestSocketUserExtension, FlowDataSocketUserExtension,Extension, TouchHandler {

	@Inject
	protected EventsBus eventsBus;
	@Inject
	protected StyleNameConstants styleNames;
	@Inject
	protected Scheduler scheduler;
	@Inject
	protected PanelCache panelsCache;
	@Inject
	protected Page page;
	@Inject
	protected GWTPanelFactory panelFactory;
	@Inject
	protected PageEventsHandler pageEvents;
	@Inject
	protected MultiPageView view;
	@Inject
	protected StyleSocket styleSocket;
	@Inject
	protected Animation animation;
	private static int activePageCount = 3;
	private int currentVisiblePage = -1;
	private int start;
	private int lastEnd = 0;
	private int end = 0;
	private int startY = 0, endY = 0;
	private int swipeLength = 220;
	private final static int WIDTH = 100;
	private final static int DEFAULT_ANIMATION_TIME = 500;
	private final static int QUICK_ANIMATION_TIME = 150;
	private final static int TOUCH_END_TIMER_TIME = 800;
	private float currentPosition;
	private FlowRequestInvoker flowRequestInvoker;
	private boolean touchReservation = false;
	private boolean swipeStarted = false;
	private boolean swipeRight = false;
	private boolean touchLock = false;
	private FlowPanel mainPanel;
	private final Set<Integer> measuredPanels = new HashSet<Integer>();
	private final Set<Integer> detachedPages = new HashSet<Integer>();
	/**
	 * ktore strony zostaly zaladowane
	 */
	private final Set<Integer> loadedPages = new HashSet<Integer>();
	private static int visblePageCount = 3;
	private int pageProgressBar = -1;

	private ResizeTimer resizeTimer;
	private AnimationEndCallback animationCallback = null;
	private boolean swipeDisabled = false;
	private final Timer touchEndTimer = new Timer() {

		@Override
		public void run() {
			animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
			swipeStarted = false;
			resetPostionValues();
		}
	};
	private FlowDataSupplier flowDataSupplier;

	private void setStylesForPages(boolean swipeStarted) {
		if (swipeStarted) {
			Panel selectedPanel = panelsCache.get(currentVisiblePage).getKey();
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

	protected void setSwipeLength(int length) {
		swipeLength = length;
	}

	protected void setVisiblePanels(int pageNumber) {
		showProgressBarForPage(pageNumber);
		if (!measuredPanels.contains(pageNumber)) {
			resizeTimer.cancel();
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
		KeyValue<FlowPanel, FlowPanel> panel = null;
		if (panelsCache.isPresent(pageNumber)) {
			panel = panelsCache.get(pageNumber);
		} else {
			if (panelsCache.isEmpty()) {
				for (int index = 0; index <= pageNumber; ++index) {
					panel = createAndPutToCache(index);
				}
			} else {
				Integer maxIndex = panelsCache.getCache().keySet().iterator().next();
				if (pageNumber > maxIndex) {
					for (int index = maxIndex + 1; index <= pageNumber; ++index) {
						panel = createAndPutToCache(index);
					}
				}
			}
			panel.getKey().addStyleName(styleNames.QP_PAGE_UNSELECTED());
			panel.getKey().addStyleName(pageNumber > currentVisiblePage ? styleNames.QP_PAGE_NEXT() : styleNames.QP_PAGE_PREV());
			showProgressBarForPage(pageNumber);
		}
		loadedPages.add(pageNumber);
		return panel.getValue();
	}

	private KeyValue<FlowPanel, FlowPanel> createAndPutToCache(int index) {
		KeyValue<FlowPanel, FlowPanel> panel = panelsCache.get(index);
		mainPanel.add(panel.getKey());
		return panel;
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		switch (event.getType()) {
		case TOUCH_EVENT_RESERVATION:
			reset();
			resetPostionValues();
			this.touchReservation = true;
			break;
		case ASSESSMENT_STARTED:
			onAssesmentStart();
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

	private void onAssesmentStart() {
		setSwipeDisabled(flowDataSupplier.getPageCount() < 2);
	}

	private void scheduleDeferedRemoveFromParent(final int page) {
		scheduler.scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				panelsCache.get(page).getValue().removeFromParent();
			}
		});
	}

	private void scheduleDeferedAttachToParent(final KeyValue<FlowPanel, FlowPanel> pair, final int pageNumber) {
		scheduler.scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				pair.getKey().add(pair.getValue());
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
				final KeyValue<FlowPanel, FlowPanel> pair = panelsCache.get(page);
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

	private void animatePageSwitch(float from, final float to, final NavigationButtonDirection direction, int duration, final boolean onlyPositionReset) {// NOPMD
		if (Math.abs(from - to) > 1) {
			if (!onlyPositionReset) {
				Window.scrollTo(0, 0);
			}
			animation.removeAnimationEndCallback(animationCallback);
			animationCallback = new AnimationEndCallback() {
				@Override
				public void onComplate() {

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

	protected float getPositionLeft() {
		Style style = mainPanel.getElement().getStyle();
		return NumberUtils.tryParseFloat(style.getLeft().replaceAll("[a-z%]+$", ""));

	}

	private void move(boolean swipeRight, float length) {
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
		touchEndTimer.cancel();
		touchReservation = false;
		swipeStarted = false;
		swipeRight = false;
		touchLock = false;
		setStylesForPages(swipeStarted);

	}

	private void resetPostionValues() {
		lastEnd = start = end;
	}

	public native int getInnerWidth()/*-{
		return $wnd.innerWidth;
	}-*/;

	public boolean isZoomed() {
		boolean zoomed = false;
		if (UserAgentChecker.isMobileUserAgent(MobileUserAgent.FIREFOX)) {
			zoomed = Window.getScrollLeft() != 0;
		} else {
			zoomed = getInnerWidth() != Window.getClientWidth() && getInnerWidth()-1 !=Window.getClientWidth();
		}
		return zoomed;
	}

	private boolean isHorizontalSwipe(){
		return Math.abs(startY - endY) < Math.abs(start - end);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.controller.multiview.TouchHandler#onTouchMove (com.google.gwt.dom.client.NativeEvent)
	 */
	@Override
	public void onTouchMove(NativeEvent event) {
		// nie zawsze wystepuje event touchend na androidzie - emulacja zachownaia
		touchEndTimer.cancel();
		if (!touchReservation && !touchLock && !animation.isRunning()) {
			JsArray<Touch> touches = event.getTouches();
			if (touches == null) {
				end = event.getScreenX();
			} else {
				for (int x = 0; x < touches.length();) {
					Touch touch = touches.get(x);
					end = touch.getPageX();
					endY = touch.getScreenY();
					break;
				}
			}
			if (!isZoomed() && isHorizontalSwipe()) {
				event.preventDefault();
			}
			if (isHorizontalSwipe()) {
				touchEndTimer.schedule(TOUCH_END_TIMER_TIME);
				if (!isZoomed()) {
					swipeStarted = true;
					setStylesForPages(swipeStarted);
					if (lastEnd != end && lastEnd > 0) {
						swipeRight = (lastEnd > end);
						move(swipeRight, ((float) Math.abs(lastEnd - end) / RootPanel.get().getOffsetWidth()) * 100);
						lastEnd = end;
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.controller.multiview.TouchHandler#onTouchEnd (com.google.gwt.dom.client.NativeEvent)
	 */
	@Override
	public void onTouchEnd(NativeEvent event) {
		touchEndTimer.cancel();
		if (swipeStarted) {
			event.preventDefault();
		}
		if (!touchReservation && swipeStarted) {
			reset();
			JsArray<Touch> touches = event.getChangedTouches();
			if (touches == null) {
				end = event.getScreenX();
			} else {
				for (int x = 0; x < touches.length();) {
					Touch touch = touches.get(x);
					end = touch.getPageX();
					endY = touch.getScreenY();
					break;
				}
			}
			if (end > 0 && (Window.getClientHeight() / 2.5) > (startY > endY ? startY - endY : endY - startY)) {
				switchPage(swipeLength);
			} else {
				animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
			}
		} else {
			reset();
		}
		resetPostionValues();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see eu.ydp.empiria.player.client.controller.multiview.TouchHandler#onTouchStart (com.google.gwt.dom.client.NativeEvent)
	 */
	@Override
	public void onTouchStart(NativeEvent event) {
		if (!touchReservation && !animation.isRunning()) {
			reset();
			JsArray<Touch> touches = event.getTouches();
			if (touches == null) {
				lastEnd = start = event.getScreenX();
			} else {
				for (int x = 0; x < touches.length();) {
					Touch touch = touches.get(x);
					lastEnd = start = touch.getPageX();
					startY = touch.getScreenY();
					end = -1;
					break;
				}
				touchLock = touches.length() > 1;
			}
		}
	}

	public NavigationButtonDirection getDirection() {
		NavigationButtonDirection direction = null;
		if (end > start) {
			direction = NavigationButtonDirection.PREVIOUS;
		} else if (start > end) {
			direction = NavigationButtonDirection.NEXT;
		}
		return direction;

	}

	private void switchPage(int minSwipeLength) {
		if (touchReservation) {
			animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
			return;
		}
		int swipeLength = Math.abs(start - end);
		if (swipeLength >= minSwipeLength) {
			NavigationButtonDirection direction = getDirection();
			float percent = (float) swipeLength / RootPanel.get().getOffsetWidth() * 100;
			if (direction != null) {
				if (direction == NavigationButtonDirection.PREVIOUS && page.getCurrentPageNumber() > 0) {
					animatePageSwitch(getPositionLeft(), getPositionLeft() + (WIDTH - percent), direction, DEFAULT_ANIMATION_TIME, true);
				} else if (direction == NavigationButtonDirection.NEXT && page.getCurrentPageNumber() < page.getPageCount() - 1) {
					animatePageSwitch(getPositionLeft(), getPositionLeft() - (WIDTH - percent), direction, DEFAULT_ANIMATION_TIME, true);
				} else {
					animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
				}
			}
		} else {
			animatePageSwitch(getPositionLeft(), currentPosition, null, QUICK_ANIMATION_TIME, true);
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
		}
	}

	@Override
	public void init() {
		configure();
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.LOAD_PAGE_VIEW), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.ASSESSMENT_STARTED), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TOUCH_EVENT_RESERVATION), this);
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_VIEW_LOADED), this);
		mainPanel = panelFactory.getFlowPanel();
		resizeTimer = new ResizeTimer(this);
		view.setController(this);
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
		if (!swipeDisabled) {
			pageEvents.setTouchHandler(this);
		}else{
			pageEvents.removeTouchHandler(this);
		}
		panelsCache.setSwipeDisabled(swipeDisabled);
		view.setSwipeDisabled(swipeDisabled);
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

}
