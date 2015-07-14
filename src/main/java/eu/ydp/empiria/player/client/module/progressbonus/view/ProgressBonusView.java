package eu.ydp.empiria.player.client.module.progressbonus.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;

public interface ProgressBonusView extends IsWidget {

    public void showImage(ShowImageDTO dto);
}
