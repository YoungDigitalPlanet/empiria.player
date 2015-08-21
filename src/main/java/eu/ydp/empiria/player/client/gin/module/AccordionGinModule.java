package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import eu.ydp.empiria.player.client.module.accordion.view.AccordionView;
import eu.ydp.empiria.player.client.module.accordion.view.AccordionViewImpl;
import eu.ydp.empiria.player.client.module.accordion.view.section.AccordionSectionView;
import eu.ydp.empiria.player.client.module.accordion.view.section.AccordionSectionViewImpl;

public class AccordionGinModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(AccordionView.class).to(AccordionViewImpl.class);
        bind(AccordionSectionView.class).to(AccordionSectionViewImpl.class);
    }
}
