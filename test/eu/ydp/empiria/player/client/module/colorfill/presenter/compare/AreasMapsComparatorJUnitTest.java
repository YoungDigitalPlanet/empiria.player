package eu.ydp.empiria.player.client.module.colorfill.presenter.compare;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;


public class AreasMapsComparatorJUnitTest {

	private AreasMapsComparator comparator = new AreasMapsComparator();
	
	@Test
	public void findDifference_added(){
		// given
		final Area addedArea = new Area(11, 12);
		Map<Area, ColorModel> previous = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3));
		Map<Area, ColorModel> current = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3), addedArea, ColorModel.createFromRgba(10, 11, 12, 13));
				
		// when
		AreasMapComparationResult result = comparator.findDifference(previous, current);
		
		// then
		assertThat(result.isDifference()).isTrue();
		assertThat(result.isAddedOrChanged()).isTrue();
		assertThat(result.getArea()).isEqualTo(addedArea);
	}
	
	@Test
	public void findDifference_changed(){
		// given
		final Area changedArea = new Area(11, 12);
		Map<Area, ColorModel> previous = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3), changedArea, ColorModel.createFromRgba(10, 11, 12, 13));
		Map<Area, ColorModel> current = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3), changedArea, ColorModel.createFromRgba(100, 101, 102, 103));
				
		// when
		AreasMapComparationResult result = comparator.findDifference(previous, current);
		
		// then
		assertThat(result.isDifference()).isTrue();
		assertThat(result.isAddedOrChanged()).isTrue();
		assertThat(result.getArea()).isEqualTo(changedArea);
	}
	
	@Test
	public void findDifference_same(){
		// given
		Map<Area, ColorModel> oneMap = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3), new Area(11, 12), ColorModel.createFromRgba(10, 11, 12, 13));
				
		// when
		AreasMapComparationResult result = comparator.findDifference(oneMap, oneMap);
		
		// then
		assertThat(result.isDifference()).isFalse();
	}
	
	@Test
	public void findDifference_removed(){
		// given
		final Area removedArea = new Area(11, 12);
		Map<Area, ColorModel> previous = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3), removedArea, ColorModel.createFromRgba(10, 11, 12, 13));
		Map<Area, ColorModel> current = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3));
				
		// when
		AreasMapComparationResult result = comparator.findDifference(previous, current);
		
		// then
		assertThat(result.isDifference()).isTrue();
		assertThat(result.isAddedOrChanged()).isFalse();
		assertThat(result.getArea()).isEqualTo(removedArea);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testArguments(){
		// given
		Map<Area, ColorModel> previous = Collections.EMPTY_MAP;
		Map<Area, ColorModel> current = ImmutableMap.of(new Area(1, 2), ColorModel.createFromRgba(0, 1, 2, 3), new Area(11, 12), ColorModel.createFromRgba(10, 11, 12, 13));
				
		// when
		comparator.findDifference(previous, current);
	}
}
