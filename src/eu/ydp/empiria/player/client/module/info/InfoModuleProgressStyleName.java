package eu.ydp.empiria.player.client.module.info;

import com.google.inject.Inject;

import eu.ydp.gwtutil.client.NumberUtils;

public class InfoModuleProgressStyleName {
	private static final String TEST_RESULT_KEY = "$[test.result]";

	@Inject private InfoModuleProgressMapping infoModuleProgressMapping;
	@Inject private ContentFieldRegistry fieldRegistry;


	public String getCurrentStyleName(int refItemIndex){
		ContentFieldInfo fieldInfo = fieldRegistry.getFieldInfo(TEST_RESULT_KEY);
		if(fieldInfo!=null){
			 Integer percent = NumberUtils.tryParseInt(fieldInfo.getValue(refItemIndex),null);
			 return infoModuleProgressMapping.getStyleNameForProgress(percent);
		}
		return "";
	}
}
