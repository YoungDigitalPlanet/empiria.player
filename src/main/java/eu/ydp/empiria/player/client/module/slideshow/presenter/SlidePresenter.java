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

package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.common.base.Strings;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideBean;
import eu.ydp.empiria.player.client.module.slideshow.structure.SourceBean;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SlidePresenter {

    private final SlideView view;
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    private MathJaxNative mathJaxNative;

    @Inject
    public SlidePresenter(@ModuleScoped SlideView view, MathJaxNative mathJaxNative) {
        this.view = view;
        this.mathJaxNative = mathJaxNative;
    }


    public void replaceViewData(SlideBean slide) {
        view.clearTitle();
        view.clearNarration();

        if (slide.hasSlideTitle()) {
            Element title = slide.getSlideTitle().getTitleValue().getValue();
            setTitle(title);
        }

        if (slide.hasNarration()) {
            Element narration = slide.getNarration().getNarrationValue().getValue();
            setNarration(narration);
        }

        SourceBean source = slide.getSource();
        setSource(source);
        mathJaxNative.renderMath();
    }

    private void setSource(SourceBean source) {
        String src = source.getSrc();
        if (!Strings.isNullOrEmpty(src)) {
            view.setImage(src);
        }
    }

    private void setTitle(Element title) {
        Widget titleView = getWidgetFromElement(title);
        view.setSlideTitle(titleView);
    }

    private void setNarration(Element narration) {
        Widget narrationView = getWidgetFromElement(narration);
        view.setNarration(narrationView);
    }

    private Widget getWidgetFromElement(Element element) {
        return inlineBodyGeneratorSocket.generateInlineBody(element);
    }

    public void setInlineBodyGenerator(InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
    }
}
