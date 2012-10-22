package eu.ydp.empiria.player.client.module.connection.presenter;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class ConnectionModulePresenterImpl implements ConnectionModulePresenter, PairConnectEventHandler  {
	
	private MatchInteractionBean bean;
	
	private ConnectionModuleModel model;

	private MultiplePairModuleView moduleView;
	
	private ModuleSocket moduleSocket;
	
	@Override
	public void setModuleSocket(ModuleSocket moduleSocket) {
		this.moduleSocket = moduleSocket;
	}
	
	@Override
	public void bindView() {
		moduleView.setBean(bean);
		moduleView.bindView();
		
		moduleView.reset();
	}

	@Override
	public void reset() {
		moduleView.reset();
	}

	@Override
	public void setModel(ConnectionModuleModel model) {
		this.model = model;
	}

	@Override
	public void setBean(MatchInteractionBean bean) {
		this.bean = bean;
	}

	@Override
	public void setLocked(boolean locked) {
		moduleView.setLocked(locked);
	}

	@Override
	public void showCorrectAnswers() {
		// TODO Auto-generated method stub
	}

	@Override
	public void showCurrentAnswers() {
		// TODO Auto-generated method stub
	}

	@Override
	public void markCorrectAnswers() {
		setAnswersMarked(true, MultiplePairModuleConnectType.CORRECT);
	}

	@Override
	public void markWrongAnswers() {
		setAnswersMarked(true, MultiplePairModuleConnectType.WRONG);
	}
	
	@Override
	public void unmarkCorrectAnswers() {
		setAnswersMarked(false, MultiplePairModuleConnectType.CORRECT);
	}

	@Override
	public void unmarkWrongAnswers() {
		setAnswersMarked(true, MultiplePairModuleConnectType.WRONG);
	}

	/**
	 * Marks / unmarks answers
	 * 
	 * @param markMode - true: mark, false: unmark
	 * @param markingType - {@link MultiplePairModuleConnectType#CORRECT} or {@link MultiplePairModuleConnectType#WRONG} 
	 */
	private void setAnswersMarked(boolean markMode, MultiplePairModuleConnectType markingType) {
		List<Boolean> responseEvaluated = evaluateResponse();		
		List<KeyValue<String, String>> currentAnswers = model.getCurrentAnswers();
		
		// TODO: jesli dana pozycja nie jest zaznaczona wcale to wyslac MultiplePairModuleConnectType.NORMAL
		
		int responseCnt = 0;
		for (Boolean isCorrect : responseEvaluated) {
			MultiplePairModuleConnectType type = (isCorrect) ? MultiplePairModuleConnectType.CORRECT : MultiplePairModuleConnectType.WRONG;			
			KeyValue<String, String> correctPair = currentAnswers.get(responseCnt);
			if (markingType.equals(type)) {
				if (markMode) {
					moduleView.connect(correctPair.getKey(), correctPair.getValue(), type);
				} else {
					moduleView.disconnect(correctPair.getKey(), correctPair.getValue());
					// TODO: restore user state
				}
			}
			responseCnt++; 
		}		
	}	
	
	@Override
	public Widget asWidget() {
		return moduleView.asWidget();
	}

	@Override
	public IsWidget getFeedbackPlaceholderByIdentifier(String identifier) {
		// TODO to be implemented
		return null;
	}
	
	@Override
	public void onConnectionEvent(PairConnectEvent event) {
		switch (event.getType()) {
		case CONNECTED:
			if (isConnectionValid(event.getSourceItem(), event.getTargetItem())) {
				model.addAnswer(event.getItemsPair());
			}
			break;
		case DISCONNECTED:			
			model.removeAnswer(event.getItemsPair());
			break;
		case WRONG_CONNECTION:
		default:
			/* TODO: to handle incorrect situation */
			break;
		}
		
	}

	private boolean isConnectionValid(String sourceItem, String targetItem) {
		// TODO: to be implemented
		return true;
	}
	
	private List<Boolean> evaluateResponse() {
		return moduleSocket.evaluateResponse(model.getResponse());
	}
	

}
