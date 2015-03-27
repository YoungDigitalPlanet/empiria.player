package eu.ydp.empiria.player.client.module.info;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class ContentFieldRegistry {

	@Inject
	private ContentFieldInfoListProvider contentFieldInfoListProvider;
	@Inject
	private ContentFieldInfoSearcher contentFieldInfoSearcher;
	private final List<ContentFieldInfo> fieldInfos = Lists.newArrayList();

	public Optional<ContentFieldInfo> getFieldInfo(final String fieldName) {
		registerIfRequired();
		return contentFieldInfoSearcher.findByTagName(fieldName, fieldInfos);
	}

	private void register() {
		fieldInfos.addAll(contentFieldInfoListProvider.get());
	}

	private boolean isRegistered() {
		return !fieldInfos.isEmpty();
	}

	private void registerIfRequired() {
		if (!isRegistered()) {
			register();
		}
	}

}
