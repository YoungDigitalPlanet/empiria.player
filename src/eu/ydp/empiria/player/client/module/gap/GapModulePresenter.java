package eu.ydp.empiria.player.client.module.gap;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;

public interface GapModulePresenter {

	public static final String WRONG = "wrong";

	public static final String CORRECT = "correct";

	public static final String NONE = "none";

	void setWidth(double value, Unit unit);

	int getOffsetWidth();

	void setHeight(double value, Unit unit);

	int getOffsetHeight();

	void setMaxLength(int length);

	void setFontSize(double value, Unit unit);

	int getFontSize();

	void setText(String text);

	HasWidgets getContainer();

	void installViewInContainer(HasWidgets container);

	void setViewEnabled(boolean enabled);

	void setMarkMode(String mode);

	void removeMarking();
}
