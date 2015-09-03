package eu.ydp.empiria.player.client.module.button;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeClient;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.core.base.ControlModule;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.base.ISimpleModule;
import eu.ydp.empiria.player.client.module.core.flow.IGroup;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class AbstractActivityButtonModule extends ControlModule implements ISimpleModule, WorkModeClient {

    @Inject
    private CustomPushButton button;
    @Inject
    private ModeStyleNameConstants styleNameConstants;
    private boolean isEnabled = true;
    private final List<String> styles = new ArrayList<>();

    protected abstract void invokeRequest();

    protected abstract String getStyleName();

    @Override
    public void initModule(Element element) {// NOPMD
        updateStyleName();
        button.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (isEnabled) {
                    invokeRequest();
                }
            }
        });
    }

    @Override
    public Widget getView() {
        return button;
    }

    protected GroupIdentifier getCurrentGroupIdentifier() {
        return getModuleSocket().getParentGroupIdentifier(this);
    }

    protected boolean currentGroupIsConcerned(GroupIdentifier groupId) {
        Stack<HasChildren> parentsHierarchy = getModuleSocket().getParentsHierarchy(this);
        for (IModule currModule : parentsHierarchy) {
            if (currModule instanceof IGroup) {
                if (((IGroup) currModule).getGroupIdentifier()
                        .equals(groupId)) {// NOPMD
                    return true; // NOPMD
                }
            }
        }
        return false;
    }

    protected void updateStyleName() {
        final String currentStyleName = getCurrentStyleName();
        button.setStyleName(currentStyleName);
        for (String style : styles) {
            button.addStyleName(style);
        }
    }

    private String getCurrentStyleName() {
        String styleName = getStyleName();

        if (!isEnabled) {
            styleName += "-disabled"; // NOPMD
        }

        return styleName;
    }

    @Override
    public void enablePreviewMode() {
        isEnabled = false;
        styles.add(styleNameConstants.QP_MODULE_MODE_PREVIEW());
        updateStyleName();
    }

    @Override
    public void enableTestSubmittedMode() {
        isEnabled = false;
        styles.add(styleNameConstants.QP_MODULE_MODE_TEST_SUBMITTED());
        updateStyleName();
    }

    @Override
    public void disableTestSubmittedMode() {
        isEnabled = true;
        styles.remove(styleNameConstants.QP_MODULE_MODE_TEST_SUBMITTED());
        updateStyleName();
    }

    @Override
    public void enableTestMode() {
        isEnabled = false;
        styles.add(styleNameConstants.QP_MODULE_MODE_TEST());
        updateStyleName();
    }

    @Override
    public void disableTestMode() {
        isEnabled = true;
        styles.remove(styleNameConstants.QP_MODULE_MODE_TEST());
        updateStyleName();
    }
}
