package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class ExplorableImgWindowImg extends ExplorableImgWindowBase {

	private static ExplorableImgWindowImgUiBinder uiBinder = GWT.create(ExplorableImgWindowImgUiBinder.class);

	interface ExplorableImgWindowImgUiBinder extends UiBinder<Widget, ExplorableImgWindowImg> {}

	@UiField
	FlowPanel windowPanel;
	@UiField
	FlowPanel imagePanel;
	@UiField
	Image image;
	
	private boolean imageLoaded = false;
	private int prevX = -1, prevY = -1;
	
	public ExplorableImgWindowImg() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void init(int wndWidth, int wndHeight, String imageUrl, double initialScale) {
		setWindowWidth(wndWidth);
		setWindowHeight(wndHeight);
		setScale(initialScale);
		
		windowPanel.setSize(String.valueOf(getWindowWidth()) + "px", String.valueOf(getWindowHeight()) + "px");
		windowPanel.getElement().getStyle().setOverflow(Overflow.AUTO);
		
		image.setUrl(imageUrl);

		image.addLoadHandler(new LoadHandler() {
			
			@Override
			public void onLoad(LoadEvent event) {	
				imageLoaded = true;
				
				setOriginalImageWidth(image.getWidth());
				setOriginalImageHeight( image.getHeight());
				
				findScaleMinAndOriginalAspectRatio();
				
				scaleTo(getScale());
				centerImage();
			}
		});
		
		image.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
			}
		});
		
		image.addMouseDownHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.preventDefault();
				prevX = event.getClientX();
				prevY = event.getClientY();
			}
		});
		
		image.addMouseMoveHandler(new MouseMoveHandler() {
			
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				event.preventDefault();

				if (prevX != -1  &&  prevY != -1){
					int scrollLeft = windowPanel.getElement().getScrollLeft();
					windowPanel.getElement().setScrollLeft(scrollLeft - (event.getClientX() - prevX));
					prevX = event.getClientX();
					
					int scrollTop = windowPanel.getElement().getScrollTop();
					windowPanel.getElement().setScrollTop(scrollTop - (event.getClientY() - prevY));
					prevY = event.getClientY();
				}
			}
		});
		
		image.addMouseUpHandler(new MouseUpHandler() {
			
			@Override
			public void onMouseUp(MouseUpEvent event) {
				event.preventDefault();
				prevX = -1;
				prevY = -1;
			}
		});
	}

	@Override
	public void zoomIn() {
		scaleBy(SCALE_STEP);
	}

	@Override
	public void zoomOut() {
		scaleBy(1.0d/SCALE_STEP);
	}
	
	private void centerImage(){
		
		windowPanel.getElement().setScrollLeft((image.getWidth() - windowPanel.getOffsetWidth() ) / 2);
		windowPanel.getElement().setScrollTop((image.getHeight() - windowPanel.getOffsetHeight() ) / 2); 
	}
	
	private void scaleBy(double dScale){
		double newScale;
		if (getZoom()*dScale > ZOOM_MAX)
			newScale = (double)getOriginalImageWidth() / (double)getWindowWidth() * (ZOOM_MAX);
		else if (getScale() * dScale > getScaleMin())
			newScale = getScale()*dScale;
		else
			newScale = getScaleMin();
		
		scaleTo(newScale);
	}
	
	private void scaleTo(double newScale){
		
		int lastCenterLeft = windowPanel.getElement().getScrollLeft() + getWindowWidth()/2;
		int lastCenterTop = windowPanel.getElement().getScrollTop() + getWindowHeight()/2;
		
		int newCenterLeft = (int)( lastCenterLeft * (newScale/getScale()) );
		int newCenterTop = (int)( lastCenterTop * (newScale/getScale()) );
 
		int nextScrollLeft = newCenterLeft - getWindowWidth()/2;
		int nextScrollTop = newCenterTop - getWindowHeight()/2;
		
		setScale(newScale);

		double newImageWidth = getWindowWidth() * getScale();
		double newImageheight = newImageWidth / getOriginalAspectRatio();
		
		image.setSize(String.valueOf((int)newImageWidth) + "px", String.valueOf((int)newImageheight) + "px");
		
		windowPanel.getElement().setScrollLeft(nextScrollLeft);
		windowPanel.getElement().setScrollTop(nextScrollTop); 
		
	}
	
	

}
