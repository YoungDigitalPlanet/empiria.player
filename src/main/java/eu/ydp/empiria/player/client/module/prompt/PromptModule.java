package eu.ydp.empiria.player.client.module.prompt;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class PromptModule extends SimpleModuleBase implements Factory<PromptModule> {

    protected Widget contents;

    @Override
    public void initModule(Element element) {
        contents = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(element);
        contents.setStyleName("qp-prompt");
    }

    @Override
    public Widget getView() {
        return contents;
    }

    @Override
    public PromptModule getNewInstance() {
        return new PromptModule();
    }

}
