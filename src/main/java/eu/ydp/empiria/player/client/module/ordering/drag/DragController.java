package eu.ydp.empiria.player.client.module.ordering.drag;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class DragController {

    @Inject
    @ModuleScoped
    private OrderInteractionView interactionView;
    @Inject
    private Sortable sortable;
    @Inject
    private SortCallback callback;

    public void init(final OrderInteractionOrientation orientation) {
        String id = getIdSelector();
        sortable.init(id, orientation, callback);
    }

    public void enableDrag() {
        String id = getIdSelector();
        sortable.enable(id);
    }

    public void disableDrag() {
        String id = getIdSelector();
        sortable.disable(id);
    }

    private String getIdSelector() {
        return "." + interactionView.getMainPanelUniqueCssClass();
    }
}
