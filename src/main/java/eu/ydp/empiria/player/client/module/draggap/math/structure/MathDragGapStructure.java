package eu.ydp.empiria.player.client.module.draggap.math.structure;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBaseStructure;

public class MathDragGapStructure extends DragGapBaseStructure<MathDragGapBean, MathDragGapModuleJAXBParserFactory> {

    @Inject
    MathDragGapModuleJAXBParserFactory factory;

    @Override
    protected MathDragGapModuleJAXBParserFactory getParserFactory() {
        return factory;
    }
}
