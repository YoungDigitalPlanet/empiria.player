package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import eu.ydp.empiria.player.client.module.connection.item.ConnectionItem;
import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;

public class SurfacesOffsetsExtractor {
	public Collection<Integer> extractLeftOffsets(ConnectionItems items) {
		return Collections2.transform(items.getAllItems(), new Function<ConnectionItem, Integer>() {

			@Override
			@Nullable
			public Integer apply(@Nullable ConnectionItem ci) {
				return ci.getOffsetLeft();
			}
		});
	}

	public Collection<Integer> extractTopOffsets(ConnectionItems items) {
		return Collections2.transform(items.getAllItems(), new Function<ConnectionItem, Integer>() {

			@Override
			@Nullable
			public Integer apply(@Nullable ConnectionItem ci) {
				return ci.getOffsetTop();
			}
		});
	}

	public Collection<Integer> extractBottomOffsets(ConnectionItems items) {
		return Collections2.transform(items.getAllItems(), new Function<ConnectionItem, Integer>() {

			@Override
			@Nullable
			public Integer apply(@Nullable ConnectionItem ci) {
				return ci.getOffsetTop() + ci.getHeight();
			}
		});
	}

	public Collection<Integer> extractRightOffsets(ConnectionItems items) {
		return Collections2.transform(items.getAllItems(), new Function<ConnectionItem, Integer>() {

			@Override
			@Nullable
			public Integer apply(@Nullable ConnectionItem ci) {
				return ci.getOffsetLeft() + ci.getWidth();
			}
		});
	}
}