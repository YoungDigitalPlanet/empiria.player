package eu.ydp.empiria.player.client.module.colorfill;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.color.ColorModel;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseAnswerByViewBuilder;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ResponseUserAnswersConverter;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.structure.ColorfillBeanProxy;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ColorfillModelProxy {

	@Inject @ModuleScoped
	private ColorfillInteractionModuleModel model;

	@Inject @ModuleScoped
	private ResponseAnswerByViewBuilder responseAnswerByViewBuilder;

	@Inject
	private ResponseUserAnswersConverter responseUserAnswersConverter;

	@Inject @ModuleScoped
	private ColorfillBeanProxy beanProxy;

	@Inject @PageScoped
	AnswerEvaluationSupplier answerEvaluationSupplier;

	@Inject @ModuleScoped
	Response response;

	public void updateUserAnswers() {
		List<Area> areas = beanProxy.getAreas();
		areas.addAll(beanProxy.getFakeAreas());
		
		List<String> userAnswers = responseAnswerByViewBuilder.buildNewResponseAnswersByCurrentImage(areas);
		model.setNewUserAnswers(userAnswers);

	}

	public Map<Area, ColorModel> getUserAnswers() {
		List<String> currentAnswers = model.getCurrentAnswers();
		Map<Area, ColorModel> areasWithColors = responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(currentAnswers);
		return areasWithColors;
	}

	public List<Area> getUserWrongAnswers() {
		return getUserAnswers(false);
	}

	public List<Area> getUserCorrectAnswers() {
		return getUserAnswers(true);
	}

	private List<Area> getUserAnswers(boolean answerEvaluationValue) {
		List<Boolean> evaluateAnswer = answerEvaluationSupplier.evaluateAnswer(response);
		List<String> currentAnswers = model.getCurrentAnswers();
		List<String> userAnswers = filerResponses(evaluateAnswer, currentAnswers, answerEvaluationValue);
		return responseUserAnswersConverter.convertResponseAnswersToAreaList(userAnswers);
	}

	private List<String> filerResponses(List<Boolean> evaluateAnswer, List<String> currentAnswers, boolean evaluationValue) {
		List<String> answers = Lists.newArrayList();
		for (int x = 0; x < evaluateAnswer.size(); ++x) {
			if (evaluateAnswer.get(x) == evaluationValue) {
				answers.add(currentAnswers.get(x));
			}
		}
		return answers;
	}
}
