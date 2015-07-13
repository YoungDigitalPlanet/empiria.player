package eu.ydp.empiria.player.client.module.labelling;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.labelling.structure.ChildBean;
import eu.ydp.empiria.player.client.module.labelling.structure.LabellingInteractionBean;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingChildView;
import eu.ydp.empiria.player.client.module.labelling.view.LabellingView;

import java.util.List;

public class LabellingViewBuilder {

    @Inject
    private Provider<LabellingView> viewProvider;
    @Inject
    private Provider<LabellingChildView> childContainerProvider;

    public LabellingView buildView(LabellingInteractionBean structure, BodyGeneratorSocket bgs) {
        LabellingView view = viewProvider.get();
        view.setBackground(structure.getImg());
        view.setViewId(structure.getId());
        processChildren(structure, bgs, view);
        return view;
    }

    private void processChildren(LabellingInteractionBean structure, BodyGeneratorSocket bgs, LabellingView view) {
        List<ChildBean> children = fetchChildBeanList(structure);
        for (ChildBean child : children) {
            createAndAddChild(view, child, bgs);
        }
    }

    private List<ChildBean> fetchChildBeanList(LabellingInteractionBean structure) {
        if (structure.getChildren() == null) {
            return Lists.newArrayList();
        }
        return structure.getChildren().getChildBeanList();
    }

    private void createAndAddChild(LabellingView container, ChildBean child, BodyGeneratorSocket bgs) {
        LabellingChildView childContainer = createChild(child, bgs);
        container.addChild(childContainer.getView(), child.getX(), child.getY());

    }

    private LabellingChildView createChild(ChildBean child, BodyGeneratorSocket bgs) {
        LabellingChildView childContainer = childContainerProvider.get();
        bgs.generateBody(child.getContent().getValue(), childContainer.getContainer());
        return childContainer;
    }

}
