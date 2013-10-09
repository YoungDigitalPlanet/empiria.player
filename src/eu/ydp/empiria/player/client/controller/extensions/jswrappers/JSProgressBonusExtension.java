package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressBonusConfigJs;

public class JSProgressBonusExtension extends AbstractJsExtension implements ProgressBonusExtension {

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_PROGRESS_BONUS;
	}

	@Override
	public void init() {
	}

	@Override
	public ProgressBonusConfig getProgressBonusConfig() {
		JavaScriptObject configJso = getConfigNative(extensionJsObject);
		ProgressBonusConfigJs configJs = configJso.cast();
		return ProgressBonusConfig.fromJs(configJs);
	}

	@Override
	public String getProgressBonusId() {
		return getProgressBonusNative(extensionJsObject);
	}

	private native String getProgressBonusNative(JavaScriptObject extensionJsObject)/*-{
		return extensionJsObject.getProgressBonusId();
	}-*/;

	private native JavaScriptObject getConfigNative(JavaScriptObject extensionJsObject)/*-{
		return extensionJsObject.getProgressBonusConfig();
	}-*/;
}
