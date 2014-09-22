package eu.ydp.empiria.player.client.module.button;

import java.util.Stack;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ControlModule;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IGroup;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.module.workmode.WorkModePreviewClient;
import eu.ydp.empiria.player.client.module.workmode.WorkModeTestSubmittedClient;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public abstract class AbstractActivityButtonModule extends ControlModule implements ISimpleModule, WorkModePreviewClient, WorkModeTestSubmittedClient {

	@Inject
	private CustomPushButton button;
	@Inject
	private StyleNameConstants styleNameConstants;
	private boolean isEnabled = true;
	private boolean isPreviewMode = false;

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
				if (((IGroup) currModule).getGroupIdentifier().equals(groupId)) {// NOPMD
					return true; // NOPMD
				}
			}
		}
		return false;
	}

	protected abstract void invokeRequest();

	protected void updateStyleName() {
		final String currentStyleName = getCurrentStyleName();
		button.setStyleName(currentStyleName);
		if (isPreviewMode) {
			final String qp_MODULE_MODE_PREVIEW = styleNameConstants.QP_MODULE_MODE_PREVIEW();
			button.addStyleName(qp_MODULE_MODE_PREVIEW);
		}
	}

	private String getCurrentStyleName() {
		String styleName = getStyleName();

		if (!isEnabled) {
			styleName += "-disabled"; // NOPMD
		}

		return styleName;
	}

	protected abstract String getStyleName();

	@Override
	public void enablePreviewMode() {
		isEnabled = false;
		isPreviewMode = true;
		updateStyleName();
	}

	@Override
	public void enableTestSubmittedMode() {
		isEnabled = false;
		button.addStyleName(styleNameConstants.QP_MODULE_MODE_TEST_SUBMITTED());
	}

	@Override
	public void disableTestSubmittedMode() {
		isEnabled = true;
		button.removeStyleName(styleNameConstants.QP_MODULE_MODE_TEST_SUBMITTED());
	}
}
