package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

public abstract class SingleViewModuleBase extends ModuleBase implements ISingleViewModule {

	private ModuleSocket moduleSocket;

	protected final void initModuleInternal(Element element, ModuleSocket ms) {
		moduleSocket = ms;
		readAttributes(element);
		initModule(element);
		applyIdAndClassToView(getView());
	}

	protected abstract void initModule(Element element);

	protected ModuleSocket getModuleSocket(){
		return moduleSocket;
	}
	@Override
	public HasParent getParentModule() {
		return moduleSocket.getParent(this);
	}
}
