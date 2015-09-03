package eu.ydp.empiria.player.client.module.containers.group;

import com.google.gwt.dom.client.Document;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.core.flow.IGroup;
import eu.ydp.empiria.player.client.module.containers.BindingContainerModule;

public abstract class GroupModuleBase extends BindingContainerModule implements IGroup {

    protected GroupIdentifier groupIdentifier;
    private String moduleId;

    public GroupModuleBase() {
    }

    @Override
    protected String getModuleId() {
        if (super.getModuleId() == null || "".equals(super.getModuleId().trim())) {
            if (moduleId == null)
                moduleId = Document.get().createUniqueId();
            return moduleId;
        }
        return super.getModuleId();
    }

    @Override
    public void initModule(Element element) {
        super.initModule(element);
        groupIdentifier = new DefaultGroupIdentifier(getModuleId());
    }

    @Override
    public GroupIdentifier getGroupIdentifier() {
        return groupIdentifier;
    }

}
