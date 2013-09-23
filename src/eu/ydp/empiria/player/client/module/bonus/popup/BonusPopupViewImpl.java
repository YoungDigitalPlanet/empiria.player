package eu.ydp.empiria.player.client.module.bonus.popup;

import javax.annotation.PostConstruct;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.util.geom.Size;
import eu.ydp.gwtutil.client.util.geom.WidgetSize;

public class BonusPopupViewImpl implements BonusPopupView{

	private static BonusPopupViewUiBinder uiBinder = GWT.create(BonusPopupViewUiBinder.class);

	@UiTemplate("BonusPopupViewImpl.ui.xml")
	interface BonusPopupViewUiBinder extends UiBinder<Widget, BonusPopupViewImpl> {}

	@UiField
	FlowPanel container;
	
	@UiField
	FlowPanel closableWrapper;
	
	@UiField
	FlowPanel content;

	private final Provider<BonusPopupPresenter> presenterProvider;
	private BonusPopupPresenter presenter;
	private final UserInteractionHandlerFactory userInteractionHandlerFactory;
	
	@Inject
	public BonusPopupViewImpl(Provider<BonusPopupPresenter> presenterProvider, UserInteractionHandlerFactory userInteractionHandlerFactory) {
		this.presenterProvider = presenterProvider;
		this.userInteractionHandlerFactory = userInteractionHandlerFactory;
	}

	@PostConstruct
	public void initialize() {
		presenter = presenterProvider.get();
		uiBinder.createAndBindUi(this);
		addClickHandlerToClosableWrapper();
	}
	
	private void addClickHandlerToClosableWrapper() {
		userInteractionHandlerFactory.applyUserClickHandler(new Command() {
			@Override
			public void execute(NativeEvent event) {
				presenter.closeClicked();
			}
		}, closableWrapper);
	}

	@Override
	public void showImage(String url, Size size) {
		content.clear();
		WidgetSize widgetSize = new WidgetSize(size);
		widgetSize.setOnWidget(content);
		
		FlowPanel panelWithBackground = new FlowPanel();
		widgetSize.setOnWidget(panelWithBackground);
		
		setBackgroundImage(url, panelWithBackground);
	}

	private void setBackgroundImage(String url, FlowPanel panelWithBackground) {
		Element element = panelWithBackground.getElement();
		Style style = element.getStyle();
		style.setBackgroundImage(url);
	}

	@Override
	public void setAnimationWidget(IsWidget widget, Size size) {
		content.clear();
		WidgetSize widgetSize = new WidgetSize(size);
		widgetSize.setOnWidget(content);
		
		content.add(widget.asWidget());
	}

	@Override
	public void attachToRoot() {
		RootPanel.get().add(container);
	}

	@Override
	public void reset() {
		content.clear();
		RootPanel.get().remove(container);
	}
	
}
