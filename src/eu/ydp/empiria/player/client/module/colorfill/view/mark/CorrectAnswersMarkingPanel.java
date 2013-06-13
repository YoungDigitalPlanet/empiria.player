package eu.ydp.empiria.player.client.module.colorfill.view.mark;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class CorrectAnswersMarkingPanel extends AnswersMarkingPanel {

	@Inject
	public CorrectAnswersMarkingPanel(StyleNameConstants styleNameConstants) {
		super(styleNameConstants.QP_COLORFILL_ANSWERS_MARKING_CORRECT());
	}

}
