package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.List;

public class ContentFieldInfoSearcher {

    public Optional<ContentFieldInfo> findByTagName(final String tagName, List<ContentFieldInfo> contentFieldInfos) {
        return Iterables.tryFind(contentFieldInfos, new Predicate<ContentFieldInfo>() {
            @Override
            public boolean apply(ContentFieldInfo fieldInfo) {
                String tag = fieldInfo.getTag();
                return tag.equals(tagName);
            }
        });
    }
}
