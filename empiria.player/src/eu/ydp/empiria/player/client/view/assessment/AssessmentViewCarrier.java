package eu.ydp.empiria.player.client.view.assessment;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.model.Assessment;
import eu.ydp.empiria.player.client.view.sockets.ViewSocket;

public class AssessmentViewCarrier {

	public AssessmentViewCarrier(Assessment a, ViewSocket hvs, ViewSocket fvs){
		headerViewSocket = hvs;
		footerViewSocket = fvs;
	}
	
	private ViewSocket headerViewSocket;
	private ViewSocket footerViewSocket;
	
	public Widget getHeaderView(){
		if (headerViewSocket != null)
			return headerViewSocket.getView();
		return null;
	}
	
	public Widget getFooterView(){
		if (footerViewSocket != null)
			return footerViewSocket.getView();
		return null;
	}
	
}
