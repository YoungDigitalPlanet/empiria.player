package eu.ydp.empiria.player.client.module.accordion.presenter;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.accordion.AccordionContentGenerator;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import eu.ydp.empiria.player.client.module.accordion.controller.AccordionSectionsController;
import eu.ydp.empiria.player.client.module.accordion.controller.AccordionSectionsControllerProvider;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionBean;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionSectionBean;
import eu.ydp.empiria.player.client.module.accordion.view.AccordionView;
import eu.ydp.empiria.player.client.module.accordion.view.section.AccordionSectionView;
import eu.ydp.empiria.player.client.module.accordion.view.section.AccordionSectionViewFactory;
import eu.ydp.gwtutil.client.event.factory.Command;

public class AccordionPresenter implements IsWidget {

    private final AccordionView view;
    private AccordionSectionViewFactory viewFactory;
    private AccordionSectionsControllerProvider controllerProvider;

    @Inject
    public AccordionPresenter(AccordionView view, AccordionSectionViewFactory viewFactory, AccordionSectionsControllerProvider controllerProvider) {
        this.view = view;
        this.viewFactory = viewFactory;
        this.controllerProvider = controllerProvider;
    }

    public void initialize(AccordionBean bean, AccordionContentGenerator generator) {
        Transition transition = bean.getTransition();
        AccordionSectionsController controller = controllerProvider.getController(transition);

        for (AccordionSectionBean accordionSectionBean : bean.getSections()) {
            AccordionSectionView sectionView = viewFactory.createView(accordionSectionBean, generator);
            sectionView.init(transition);
            sectionView.addClickEvent(createClickEvent(controller, sectionView));
            view.addSection(sectionView);
        }
    }

    private Command createClickEvent(final AccordionSectionsController controller, final AccordionSectionView sectionView) {
        return new Command() {
            @Override
            public void execute(NativeEvent event) {
                controller.onClick(sectionView);
            }
        };
    }

    @Override
    public Widget asWidget() {
        return view.asWidget();
    }
}
