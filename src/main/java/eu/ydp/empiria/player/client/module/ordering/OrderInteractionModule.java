package eu.ydp.empiria.player.client.module.ordering;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionStructure;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class OrderInteractionModule extends AbstractInteractionModule<OrderInteractionModuleModel, OrderInteractionBean> {

    @Inject
    @ModuleScoped
    private OrderInteractionPresenter presenter;

    @Inject
    private OrderInteractionStructure orderInteractionStructure;

    @Inject
    @ModuleScoped
    private OrderInteractionModuleModel moduleModel;

    @Inject
    @ModuleScoped
    private DragController dragController;

    @Override
    public Widget getView() {
        return presenter.asWidget();
    }

    @Override
    protected void initalizeModule() {
        moduleModel.initialize(this);
    }

    @Override
    protected OrderInteractionModuleModel getResponseModel() {
        return moduleModel;
    }

    @Override
    protected ActivityPresenter<OrderInteractionModuleModel, OrderInteractionBean> getPresenter() {
        return presenter;
    }

    @Override
    protected AbstractModuleStructure<OrderInteractionBean, ? extends JAXBParserFactory<OrderInteractionBean>> getStructure() {
        return orderInteractionStructure;
    }

    @Override
    public void onBodyLoad() {
        super.onBodyLoad();
        dragController.init(presenter.getOrientation());
        presenter.setLocked(locked);
    }
}
