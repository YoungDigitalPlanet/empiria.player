package eu.ydp.empiria.player.client.module.drawing;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.IResetable;
import eu.ydp.empiria.player.client.module.SimpleModuleBase;

public class DrawingModule extends SimpleModuleBase implements IResetable {

	@Inject private DrawingPresenter presenter;

	@Override
	public void reset() {
		presenter.reset();
	}

	@Override
	public Widget getView() {
		return presenter.getView().asWidget();
	}

	@Override
	protected void initModule(Element element) {
	}

}
