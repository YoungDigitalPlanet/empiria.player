package eu.ydp.empiria.player.client.module.accordion.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.accordion.AccordionContentGenerator;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionSectionBean;

public class AccordionSectionFactory {
    @Inject
    private Provider<AccordionSectionPresenter> sectionProvider;

    public AccordionSectionPresenter createSection(AccordionSectionBean bean, AccordionContentGenerator generator) {
        AccordionSectionPresenter presenter = sectionProvider.get();

        Element title = bean.getTitle().getValue();
        Widget widget = generator.generateInlineBody(title);
        presenter.setTitle(widget);

        Element content = bean.getContent().getValue();
        generator.generateBody(content, presenter.getContentContainer());

        return presenter;
    }
}
