package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.Assessment;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class AssessmentViewCarrier {
	
	private Assessment assessment;

	public AssessmentViewCarrier(Assessment a, ViewSocket hvs, ViewSocket fvs){
		headerViewSocket = hvs;
		footerViewSocket = fvs;
		assessment = a;
	}
	
	private ViewSocket headerViewSocket;
	private ViewSocket footerViewSocket;
	
	public Widget getHeaderView(){
		if (headerViewSocket != null)
			return headerViewSocket.getView();
		return null;
	}
	
	public Panel getPageSlot(){
		return assessment.getPageSlot();
	}
	
	public Widget getSkinView(){
		return assessment.getSkinView(); 
	}
	
	public Widget getFooterView(){
		if (footerViewSocket != null)
			return footerViewSocket.getView();
		return null;
	}
	
}
