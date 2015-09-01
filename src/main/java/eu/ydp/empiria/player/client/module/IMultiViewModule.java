package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;

import java.util.List;

public interface IMultiViewModule extends IModule {
    void initModule(ModuleSocket moduleSocket);

    void addElement(Element element);

    void installViews(List<HasWidgets> placeholders);
}
