package eu.ydp.empiria.player.client.module.ordering.view;

public class OrderInteractionViewUniqueCssProvider {

	private static final String QP_ORDERED_UNIQUE = "qp-ordered-unique-";
	private int counter;

	public String getNext() {
		++counter;

		return QP_ORDERED_UNIQUE + String.valueOf(counter);
	}

}
