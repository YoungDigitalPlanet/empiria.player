package eu.ydp.empiria.player.client.module.draggap.standard.structure;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.draggap.structure.DragGapBaseStructure;

public class DragGapStructure extends DragGapBaseStructure<DragGapBean, DragGapModuleJAXBParserFactory> {

    @Inject
    DragGapModuleJAXBParserFactory factory;

    @Override
    protected DragGapModuleJAXBParserFactory getParserFactory() {
        return factory;
    }
}
