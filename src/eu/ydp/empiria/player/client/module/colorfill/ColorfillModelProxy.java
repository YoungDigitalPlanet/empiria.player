package eu.ydp.empiria.player.client.module.colorfill;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseAnswerByViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseUserAnswersConverter;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillBeanProxy;

public class ColorfillModelProxy {
	
	@Inject @ModuleScoped
	private ColorfillInteractionModuleModel model;
	
	@Inject @ModuleScoped
	private ResponseAnswerByViewBuilder responseAnswerByViewBuilder;
	
	@Inject
	private ResponseUserAnswersConverter responseUserAnswersConverter;
	
	@Inject @ModuleScoped
	private ColorfillBeanProxy beanProxy;

	public void updateUserAnswers() {
		List<String> userAnswers = responseAnswerByViewBuilder.buildNewResponseAnswersByCurrentImage(beanProxy.getAreas());
		model.setNewUserAnswers(userAnswers);
	}
	
	public Map<Area, ColorModel> getUserAnswers(){
		List<String> currentAnswers = model.getCurrentAnswers();
		Map<Area, ColorModel> areasWithColors = responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(currentAnswers);
		return areasWithColors;
	}
}
