package eu.ydp.empiria.player.client.module.ordering.presenter;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.module.ordering.model.ItemClickAction;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;

@SuppressWarnings("PMD")
public class ItemClickControllerJUnitTest {

	private ItemClickController itemClickController;
	private OrderingItemsDao orderingItemsDao;
	private OrderingItem firstItem;
	private OrderingItem secondItem;

	@Before
	public void setUp() throws Exception {
		itemClickController = new ItemClickController();
		orderingItemsDao = new OrderingItemsDao();
		itemClickController.initialize(orderingItemsDao);

		addInitialItems();
	}

	private void addInitialItems() {
		firstItem = new OrderingItem("1", "value1");
		secondItem = new OrderingItem("2", "value2");
		orderingItemsDao.addItem(firstItem);
		orderingItemsDao.addItem(secondItem);
	}

	@Test
	public void shouldSelectClickedItem() throws Exception {
		ItemClickAction itemClickAction = itemClickController.itemClicked("2");

		assertEquals(ItemClickAction.SELECT, itemClickAction);
		assertEquals(true, secondItem.isSelected());
	}

	@Test
	public void shouldUnselectClickedItemWhenWasAlreadySelected() throws Exception {
		//given
		orderingItemsDao.setLastClickedItem(secondItem);
		secondItem.setSelected(true);

		//when
		ItemClickAction itemClickAction = itemClickController.itemClicked("2");

		//then
		assertEquals(ItemClickAction.UNSELECT, itemClickAction);
		assertEquals(false, secondItem.isSelected());
	}

	@Test
	public void shouldSwitchItemsInCurrentOrder() throws Exception {
		//given
		orderingItemsDao.setItemsOrder(Lists.newArrayList("2", "1"));
		orderingItemsDao.setLastClickedItem(secondItem);
		secondItem.setSelected(true);

		//when
		ItemClickAction itemClickAction = itemClickController.itemClicked("1");

		//then
		assertEquals(ItemClickAction.SWITCH, itemClickAction);
		assertEquals(false, secondItem.isSelected());
		assertEquals(false, firstItem.isSelected());

		List<String> expectedItemsOrder = Lists.newArrayList("1", "2");
		assertEquals(expectedItemsOrder, orderingItemsDao.getItemsOrder());
	}

	@Test
	public void shouldDoNothingWhenItemIsLocked() throws Exception {
		//given
		firstItem.setLocked(true);
		//when
		ItemClickAction itemClickAction = itemClickController.itemClicked("1");

		//then
		assertEquals(ItemClickAction.LOCK, itemClickAction);
	}

}
