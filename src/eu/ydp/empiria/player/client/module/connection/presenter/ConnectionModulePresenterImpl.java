package eu.ydp.empiria.player.client.module.connection.presenter;

import static eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType.CORRECT;
import static eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType.NORMAL;
import static eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType.WRONG;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleView;
import eu.ydp.empiria.player.client.module.connection.ConnectionModuleModel;
import eu.ydp.empiria.player.client.module.connection.structure.MatchInteractionBean;
import eu.ydp.empiria.player.client.module.connection.structure.SimpleAssociableChoiceBean;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.multiplepair.PairConnectEventHandler;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class ConnectionModulePresenterImpl implements ConnectionModulePresenter, PairConnectEventHandler {

	private MatchInteractionBean bean;

	ConnectionModuleModel model;

	@Inject
	private MultiplePairModuleView moduleView;

	private ModuleSocket moduleSocket;

	@Override
	public void setModuleSocket(ModuleSocket moduleSocket) {
		this.moduleSocket = moduleSocket;
	}

	@Override
	public void bindView() {
		moduleView.addPairConnectEventHandler(this);
		moduleView.setBean(bean);
		moduleView.setModuleSocket(moduleSocket);
		moduleView.bindView();

		moduleView.reset();
	}

	@Override
	public void reset() {
		moduleView.reset();
	}

	@Override
	public void setModuleView(MultiplePairModuleView<SimpleAssociableChoiceBean> moduleView) {
		this.moduleView = moduleView;
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
	public void showAnswers(ShowAnswersType mode) {
		showAnswers(model.getCorrectAnswers(), (mode == ShowAnswersType.CORRECT) ? CORRECT : NORMAL);
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		setAnswersMarked(mode == MarkAnswersMode.MARK, (type == MarkAnswersType.CORRECT) ? CORRECT : WRONG);

	}

	@Override
	public Widget asWidget() {
		return moduleView.asWidget();
	}

	@Override
	public void onConnectionEvent(PairConnectEvent event) {

		DirectedPair pair = getDirectedPair(event);

		switch (event.getType()) {
		case CONNECTED:
			if (event.isUserAction()) {
				boolean isResponseExists = model.checkUserResonseContainsAnswer(pair.toString());
				if (isConnectionValid(pair) && !isResponseExists) {
					model.addAnswer(getDirectedPair(event).toString());
				} else {
					moduleView.disconnect(event.getSourceItem(), event.getTargetItem());
				}
			}
			break;
		case DISCONNECTED:
			if (event.isUserAction() && pair.getSource() != null && pair.getTarget() != null) {
				model.removeAnswer(getDirectedPair(event).toString());
			}
			break;
		case WRONG_CONNECTION:
		default:
			/* TODO: to handle incorrect situation */
			break;
		}
	}

	private DirectedPair getDirectedPair(PairConnectEvent event) {
		DirectedPair pair = new DirectedPair();

		String start = event.getSourceItem();
		String end = event.getTargetItem();

		if (bean.getSourceChoicesIdentifiersSet().contains(start)) {
			pair.setSource(start);
		} else if (bean.getTargetChoicesIdentifiersSet().contains(start)) {
			pair.setTarget(start);
		}

		if (bean.getSourceChoicesIdentifiersSet().contains(end)) {
			pair.setSource(end);
		} else if (bean.getTargetChoicesIdentifiersSet().contains(end)) {
			pair.setTarget(end);
		}

		return pair;
	}

	private boolean isConnectionValid(DirectedPair pair) {
		int errorsCount = 0;

		if (pair.getSource() == null || pair.getTarget() == null) {
			// invalid pair
			errorsCount++;
		} else if (bean.getMaxAssociations() > 0 && model.getCurrentOverallPairingsNumber() >= bean.getMaxAssociations()) {
			// The maxAssociations attribute controls the maximum number of
			// pairings the user is allowed to make overall.
			errorsCount++;
		} else if (bean.getChoiceByIdentifier(pair.getSource()).getMatchMax() > 0
				&& model.getCurrentChoicePairingsNumber(pair.getSource()) >= bean.getChoiceByIdentifier(pair.getSource()).getMatchMax()) {
			// Individually, each choice has a matchMax attribute that controls
			// how many pairings it can be part of.
			errorsCount++;
		} else if (bean.getChoiceByIdentifier(pair.getTarget()).getMatchMax() > 0
				&& model.getCurrentChoicePairingsNumber(pair.getTarget()) >= bean.getChoiceByIdentifier(pair.getTarget()).getMatchMax()) {
			errorsCount++;
		}

		return errorsCount == 0;
	}

	/**
	 * Sets connections in view using given {@link KeyValue} collection for
	 * defined {@link MultiplePairModuleConnectType}
	 *
	 * @param answers
	 * @param type
	 */
	private void showAnswers(List<KeyValue<String, String>> answers, MultiplePairModuleConnectType type) {
		moduleView.reset();
		for (KeyValue<String, String> answer : answers) {
			moduleView.connect(answer.getKey(), answer.getValue(), type);
		}
	}

	/**
	 * Marks / unmarks answers
	 *
	 * @param markMode
	 *            - {@link Boolean} mark/unmark
	 * @param markingType
	 *            - {@link MultiplePairModuleConnectType#CORRECT} or
	 *            {@link MultiplePairModuleConnectType#WRONG}
	 */
	private void setAnswersMarked(boolean markMode, MultiplePairModuleConnectType markingType) {
		List<Boolean> responseEvaluated = model.evaluateResponse();
		List<KeyValue<String, String>> currentAnswers = model.getCurrentAnswers();

		int responseCnt = 0;
		for (Boolean isCorrect : responseEvaluated) {
			MultiplePairModuleConnectType type = (isCorrect) ? MultiplePairModuleConnectType.CORRECT : MultiplePairModuleConnectType.WRONG;
			KeyValue<String, String> answersPair = currentAnswers.get(responseCnt);
			if (markingType.equals(type)) {
				if (markMode) {
					moduleView.disconnect(answersPair.getKey(), answersPair.getValue());
					moduleView.connect(answersPair.getKey(), answersPair.getValue(), type);
					// TODO: jesli dana pozycja nie jest zaznaczona wcale to
					// wyslac MultiplePairModuleConnectType.NONE ??
				} else {
					moduleView.disconnect(answersPair.getKey(), answersPair.getValue());
					moduleView.connect(answersPair.getKey(), answersPair.getValue(), MultiplePairModuleConnectType.NORMAL); // TODO:
																															// NORMAL
				}
			}
			responseCnt++;
		}
	}

	private class DirectedPair {
		private String source;
		private String target;

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		@Override
		public String toString() {
			return source + " " + target;
		}
	}

}
