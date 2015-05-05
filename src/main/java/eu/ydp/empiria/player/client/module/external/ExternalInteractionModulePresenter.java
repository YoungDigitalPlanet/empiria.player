package eu.ydp.empiria.player.client.module.external;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionEmpiriaApi;
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
	private Optional<ExternalInteractionObject> externalObject;

	@Inject
	public ExternalInteractionModulePresenter(@ModuleScoped ExternalInteractionView view, ExternalInteractionEmpiriaApi empiriaApi, EmpiriaPaths empiriaPaths) {
		this.empiriaPaths = empiriaPaths;
		this.view = view;
		this.empiriaApi = empiriaApi;
		this.externalObject = Optional.absent();
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
		if (externalObject.isPresent()) {
			externalObject.get().reset();
		}
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
			showCorrectAnswers();
			break;
		case USER:
			hideCorrectAnswers();
			break;
		}
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void onExternalModuleLoaded(ExternalInteractionObject externalObject) {
		this.externalObject = Optional.of(externalObject);
	}

	public JSONArray getState() {
		if (externalObject.isPresent()) {
			JavaScriptObject state = externalObject.get().getStateFromExternal();
			return new JSONArray(state);
		}
		return new JSONArray();
	}

	public void setState(JSONArray stateAndStructure) {
		if (externalObject.isPresent()) {
			externalObject.get().setStateFromEmpiriaOnExternal(stateAndStructure.getJavaScriptObject());
		}
	}

	private void lock() {
		if (externalObject.isPresent()) {
			externalObject.get().lock();
		}
	}

	private void unlock() {
		if (externalObject.isPresent()) {
			externalObject.get().unlock();
		}
	}

	private void markAnswers(MarkAnswersType type) {
		switch (type) {
		case CORRECT:
			markCorrectAnswers();
			break;
		case WRONG:
			markWrongAnswers();
			break;
		}
	}

	private void unmarkAnswers(MarkAnswersType type) {
		switch (type) {
		case CORRECT:
			unmarkCorrectAnswers();
			break;
		case WRONG:
			unmarkWrongAnswers();
			break;
		}
	}

	private void unmarkCorrectAnswers() {
		if (externalObject.isPresent()) {
			externalObject.get().unmarkCorrectAnswers();
		}
	}

	private void unmarkWrongAnswers() {
		if (externalObject.isPresent()) {
			externalObject.get().unmarkWrongAnswers();
		}
	}

	private void markCorrectAnswers() {
		if (externalObject.isPresent()) {
			externalObject.get().markCorrectAnswers();
		}
	}

	private void markWrongAnswers() {
		if (externalObject.isPresent()) {
			externalObject.get().markWrongAnswers();
		}
	}

	private void hideCorrectAnswers() {
		if (externalObject.isPresent()) {
			externalObject.get().hideCorrectAnswers();
		}
	}

	private void showCorrectAnswers() {
		if (externalObject.isPresent()) {
			externalObject.get().showCorrectAnswers();
		}
	}
}
