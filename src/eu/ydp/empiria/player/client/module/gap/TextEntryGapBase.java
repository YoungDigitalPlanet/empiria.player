package eu.ydp.empiria.player.client.module.gap;

import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.textentry.DragContentController;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;

public class TextEntryGapBase extends GapBase {

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
}
