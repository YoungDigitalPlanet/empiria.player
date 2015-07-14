package eu.ydp.empiria.player.client.controller.multiview;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.client.scheduler.Scheduler;

import java.util.List;

import static com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class PanelAttacher {

    @Inject
    private Scheduler scheduler;
    @Inject
    private PanelCache panelsCache;

    public void attach(MultiPageController multiPageController, List<Integer> pagesToAttache) {
        for (Integer pageIndex : pagesToAttache) {
            final KeyValue<FlowPanel, FlowPanel> pair = panelsCache.getOrCreateAndPut(pageIndex);
            scheduleDeferredAttachToParent(multiPageController, pair, pageIndex);
        }
    }

    private void scheduleDeferredAttachToParent(final MultiPageController multiPageController, final KeyValue<FlowPanel, FlowPanel> pair,
                                                final int pageNumber) {
        scheduler.scheduleDeferred(new ScheduledCommand() {
            @Override
            public void execute() {
                FlowPanel placeHolderPanel = pair.getKey();
                FlowPanel pageContentPanel = pair.getValue();
                placeHolderPanel.add(pageContentPanel);
                if (multiPageController.isCurrentPage(pageNumber)) {
                    multiPageController.setCurrentPageHeight();
                }
            }
        });
    }
}
