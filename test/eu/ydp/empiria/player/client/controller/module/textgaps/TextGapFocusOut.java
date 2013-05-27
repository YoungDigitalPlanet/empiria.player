package eu.ydp.empiria.player.client.controller.module.textgaps;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.extensions.Extension;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionTestBase;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.modules.ModuleExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleConnectorExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleCreator;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

public abstract class TextGapFocusOut extends ExtensionTestBase {

	private boolean  passed = false;

	protected FlowRequestInvoker flowRequestInvoker;

	protected void initDeliveryEngine(){
		List<Extension> exts = new ArrayList<Extension>();
		exts.add(new MockModuleConnectorExtension());
		exts.add(new FlowRequestInvokerExtension());
		super.initDeliveryEngine(exts, false);
	}

	protected void setPassed(){
		passed = true;
	}

	public boolean isPassed() {
		return passed;
	}

	@Override
	protected XmlData[] getItemXMLDatas(){

		Document itemDoc = XMLParser.parse("<assessmentItem identifier=\"inlineChoice\" title=\"Interactive text\"><itemBody><mockInteraction responseIdentifier=\"x\"/></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
		XmlData itemData = new XmlData(itemDoc, "");
		Document itemDoc2 = XMLParser.parse("<assessmentItem identifier=\"inlineChoice2\" title=\"Interactive text 2\"><itemBody></itemBody><variableProcessing template=\"default\"/></assessmentItem>");
		XmlData itemData2 = new XmlData(itemDoc2, "");

		XmlData[] itemDatas = new XmlData[2];
		itemDatas[0] = itemData;
		itemDatas[1] = itemData2;

		return itemDatas;
	}

	protected class FlowRequestInvokerExtension extends InternalExtension implements FlowRequestSocketUserExtension{

		@Override
		public void init() {
		}

		@Override
		public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
			flowRequestInvoker = fri;
		}

	}

	protected class MockModuleConnectorExtension extends ModuleExtension implements ModuleConnectorExtension {

		@Override
		public ModuleCreator getModuleCreator() {
			return new ModuleCreator() {

				@Override
				public boolean isMultiViewModule() {
					return true;
				}

				@Override
				public boolean isInlineModule() {
					return false;
				}

				@Override
				public IModule createModule() {
					return new MockModule();
				}
			};
		}

		@Override
		public String getModuleNodeName() {
			return "mockInteraction";
		}

	}

	protected class MockModule extends OneViewInteractionModuleBase implements Factory<MockModule>{

		private final EventsBus eventsBus = PlayerGinjectorFactory.getNewPlayerGinjectorForGWTTestCase().getEventsBus();

		public MockModule(){
		}

		@Override
		public void markAnswers(boolean mark) {
		}

		@Override
		public void showCorrectAnswers(boolean show) {
		}

		@Override
		public void lock(boolean lo) {
		}

		@Override
		public void reset() {
		}

		@Override
		public JSONArray getState() {
			return new JSONArray();
		}

		@Override
		public void setState(JSONArray newState) {

		}

		@Override
		public JavaScriptObject getJsSocket() {
			return JavaScriptObject.createObject();
		}

		@Override
		public void installViews(List<HasWidgets> placeholders) {
			eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), new PlayerEventHandler() {

				@Override
				public void onPlayerEvent(PlayerEvent event) {
					if(event.getType()==PlayerEventTypes.BEFORE_FLOW){
						setPassed();
					}
				}
			});
		}

		@Override
		public void onBodyLoad() {
		}

		@Override
		public void onBodyUnload() {
		}

		@Override
		public void onSetUp() {
		}

		@Override
		public void onStart() {
		}

		@Override
		public void onClose() {
		}

		@Override
		public MockModule getNewInstance() {
			return new MockModule();
		}

	}
}
