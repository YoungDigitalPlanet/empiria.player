package eu.ydp.empiria.player.client.module.colorfill.presenter;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.colorfill.model.ColorModel;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;

public class ResponseAnswerByViewBuilder {

	private final ColorfillInteractionView interactionView;
	private final ResponseUserAnswersConverter responseUserAnswersConverter;

	@Inject
	public ResponseAnswerByViewBuilder(
			@ModuleScoped ColorfillInteractionView interactionView,
			ResponseUserAnswersConverter responseUserAnswersConverter) {
				this.interactionView = interactionView;
				this.responseUserAnswersConverter = responseUserAnswersConverter;
	}

	public List<String> buildNewResponseAnswersByCurrentImage(List<Area> areas) {
		List<String> userAnswers = Lists.newArrayList();
		for(Area area : areas){
			ColorModel color = interactionView.getColor(area);
			if(!color.isTransparent()){
				String userAnswer = responseUserAnswersConverter.buildResponseAnswerFromAreaAndColor(area, color);
				userAnswers.add(userAnswer);
			}
		}
		return userAnswers;
	}
	
}
