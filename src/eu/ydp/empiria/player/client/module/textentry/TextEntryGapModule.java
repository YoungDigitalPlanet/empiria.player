package eu.ydp.empiria.player.client.module.textentry;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_FONT_SIZE;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH;

import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.gap.TextEntryGapBase;
import eu.ydp.empiria.player.client.module.gap.TextEntryPresenterUnlocker;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

public class TextEntryGapModule extends TextEntryGapBase implements SourcelistClient {

	protected Map<String, String> styles;
	protected Integer fontSize = 16;
	
	@Inject
	public TextEntryGapModule(TextEntryModulePresenter presenter,
			StyleSocket styleSocket, 
			@PageScoped SourcelistManager sourcelistManager,
			DragContentController dragContentController,
			@PageScoped ResponseSocket responseSocket) {
		super(presenter, styleSocket, sourcelistManager, dragContentController, responseSocket);
	}


	@Override
	public void installViews(List<HasWidgets> placeholders) {
		styles = styleSocket.getStyles(getModuleElement());

		addPlayerEventHandlers();
		setDimensions(styles);
		setMaxlengthBinding(styles, getModuleElement());
		setWidthBinding(styles, getModuleElement());

		installViewPanel(placeholders.get(0));

		initReplacements(styles);
	}

	private void installViewPanel(HasWidgets placeholder) {
		applyIdAndClassToView((Widget) presenter.getContainer());
		presenter.installViewInContainer(placeholder);
	}
	
	
	protected void setDimensions(Map<String, String> styles) {
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_FONT_SIZE)) {
			fontSize = NumberUtils.tryParseInt(styles.get(EMPIRIA_TEXTENTRY_GAP_FONT_SIZE), null);
		}

		presenter.setFontSize(fontSize, Unit.PX);

		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH)) {
			Integer gapWidth = NumberUtils.tryParseInt(styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH), null);
			presenter.setWidth(gapWidth, Unit.PX);
		}
	}

	@Override
	protected TextEntryPresenterUnlocker getTextEntryGapPresenter() {
		return (TextEntryPresenterUnlocker) presenter;
	}
}
