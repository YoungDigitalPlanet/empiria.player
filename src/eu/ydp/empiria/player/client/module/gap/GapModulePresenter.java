package eu.ydp.empiria.player.client.module.gap;

import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;

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

	String getText();

	HasWidgets getContainer();

	void installViewInContainer(HasWidgets container);

	void setViewEnabled(boolean enabled);

	void setMarkMode(String mode);

	void removeMarking();

	void addPresenterHandler(PresenterHandler handler);

	void removeFocusFromTextField();

	IsExListBox getListBox();
	
	void makeExpressionReplacements(Map<String, String> replacements);
	
}
