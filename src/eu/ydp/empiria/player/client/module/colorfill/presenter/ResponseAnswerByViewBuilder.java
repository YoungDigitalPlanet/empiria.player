package eu.ydp.empiria.player.client.module.colorfill.presenter;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ResponseAnswerByViewBuilder {

	private final ResponseUserAnswersConverter responseUserAnswersConverter;
	private final ColorfillInteractionViewColors interactionViewColors;

	@Inject
	public ResponseAnswerByViewBuilder(
			@ModuleScoped ColorfillInteractionViewColors interactionViewColors,
			ResponseUserAnswersConverter responseUserAnswersConverter) {
			this.interactionViewColors = interactionViewColors;
			this.responseUserAnswersConverter = responseUserAnswersConverter;
	}

	public List<String> buildNewResponseAnswersByCurrentImage(List<Area> areas) {
		List<String> userAnswers = Lists.newArrayList();
		Map<Area, ColorModel> colors = interactionViewColors.getColors(areas);
		for(Map.Entry<Area, ColorModel> entry : colors.entrySet()){
			if(!entry.getValue().isTransparent()){
				String userAnswer = responseUserAnswersConverter.buildResponseAnswerFromAreaAndColor(entry.getKey(), entry.getValue());
				userAnswers.add(userAnswer);
			}
		}
		return userAnswers;
	}
	
}
