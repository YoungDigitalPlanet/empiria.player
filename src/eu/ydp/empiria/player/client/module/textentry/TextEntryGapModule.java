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
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.NumberUtils;

public class TextEntryGapModule extends TextEntryGapBase implements SourcelistClient {
	
	@Inject
	public TextEntryGapModule(TextEntryModulePresenter presenter,
			StyleSocket styleSocket, 
			@PageScoped SourcelistManager sourcelistManager,
			DragContentController dragContentController,
			@PageScoped ResponseSocket responseSocket) {
		super(presenter, styleSocket, sourcelistManager, dragContentController, responseSocket);
	}

	protected Map<String, String> styles;
	


	@Override
	public void reset() {
		super.reset();
		sourcelistManager.onUserValueChanged();
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

	private void installViewPanel(HasWidgets placeholder) {
		applyIdAndClassToView((Widget) presenter.getContainer());
		presenter.installViewInContainer(placeholder);
	}

	// ------------------------ INTERFACES ------------------------

	@Override
	public void onSetUp() {
		updateResponse(false);
		registerBindingContexts();
	}

	@Override
	public void onStart() {
		sourcelistManager.registerModule(this);
		setBindingValues();
	}


	@Override
	public String getDragItemId() {
		return getTextEntryPresenter().getText();
	}

	@Override
	public void setDragItem(String itemId) {
		SourcelistItemValue item = sourcelistManager.getValue(itemId, getIdentifier());
		String newText = dragContentController.getTextFromItemAppropriateToType(item);
		
		presenter.setText(newText);
	}

	@Override
	public void removeDragItem() {
		presenter.setText("");
	}

	protected TextEntryModulePresenter getTextEntryPresenter() {
		return (TextEntryModulePresenter) presenter;
	}

	@Override
	public void lockDropZone() {
		getTextEntryPresenter().lockDragZone();

	}

	@Override
	public void unlockDropZone() {
		getTextEntryPresenter().unlockDragZone();	
	}

	@Override
	public void setSize(HasDimensions size) {
		// intentionally empty - text gap does not fit its size
	}
			
	@Override
	public void lock(boolean lock) {
		super.lock(lock);
		if (lock) {
			sourcelistManager.lockGroup(getIdentifier());
		} else {
			sourcelistManager.unlockGroup(getIdentifier());
			getTextEntryPresenter().unlockDragZone();
		}
	}

}
