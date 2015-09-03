package eu.ydp.empiria.player.client.controller;

import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IInlineContainerModule;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;

import java.util.HashSet;
import java.util.Set;

public class InlineContainerStylesExtractor {

    public Set<InlineFormattingContainerType> getInlineStyles(IModule module) {
        Set<InlineFormattingContainerType> inlineStyles = null;

        if (module != null) {
            inlineStyles = extractInlineStylesFromParentHierarchy(module);
        } else {
            inlineStyles = new HashSet<InlineFormattingContainerType>();
        }

        return inlineStyles;
    }

    private Set<InlineFormattingContainerType> extractInlineStylesFromParentHierarchy(IModule module) {
        Set<InlineFormattingContainerType> inlineStyles = new HashSet<InlineFormattingContainerType>();

        HasChildren currParent = module.getParentModule();
        while (currParent != null) {
            if (currParent instanceof IInlineContainerModule) {
                IInlineContainerModule inlineContainerModule = (IInlineContainerModule) currParent;
                inlineStyles.add(inlineContainerModule.getType());
            }
            currParent = currParent.getParentModule();
        }

        return inlineStyles;
    }

}
