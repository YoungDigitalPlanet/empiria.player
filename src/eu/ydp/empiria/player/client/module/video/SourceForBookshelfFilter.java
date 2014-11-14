package eu.ydp.empiria.player.client.module.video;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public class SourceForBookshelfFilter {
	
	public List<String> getFilteredSources(List<String> sources){
		return FluentIterable.from(sources).filter(getPredicate()).toList();
	}
	
	private Predicate<String> getPredicate() {
		return new Predicate<String>() {

			@Override
			public boolean apply(String input) {
				return input.contains(".mp4");
			}
		};
	}

}
