package eu.ydp.empiria.player.client.module.ordering.view;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.module.ordering.OrderingStyleNameConstants;

@Singleton
public class OrderInteractionViewUniqueCssProvider {

    @Inject
    private OrderingStyleNameConstants styleNameConstants;
    private int counter;

    public String getNext() {
        ++counter;

        return styleNameConstants.QP_ORDERED_UNIQUE() + "-" + String.valueOf(counter);
    }

}
