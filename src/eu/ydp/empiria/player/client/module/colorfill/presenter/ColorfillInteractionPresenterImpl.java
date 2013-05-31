package eu.ydp.empiria.player.client.module.colorfill.presenter;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillInteractionModuleModel;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillInteractionBean;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;

public class ColorfillInteractionPresenterImpl implements ColorfillInteractionPresenter {

	private final ColorfillInteractionView interactionView;
	private final ColorfillInteractionModuleModel model;	
	private final ResponseUserAnswersConverter responseUserAnswersConverter;
	private final ResponseAnswerByViewBuilder responseAnswerByViewBuilder;
	private final ColorfillViewBuilder colorfillViewBuilder;
	private final ColorButtonsController colorButtonsController;
	
	private ColorfillInteractionBean bean;
	private List<Area> areas;
	
	@Inject
	public ColorfillInteractionPresenterImpl(
			ResponseUserAnswersConverter responseUserAnswersConverter,
			ResponseAnswerByViewBuilder responseAnswerByViewBuilder,
			ColorfillViewBuilder colorfillViewBuilder,
			ColorButtonsController colorButtonsController,
			@ModuleScoped ColorfillInteractionView interactionView,
			@ModuleScoped ColorfillInteractionModuleModel model) {
		this.responseUserAnswersConverter = responseUserAnswersConverter;
		this.responseAnswerByViewBuilder = responseAnswerByViewBuilder;
		this.colorfillViewBuilder = colorfillViewBuilder;
		this.colorButtonsController = colorButtonsController;
		this.interactionView = interactionView;
		this.model = model;
	}


	@Override
	public void bindView() {
		colorfillViewBuilder.buildView(bean);
	}

	@Override
	public void imageColorChanged(Area area) {
		ColorModel currentSelectedButtonColor = colorButtonsController.getCurrentSelectedButtonColor();
		if(currentSelectedButtonColor != null){
			interactionView.setColor(area, currentSelectedButtonColor);
			List<String> userAnswers = responseAnswerByViewBuilder.buildNewResponseAnswersByCurrentImage(areas);
			model.setNewUserAnswers(userAnswers);
		}
	}


	@Override
	public void buttonClicked(ColorModel color) {
		colorButtonsController.colorButtonClicked(color);
	}

	@Override
	public void reset() {
		//Unused - will be implemented later
	}

	@Override
	public void setModel(ColorfillInteractionModuleModel model) {
		//Unused - will be removed in the future
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
	}

	@Override
	public void setBean(ColorfillInteractionBean bean) {
		this.bean = bean;
		areas = bean.getAreas().getAreas();
	}

	@Override
	public void setLocked(boolean locked) {
		//Unused - will be implemented later
	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		//Unused - will be implemented later
	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		if(mode == ShowAnswersType.USER){
			List<String> currentAnswers = model.getCurrentAnswers();
			Map<Area, ColorModel> areasWithColors = responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(currentAnswers);
			interactionView.setColors(areasWithColors);
		}
	}

	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}
}
