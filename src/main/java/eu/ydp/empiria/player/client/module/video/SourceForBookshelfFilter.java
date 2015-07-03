package eu.ydp.empiria.player.client.module.video;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import java.util.List;

public class SourceForBookshelfFilter {

    private static final Predicate<String> PREDICATE = new Predicate<String>() {

        @Override
        public boolean apply(String input) {
            return input.contains(".mp4");
        }
    };

    public List<String> getFilteredSources(List<String> sources) {
        return FluentIterable.from(sources).filter(PREDICATE).toList();
    }
}
