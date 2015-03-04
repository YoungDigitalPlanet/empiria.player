package eu.ydp.empiria.player.client.module;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

public abstract class ModuleBase extends ParentedModuleBase {

	private String moduleId;
	private String moduleClass;

	@Override
	protected final void initModule(ModuleSocket moduleSocket) {
		super.initModule(moduleSocket);
	}

	protected final void readAttributes(Element element) {

		String className = element.getAttribute("class");
		if (className != null && !"".equals(className)) {
			moduleClass = className;
		} else {
			moduleClass = "";
		}
		String id = element.getAttribute("id");
		if (id != null && !"".equals(id)) {
			moduleId = id;
		} else {
			moduleId = "";
		}
	}

	protected String getModuleId() {
		return moduleId;
	}

	protected String getModuleClass() {
		return moduleClass;
	}

	protected final void applyIdAndClassToView(Widget widget) {
		if (widget != null) {
			if (getModuleId() != null && !"".equals(getModuleId().trim()))
				widget.getElement().setId(getModuleId());
			if (getModuleClass() != null && !"".equals(getModuleClass().trim()))
				widget.addStyleName(getModuleClass());
		}
	}
}
