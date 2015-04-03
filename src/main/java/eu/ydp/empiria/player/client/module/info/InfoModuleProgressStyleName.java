package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Optional;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.NumberUtils;

public class InfoModuleProgressStyleName {
	private static final String ITEM_RESULT_KEY = "$[item.result]";

	@Inject
	private InfoModuleProgressMapping infoModuleProgressMapping;
	@Inject
	private ContentFieldRegistry fieldRegistry;

	public String getCurrentStyleName(int refItemIndex) {
		Optional<ContentFieldInfo> fieldInfo = fieldRegistry.getFieldInfo(ITEM_RESULT_KEY);
		if (fieldInfo.isPresent()) {
			Integer percent = NumberUtils.tryParseInt(fieldInfo.get().getValue(refItemIndex), null);
			return infoModuleProgressMapping.getStyleNameForProgress(percent);
		}
		return "";
	}
}