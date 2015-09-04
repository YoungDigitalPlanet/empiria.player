package eu.ydp.empiria.player.client.module.draggap.view;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.draggap.DragGapStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.gwtutil.client.StringUtils;

public class DragGapStylesProvider {

    private final DragGapStyleNameConstants styleNameConstants;

    @Inject
    public DragGapStylesProvider(DragGapStyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
    }

    public String getCorrectGapStyleName(UserAnswerType type) {
        switch (type) {
            case CORRECT:
                return styleNameConstants.QP_DRAG_GAP_CORRECT();
            case WRONG:
                return styleNameConstants.QP_DRAG_GAP_WRONG();
            case DEFAULT:
                return styleNameConstants.QP_DRAG_GAP_DEFAULT();
            case NONE:
                return styleNameConstants.QP_DRAG_GAP_NONE();
            default:
                return StringUtils.EMPTY_STRING;
        }
    }
}
