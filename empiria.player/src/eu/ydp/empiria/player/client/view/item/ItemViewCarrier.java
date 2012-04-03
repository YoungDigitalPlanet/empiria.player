package eu.ydp.empiria.player.client.view.item;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ItemViewCarrier {

	public ItemViewCarrier(Widget title, Widget _contentView, Widget _feedbackView, Widget _scoreView){
		titleView = title;
		contentView = _contentView;
		feedbackView = _feedbackView;
		scoreView = _scoreView;
		errorView = null;
	}
	
	public ItemViewCarrier(Widget _contentView){
		contentView = _contentView;
	}
	
	public ItemViewCarrier(String err){
		errorView = new FlowPanel();
		errorView.setStyleName("qp-item-error");
		
		Label errorLabel = new Label(err);
		errorLabel.setStyleName("qp-item-error-text");
		errorView.add(errorLabel);
	}
	
	private Widget titleView;
	private Widget contentView;
	private Widget feedbackView;
	private Widget scoreView;
	private Panel errorView;
	
	public boolean isError(){
		return errorView != null;
	}
	
	public Widget getErrorView(){
		return errorView;
	}

	public Widget getTitleView(){
		return titleView;
	}
	public Widget getContentView(){
		return contentView;
	}
	public Widget getFeedbackView(){
		return feedbackView;
	}
	public Widget getScoreView(){
		return scoreView;
	}
}
