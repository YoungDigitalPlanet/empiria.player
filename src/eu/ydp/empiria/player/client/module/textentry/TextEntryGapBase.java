package eu.ydp.empiria.player.client.module.textentry;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.*;

import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.gap.GapBase;
import eu.ydp.empiria.player.client.module.gap.GapDropHandler;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;

public abstract class TextEntryGapBase extends GapBase implements SourcelistClient {

	private static final String SOURCE_LIST_ID = "sourcelistId";

	@Inject
	@PageScoped
	protected SourcelistManager sourcelistManager;

	@Inject
	protected StyleSocket styleSocket;

	@Inject
	protected DragContentController dragContentController;

	@Inject
	@PageScoped
	protected ResponseSocket responseSocket;

	public void postConstruct() {
		addHandlersInPresenter();
	}

	private void addHandlersInPresenter() {
		getTextEntryPresenter().addPresenterHandler(new TextEntryPresenterHandler());
		getTextEntryPresenter().addDomHandlerOnObjectDrop(new TextEntryDomHandler());
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		// implementacja wyżej
	}

	@Override
	protected void setPreviousAnswer() {
		presenter.setText(getCurrentResponseValue());
	}

	@Override
	protected ResponseSocket getResponseSocket() {
		return responseSocket;
	}

	@Override
	public void reset() {
		if (!Strings.isNullOrEmpty(getTextEntryPresenter().getText())) {
			presenter.setText(StringUtils.EMPTY_STRING);
			updateResponse(false, true);
		}
		sourcelistManager.onUserValueChanged();
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
	public void removeDragItem() {
		presenter.setText("");
	}

	@Override
	public void setDragItem(String itemId) {
		SourcelistItemValue item = sourcelistManager.getValue(itemId, getIdentifier());
		String newText = dragContentController.getTextFromItemAppropriateToType(item);

		presenter.setText(newText);
		updateResponse(true);
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

	@Override
	protected void setCorrectAnswer() {
		String correctAnswer = getCorrectAnswer();
		String replaced = gapExpressionReplacer.ensureReplacement(correctAnswer);
		presenter.setText(replaced);
	}

	@Override
	protected void updateResponse(boolean userInteract, boolean isReset) {
		if (showingAnswer) {
			return;
		}

		if (getResponse() != null) {
			if (lastValue != null) {
				getResponse().remove(lastValue);
			}

			lastValue = getTextEntryPresenter().getText();
			getResponse().add(lastValue);
			fireStateChanged(userInteract, isReset);
		}
	}

	protected void initReplacements(Map<String, String> styles) {
		String charactersSet = getSubstitutionCharactersSetFromStyles(styles);

		if (charactersSet != null) {
			gapExpressionReplacer.useCharacters(charactersSet);
			getTextEntryPresenter().makeExpressionReplacements(gapExpressionReplacer.getReplacer());
		}
	}

	private String getSubstitutionCharactersSetFromStyles(Map<String, String> styles) {
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_EXPRESSION_REPLACEMENTS)) {
			return styles.get(EMPIRIA_TEXTENTRY_GAP_EXPRESSION_REPLACEMENTS);
		} else if (styles.containsKey(EMPIRIA_MATH_GAP_EXPRESSION_REPLACEMENTS)) {
			return styles.get(EMPIRIA_MATH_GAP_EXPRESSION_REPLACEMENTS);
		} else {
			return null;
		}
	}

	protected void updateResponse(boolean userInteract) {
		updateResponse(userInteract, false);
	}

	@Override
	public String getSourcelistId() {
		String sourceListId = getElementAttributeValue(SOURCE_LIST_ID);
		return sourceListId;
	}

	private TextEntryGapModulePresenterBase getTextEntryPresenter() {
		return (TextEntryGapModulePresenterBase) presenter;
	}

	private final class TextEntryDomHandler implements GapDropHandler {
		@Override
		public void onDrop(DragDataObject dragDataObject) {
			String itemID = dragDataObject.getItemId();
			String sourceModuleId = dragDataObject.getSourceId();
			String targetModuleId = getIdentifier();

			sourcelistManager.dragEnd(itemID, sourceModuleId, targetModuleId);
		}
	}

	private final class TextEntryPresenterHandler implements PresenterHandler {

		@Override
		public void onBlur(BlurEvent event) {
			sourcelistManager.onUserValueChanged();
			updateResponse(true);
		}
	}
}