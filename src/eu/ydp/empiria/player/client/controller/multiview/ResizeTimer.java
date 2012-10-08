package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.Timer;

public class ResizeTimer extends Timer {

	private static final int DELAY_MILLIS = 250;
	
	private static final int REPEAT_COUNT = 10;

	protected int lastSize = 0;
	
	protected int resizeCounter = 0;

	protected MultiPageController pageView;

	public ResizeTimer(MultiPageController pageview) {
		this.pageView = pageview;
	}

	@Override
	public void run() {
		int currentVisiblePage = pageView.getCurrentVisiblePage();
		int height = pageView.getHeightForPage(currentVisiblePage);
		
		if (height > 0) {
			pageView.setHeight(height);
			pageView.hideProgressBarForPage(currentVisiblePage);
			
			if (resizeCounter < REPEAT_COUNT) {
				if(lastSize != 0){
					resizeCounter++;
				}
				schedule(DELAY_MILLIS);
			} else {
				pageView.getMeasuredPanels().add(currentVisiblePage);
			}
			
			lastSize = height;
		} else {
			schedule(DELAY_MILLIS);
		}
	}
	
	@Override
	public void cancel() {
		super.cancel();
		resizeCounter = 0;
		lastSize = 0;
	}

}
