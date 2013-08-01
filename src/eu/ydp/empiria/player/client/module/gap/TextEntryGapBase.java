package eu.ydp.empiria.player.client.module.gap;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_EXPRESSION_REPLACEMENTS;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_EXPRESSION_REPLACEMENTS;

import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistClient;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.textentry.DragContentController;
import eu.ydp.empiria.player.client.module.view.HasDimensions;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public abstract class TextEntryGapBase extends GapBase implements SourcelistClient {

	protected abstract TextEntryPresenterUnlocker getTextEntryGapPresenter();
	
	protected final SourcelistManager sourcelistManager;
	protected final StyleSocket styleSocket;
	protected final DragContentController dragContentController;


	private ResponseSocket responseSocket;

	public TextEntryGapBase(
			GapModulePresenter presenter, 
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
	
	
	@Override
	public void onStart() {
		sourcelistManager.registerModule(this);
		setBindingValues();
	}
	
	@Override
	public void onSetUp() {
		updateResponse(false);
		registerBindingContexts();
	}
	
	@Override
	public void reset() {
		super.reset();
		sourcelistManager.onUserValueChanged();
	}
	

	@Override
	public String getDragItemId() {
		return presenter.getText();
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
	public void lockDropZone() {
		getTextEntryGapPresenter().lockDragZone();
	}

	@Override
	public void unlockDropZone() {
		getTextEntryGapPresenter().unlockDragZone();
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
			getTextEntryGapPresenter().unlockDragZone();
		}
	}
	
	protected void addPlayerEventHandlers(){
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if(event.getType() == PlayerEventTypes.BEFORE_FLOW){
					updateResponse(false, false);
					presenter.removeFocusFromTextField();
				}
			}
		},new CurrentPageScope());
	}
	
	protected void initReplacements(Map<String, String> styles) {
		boolean containsReplacementStyle = styles.containsKey(EMPIRIA_TEXTENTRY_GAP_EXPRESSION_REPLACEMENTS)  ||  styles.containsKey(EMPIRIA_MATH_GAP_EXPRESSION_REPLACEMENTS);
		if (containsReplacementStyle){
			String charactersSet = Objects.firstNonNull(styles.get(EMPIRIA_TEXTENTRY_GAP_EXPRESSION_REPLACEMENTS), styles.get(EMPIRIA_MATH_GAP_EXPRESSION_REPLACEMENTS));
			gapExpressionReplacer.useCharacters(charactersSet);
			presenter.makeExpressionReplacements(gapExpressionReplacer.getReplacer());
		}
		
	}
	
	protected boolean isMobileUserAgent(){
		return UserAgentChecker.getMobileUserAgent() != MobileUserAgent.UNKNOWN;
	}
}