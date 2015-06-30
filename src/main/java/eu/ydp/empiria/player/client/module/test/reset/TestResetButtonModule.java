package eu.ydp.empiria.player.client.module.test.reset;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.workmode.WorkModePreviewClient;
import eu.ydp.empiria.player.client.module.ILockable;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TestResetButtonModule extends SimpleModuleBase implements ILockable, WorkModePreviewClient {

    private final TestResetButtonPresenter presenter;

    @Inject
    public TestResetButtonModule(@ModuleScoped TestResetButtonPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    protected void initModule(Element element) {
    }

    @Override
    public void lock(boolean shouldLock) {
        if (shouldLock) {
            presenter.lock();
        } else {
            presenter.unlock();
        }
    }

    @Override
    public void enablePreviewMode() {
        presenter.enablePreviewMode();
    }
}
