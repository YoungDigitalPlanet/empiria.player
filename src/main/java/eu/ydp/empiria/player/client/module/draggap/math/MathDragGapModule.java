package eu.ydp.empiria.player.client.module.draggap.math;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.draggap.DragGapBaseModule;
import eu.ydp.empiria.player.client.module.draggap.math.structure.MathDragGapBean;
import eu.ydp.empiria.player.client.module.draggap.math.structure.MathDragGapModuleJAXBParserFactory;
import eu.ydp.empiria.player.client.module.mathjax.interaction.InteractionMathJaxModule;
import eu.ydp.empiria.player.client.module.mathjax.interaction.MathJaxGapContainer;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public class MathDragGapModule extends DragGapBaseModule<MathDragGapBean, MathDragGapModuleJAXBParserFactory> {
    @Inject
    private MathJaxGapContainer mathJaxGapContainer;


    @Override
    protected void initalizeModule() {
        super.initalizeModule();
        mathJaxGapContainer.addMathGap(getPresenter().asWidget(), getIdentifier());
    }

    @Override
    public void setSize(HasDimensions size) {
        super.setSize(size);
        InteractionMathJaxModule parentModule = (InteractionMathJaxModule) getParentModule();
        parentModule.setDirty(true);
    }
}
