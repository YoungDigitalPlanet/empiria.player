package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.fill.CanvasImageData;
import eu.ydp.empiria.player.client.module.colorfill.fill.CanvasImageDataSlower;
import eu.ydp.empiria.player.client.module.colorfill.fill.ICanvasImageData;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class CanvasImageDataProvider {

	private final UserAgentUtil userAgentUtil;
	
	@Inject
	public CanvasImageDataProvider(UserAgentUtil userAgentUtil) {
		this.userAgentUtil = userAgentUtil;
	}

	public ICanvasImageData getCanvasImageData(CanvasImageView canvasStubView){
		Context2d context2d = canvasStubView.getCanvas().getContext2d();
		int width = canvasStubView.getWidth(); 
		int height = canvasStubView.getHeight();
		
		ICanvasImageData canvasImageData;
		if(isInternetExplorer()){
			canvasImageData = new CanvasImageDataSlower(context2d, width, height);
		} else {
			canvasImageData = new CanvasImageData(context2d, width, height);
		}
		
		return canvasImageData;
	}

	private boolean isInternetExplorer() {
		return userAgentUtil.isIE();
	}
	
}
