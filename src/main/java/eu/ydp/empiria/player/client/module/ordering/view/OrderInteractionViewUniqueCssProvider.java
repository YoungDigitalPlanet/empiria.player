package eu.ydp.empiria.player.client.module.ordering.view;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class OrderInteractionViewUniqueCssProvider {

	@Inject
	private StyleNameConstants styleNameConstants;
	private int counter;

	public String getNext() {
		++counter;

		return styleNameConstants.QP_ORDERED_UNIQUE() + "-" + String.valueOf(counter);
	}

}
