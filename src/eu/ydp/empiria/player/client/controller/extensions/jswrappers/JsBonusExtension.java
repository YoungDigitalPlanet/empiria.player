package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusConfigJs;

public class JsBonusExtension extends AbstractJsExtension implements BonusExtension {

	private JavaScriptObject playerJsObject;
	
	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_BONUS;
	}

	@Override
	public void init() {
	}

	@Override
	public String getBonusId() {
		return getBonusIdNative();
	}

	private final native String getBonusIdNative()/*-{
		return extensionJsObject.getBonusId(); 
	}-*/;

	@Override
	public BonusConfig getBonusConfig() {
		BonusConfigJs bonusConfigJs = getBonusJsConfig();
		BonusConfig bonusConfig = BonusConfig.fromJs(bonusConfigJs);
		return bonusConfig;
	}

	private final native BonusConfigJs getBonusJsConfig()/*-{
		return extensionJsObject.getBonusConfig();
	}-*/;

}
