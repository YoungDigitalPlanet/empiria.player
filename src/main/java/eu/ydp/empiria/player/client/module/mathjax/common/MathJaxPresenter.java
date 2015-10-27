package eu.ydp.empiria.player.client.module.mathjax.common;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class MathJaxPresenter {

    private final MathJaxView view;
    private final MathJaxNative mathJaxNative;

    @Inject
    public MathJaxPresenter(@Assisted MathJaxView view, MathJaxNative mathJaxNative) {
        this.view = view;
        this.mathJaxNative = mathJaxNative;
    }

    public Widget getView() {
        return view.asWidget();
    }

    public void setMmlScript(String script) {
        view.setMmlScript(script);
        Element scriptElement = view.asWidget().getElement().getFirstChildElement();
        mathJaxNative.addElementToRender(scriptElement);
    }

    public void rerenderMathElement(String moduleId){
        mathJaxNative.rerenderMathElement(moduleId);
    }

    public void typesetMathElement(){
        mathJaxNative.typesetMathElement();
    }
}
