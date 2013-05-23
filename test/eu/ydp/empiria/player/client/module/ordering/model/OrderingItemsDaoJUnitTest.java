package eu.ydp.empiria.player.client.module.ordering.model;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.*;

import static org.junit.Assert.assertNull;


public class OrderingItemsDaoJUnitTest {

	private OrderingItemsDao orderingItemsDao;
	
	@Before
	public void setUp() throws Exception {
		orderingItemsDao = new OrderingItemsDao();
	}
	
	@Test
	public void shouldCreateInitialItemsOrder() throws Exception {
		addItem("item1");
		addItem("item2");
		
		assertNull(orderingItemsDao.getItemsOrder());
		
		orderingItemsDao.createInitialItemsOrder();
		
		List<String> itemsOrder = orderingItemsDao.getItemsOrder();
		assertThat(itemsOrder).containsOnly("item1", "item2");
	}

	private void addItem(String id) {
		OrderingItem orderingItem = new OrderingItem(id, "whatever");
		orderingItemsDao.addItem(orderingItem);
	}
}
