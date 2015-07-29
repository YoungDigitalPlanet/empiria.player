package eu.ydp.empiria.player.client.module.dragdrop;

import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public interface Resizable {

    void setSize(HasDimensions size);

    HasDimensions getSize();
}
