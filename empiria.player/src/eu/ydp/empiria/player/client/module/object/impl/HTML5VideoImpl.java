package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;


public class HTML5VideoImpl extends Composite implements VideoImpl{

	protected HTML html;
	
	public HTML5VideoImpl(){
		
		html = new HTML();
		
		initWidget(html);
	}
	
	public void setSrc(String src){
		html.setHTML("<video src='" + src + "' controls='true'>Video not supported!</video>");;
	}
}
