package eu.ydp.empiria.player.client.module.ordering.view.items;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.fest.assertions.api.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;


@SuppressWarnings("PMD")
@RunWith(JUnitParamsRunner.class)
public class ViewItemsSorterJUnitTest {

	Map<String, Widget> itemsToSort = new ImmutableMap.Builder<String, Widget>().
											put("1", mock(Widget.class)).
											put("2", mock(Widget.class)).
										    put("3", mock(Widget.class)).
										    build();


	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}



	@Test
	@Parameters({ "1,2,3", "2,1,3", "3,2,1", "2,2,1" })
	public void getItemsInOrder(String id1, String id2, String id3) throws Exception {
		ViewItemsSorter sorter = new ViewItemsSorter();
		ArrayList<String> itemsOrder = Lists.newArrayList(id1, id2, id3);
		List<IsWidget> itemsInOrder = sorter.getItemsInOrder(itemsOrder, itemsToSort);
		for (int x = 0; x < itemsOrder.size(); ++x) {
			Assertions.assertThat(itemsInOrder.get(x)).isEqualTo(itemsToSort.get(itemsOrder.get(x)));
		}
	}

}
