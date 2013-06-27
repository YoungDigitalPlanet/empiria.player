package eu.ydp.empiria.player.client.module.textentry;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_FONT_SIZE;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH;

import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.factory.TextEntryModuleFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.gap.GapBase;
import eu.ydp.empiria.player.client.module.gap.GapDropHandler;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.StringUtils;

public class TextEntryModule extends GapBase implements SourcelistClient {

	private final StyleSocket styleSocket;

	private final SourcelistManager sourcelistManager;

	private final ResponseSocket responseSocket;

	protected Map<String, String> styles;
	

	@Inject
	public TextEntryModule(TextEntryModuleFactory moduleFactory, StyleSocket styleSocket, @PageScoped ResponseSocket responseSocket,final SourcelistManager sourcelistManager) {
		this.styleSocket = styleSocket;
		this.responseSocket = responseSocket;
		this.sourcelistManager = sourcelistManager;

		presenter = moduleFactory.getTextEntryModulePresenter(this);
		presenter.addPresenterHandler(new PresenterHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				sourcelistManager.onUserValueChanged();
				updateResponse(true);
			}
			
			@Override
			public void onBlur(BlurEvent event) {
				if (isMobileUserAgent()) {
					sourcelistManager.onUserValueChanged();
					updateResponse(true);
				}
			}
		});

		presenter.addDomHandlerOnObjectDrop(new GapDropHandler() {

			@Override
			public void onDrop(DragDataObject dragDataObject) {
				String itemID = dragDataObject.getItemId();
				String sourceModuleId = dragDataObject.getSourceId();
				String targetModuleId = getModuleId();

				sourcelistManager.dragEnd(itemID, sourceModuleId,
						targetModuleId);
			}
		});
	
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
	protected boolean isResponseCorrect() {
		Response response = getResponse();
		List<Boolean> evaluateResponse = responseSocket.evaluateResponse(response);
		return evaluateResponse.get(0);
	}

	@Override
	public String getCorrectAnswer() {
		Response response = getResponse();
		return response.correctAnswers.getSingleAnswer();
	}

	@Override
	protected void setCorrectAnswer() {
		String correctAnswer = getCorrectAnswer();
		String replaced = gapExpressionReplacer.ensureReplacement(correctAnswer);
		presenter.setText(replaced);
	}

	@Override
	protected void setPreviousAnswer() {
		presenter.setText(getCurrentResponseValue());
	}

	@Override
	protected String getCurrentResponseValue() {
		Response response = getResponse();
		return (response.values.size() > 0) ? response.values.get(0) : StringUtils.EMPTY_STRING;
	}

	protected void updateResponse(boolean userInteract) {
		updateResponse(userInteract, false);
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

			lastValue = presenter.getText();
			getResponse().add(lastValue);
			fireStateChanged(userInteract, isReset);
		}
	}

	@Override
	public String getDragItemId() {
		return presenter.getText();
	}

	@Override
	public void setDragItem(String itemId) {
		String value = sourcelistManager.getValue(itemId, getModuleId());
		presenter.setText(value);
	}

	@Override
	public void removeDragItem() {
		presenter.setText("");
	}

	TextEntryModulePresenter getTextEntryPresenter() {
		return (TextEntryModulePresenter)presenter;
	}

	@Override
	public void lockDropZone() {
		getTextEntryPresenter().lockDragZone();

	}

	@Override
	public void unlockDropZone() {
		getTextEntryPresenter().unlockDragZone();

	}
}
