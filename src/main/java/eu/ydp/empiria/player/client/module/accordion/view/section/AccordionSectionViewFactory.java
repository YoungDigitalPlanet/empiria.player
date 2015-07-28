package eu.ydp.empiria.player.client.module.accordion.view.section;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.accordion.AccordionContentGenerator;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionSectionBean;

public class AccordionSectionViewFactory {
    @Inject
    private Provider<AccordionSectionView> provider;

    public AccordionSectionView createView(AccordionSectionBean bean, AccordionContentGenerator generator) {
        AccordionSectionView view = provider.get();

        Element title = bean.getTitle().getValue();
        Widget widget = generator.generateInlineBody(title);
        view.setTitle(widget);

        Element content = bean.getContent().getValue();
        generator.generateBody(content, view.getContentContainer());

        return view;
    }
}
