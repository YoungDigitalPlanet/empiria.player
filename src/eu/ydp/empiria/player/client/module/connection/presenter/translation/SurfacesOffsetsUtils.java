package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public class SurfacesOffsetsUtils {

	public int findMinOffsetLeft(ConnectionItems items){
		Collection<Integer> leftOffsets = extractLeftOffsets(items);
		int minLeftOffset = Collections.min(leftOffsets);
		return minLeftOffset;
	}

	public int findMaxOffsetLeft(ConnectionItems items){
		Collection<Integer> leftOffsets = extractLeftOffsets(items);
		int max = Collections.max(leftOffsets);
		return max;
	}
	
	private Collection<Integer> extractLeftOffsets(ConnectionItems items) {
		Collection<Integer> leftOffsets = Collections2.transform(items.getAllItems(), new Function<ConnectionItem, Integer>() {

			@Override
			@Nullable
			public Integer apply(@Nullable ConnectionItem arg0) {
				return arg0.getOffsetLeft();
			}
		});
		return leftOffsets;
	}
	
	public int findMinTopOffset(ConnectionItems items){
		Collection<Integer> topOffsets = extractTopOffsets(items);
		int minLeftOffset = Collections.min(topOffsets);
		return minLeftOffset;
	}

	public int findMaxTopOffset(ConnectionItems items){
		Collection<Integer> topOffsets = extractTopOffsets(items);
		int max = Collections.max(topOffsets);
		return max;
	}
	
	private Collection<Integer> extractTopOffsets(ConnectionItems items) {
		Collection<Integer> leftOffsets = Collections2.transform(items.getAllItems(), new Function<ConnectionItem, Integer>() {

			@Override
			@Nullable
			public Integer apply(@Nullable ConnectionItem arg0) {
				return arg0.getOffsetTop();
			}
		});
		return leftOffsets;
	}
}
