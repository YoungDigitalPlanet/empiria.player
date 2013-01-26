package eu.ydp.empiria.player.client.controller.extensions;

import org.junit.Test;



public class DeliveryEngineSocketUserExtensionTest {//extends ExtensionTestBase {

	@Test
	public void testName() throws Exception {
		
	}
	
	/* TODO: FIXME
	protected DeliveryEngine de;
	protected DeliveryEngineSocket des;

	public void testStateInitialPageToc(){
		testStateInitialPage("\"TOC\"");
	}

	public void testStateInitialPageItem(){
		testStateInitialPage("0");
	}

	public void testStateInitialPageSummary(){
		testStateInitialPage("\"SUMMARY\"");
	}

	protected void testStateInitialPage(String expectedPage){
		PlayerGinjector injector = PlayerGinjectorFactory.getPlayerGinjector();
		de = injector.getDeliveryEngine();
		de.init(JavaScriptObject.createObject());
		Extension ext = new MockDeliveryEngineSocketUserExtension();
		de.setFlowOptions(new FlowOptions(true, true, PageItemsDisplayMode.ONE, ActivityMode.NORMAL));
		de.loadExtension(ext);
		des.setStateString("["+expectedPage+",[[[[],[],0]]],[]]");
		de.load(getAssessmentXMLData(), getItemXMLDatas());
		String stateRetrieved = des.getStateString();
		String currPage = des.getStateString().substring(1,stateRetrieved.indexOf(","));
		assertEquals(expectedPage, currPage);
	}

	protected class MockDeliveryEngineSocketUserExtension extends InternalExtension implements DeliveryEngineSocketUserExtension{

		@Override
		public void init() {
		}

		@Override
		public void setDeliveryEngineSocket(DeliveryEngineSocket d) {
			des = d;
		}

	}
 */
}
