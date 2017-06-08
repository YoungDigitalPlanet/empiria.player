/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
