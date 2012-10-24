package eu.ydp.empiria.player.client.module.connection;

import java.util.Map;

import com.google.gwt.user.client.ui.IsWidget;

public interface ConnectionSurface extends IsWidget{

	public abstract void drawLine(double fromX, double fromY, double toX, double toY);

	public abstract void clear();

	public abstract boolean isPointOnPath(int xPos, int yPos, int approximation);

	public abstract void applyStyles(Map<String, String> styles);

}