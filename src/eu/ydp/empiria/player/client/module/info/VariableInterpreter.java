package eu.ydp.empiria.player.client.module.info;

import com.google.inject.Inject;

public class VariableInterpreter {

	@Inject private ContentFieldRegistry fieldRegistry;

	public String replaceAllTags(String content, int refItemIndex) {
		return replaceItemTags(content, refItemIndex);
	}

	private String replaceItemTags(String contentWithTags, int refItemIndex) {
		String content = contentWithTags;

		for (ContentFieldInfo info : fieldRegistry.getFieldInfos()) {
			content = replaceTag(info, content, refItemIndex);
		}

		return content;
	}

	private String replaceTag(ContentFieldInfo info, String contentWithTags, int refItemIndex) {
		String content = contentWithTags;

		if (content.contains(info.getTag())) {
			content = content.replaceAll(info.getPattern(), info.getValue(refItemIndex));
		}

		return content;
	}
}
