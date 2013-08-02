package eu.ydp.empiria.player.client.module.textentry;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_EXPRESSION_REPLACEMENTS;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_EXPRESSION_REPLACEMENTS;

import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.gap.GapBase;
import eu.ydp.empiria.player.client.module.gap.GapDropHandler;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.StringUtils;

public class TextEntryGapBase extends GapBase {

	protected final SourcelistManager sourcelistManager;
	protected final StyleSocket styleSocket;
	protected final DragContentController dragContentController;
	protected final ResponseSocket responseSocket;

	public TextEntryGapBase(
			TextEntryGapModulePresenterBase presenter, 
			StyleSocket styleSocket,
			final SourcelistManager sourcelistManager, 
			DragContentController dragContentController, 
			ResponseSocket responseSocket) {
		
		this.styleSocket = styleSocket;
		this.sourcelistManager = sourcelistManager;
		this.presenter = presenter;
		this.dragContentController = dragContentController;
		this.responseSocket = responseSocket;
		
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
				String targetModuleId = getIdentifier();

				sourcelistManager.dragEnd(itemID, sourceModuleId, targetModuleId);
			}
		});
	}
	
	protected void initReplacements(Map<String, String> styles) {
		boolean containsReplacementStyle = styles.containsKey(EMPIRIA_TEXTENTRY_GAP_EXPRESSION_REPLACEMENTS)  ||  styles.containsKey(EMPIRIA_MATH_GAP_EXPRESSION_REPLACEMENTS);
		if (containsReplacementStyle){
			String charactersSet = Objects.firstNonNull(styles.get(EMPIRIA_TEXTENTRY_GAP_EXPRESSION_REPLACEMENTS), styles.get(EMPIRIA_MATH_GAP_EXPRESSION_REPLACEMENTS));
			gapExpressionReplacer.useCharacters(charactersSet);
			getTextEntryPresenter().makeExpressionReplacements(gapExpressionReplacer.getReplacer());
		}
		
	}
	
	protected void addPlayerEventHandlers(){
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if(event.getType() == PlayerEventTypes.BEFORE_FLOW){
					updateResponse(false, false);
					getTextEntryPresenter().removeFocusFromTextField();
				}
			}
		},new CurrentPageScope());
	}
	
	protected TextEntryGapModulePresenterBase getTextEntryPresenter() {
		return (TextEntryGapModulePresenterBase)presenter;
	}
	
	protected void updateResponse(boolean userInteract) {
		updateResponse(userInteract, false);
	}
	
	@Override
	public void installViews(List<HasWidgets> placeholders) {
		// implementacja wy¿ej
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
	}
	
	protected void setCorrectAnswer() {
		String correctAnswer = getCorrectAnswer();
		String replaced = gapExpressionReplacer.ensureReplacement(correctAnswer);
		presenter.setText(replaced);
	}
	
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
}
