package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionNullObject;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionObject;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionModulePresenter
		implements ActivityPresenter<ExternalInteractionResponseModel, ExternalInteractionModuleBean>, ExternalInteractionFrameLoadHandler {

	private final EmpiriaPaths empiriaPaths;
	private final ExternalInteractionView view;
	private ExternalInteractionModuleBean externalInteractionModuleBean;
	private ExternalInteractionEmpiriaApi empiriaApi;
	private ExternalStateUtil stateUtil;
	private ExternalInteractionObject externalObject;

	@Inject
	public ExternalInteractionModulePresenter(@ModuleScoped ExternalInteractionView view, ExternalInteractionEmpiriaApi empiriaApi, EmpiriaPaths empiriaPaths,
			ExternalStateUtil stateUtil) {
		this.empiriaPaths = empiriaPaths;
		this.view = view;
		this.empiriaApi = empiriaApi;
		this.stateUtil = stateUtil;
		this.externalObject = new ExternalInteractionNullObject();
	}

	@Override
	public void bindView() {
		view.init(empiriaApi, this);

		String src = externalInteractionModuleBean.getSrc();
		String externalModuleFilePath = empiriaPaths.getMediaFilePath(src);
		view.setUrl(externalModuleFilePath);
	}

	@Override
	public void reset() {
		externalObject.reset();
	}

	@Override
	public void setModel(ExternalInteractionResponseModel model) {
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
	}

	@Override
	public void setBean(ExternalInteractionModuleBean externalInteractionModuleBean) {
		this.externalInteractionModuleBean = externalInteractionModuleBean;
	}

	@Override
	public void setLocked(boolean locked) {
		if (locked) {
			lock();
		} else {
			unlock();
		}
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		switch (mode) {
		case MARK:
			markAnswers(type);
			break;
		case UNMARK:
			unmarkAnswers(type);
			break;
		}
	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		switch (mode) {
		case CORRECT:
			externalObject.showCorrectAnswers();
			break;
		case USER:
			externalObject.hideCorrectAnswers();
			break;
		}
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void onExternalModuleLoaded(ExternalInteractionObject externalObject) {
		this.externalObject = externalObject;
	}

	public JSONArray getState() {
		JavaScriptObject state = externalObject.getStateFromExternal();
		return stateUtil.wrapState(state);
	}

	public void setState(JSONArray stateAndStructure) {
		JavaScriptObject state = stateUtil.unwrapState(stateAndStructure);
		externalObject.setStateFromEmpiriaOnExternal(state);
	}

	private void lock() {
		externalObject.lock();
	}

	private void unlock() {
		externalObject.unlock();
	}

	private void markAnswers(MarkAnswersType type) {
		switch (type) {
		case CORRECT:
			externalObject.markCorrectAnswers();
			break;
		case WRONG:
			externalObject.markWrongAnswers();
			break;
		}
	}

	private void unmarkAnswers(MarkAnswersType type) {
		switch (type) {
		case CORRECT:
			externalObject.unmarkCorrectAnswers();
			break;
		case WRONG:
			externalObject.unmarkWrongAnswers();
			break;
		}
	}
}
