package eu.ydp.empiria.player.client.gin.factory;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.assistedinject.Assisted;

public interface TouchReservationFactory {
    HandlerRegistration addTouchReservationHandler(@Assisted IsWidget widget);
}
