package eu.ydp.empiria.player.client.controller.item;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.expression.ExpressionListBuilder;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import eu.ydp.gwtutil.client.xml.NodeListIterable;
import eu.ydp.gwtutil.xml.XMLParser;

@SuppressWarnings("PMD")
@RunWith(MockitoJUnitRunner.class)
public class ItemExpressionParserJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	final String xml = "<expressions>" +
			"<expression mode=\"commutation\">" +
				"<![CDATA['a'+'b'=6]]>" +
			"</expression>" +
			"<expression mode=\"default\">" +
				"<![CDATA[3'sign'5=15]]>" +
			"</expression>" +
			"<expression>" +
				"<![CDATA[3'sign'5=15]]>" +
			"</expression>" +
		"</expressions>";

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(ExpressionListBuilder.class).toInstance(expressionListBuilder);
			binder.bind(XmlData.class).annotatedWith(PageScoped.class).toInstance(xmlData);
			binder.bind(ItemResponseManager.class).annotatedWith(PageScoped.class).toInstance(responseManager);
			binder.bind(ItemXMLWrapper.class).annotatedWith(PageScoped.class).to(ItemXMLWrapper.class);
		}
	}

	@Mock
	private ExpressionListBuilder expressionListBuilder;
	@Mock
	private XmlData xmlData;
	@Mock
	private ItemResponseManager responseManager;

	private ItemExpressionParser instance;
	Document document = spy(XMLParser.parse(xml));

	@Before
	public void before() {
		doReturn(document).when(xmlData).getDocument();
		setUp(new GuiceModuleConfiguration(), new CustomGinModule());
		instance = injector.getInstance(ItemExpressionParser.class);

	}

	@Test
	public void parseAndConnectExpressions() throws Exception {
		instance.parseAndConnectExpressions();
		verify(document).getElementsByTagName("expressions");
		NodeListIterable iterator = new NodeListIterable(document.getElementsByTagName("expressions"));
		for(Node node : iterator){
			verify(expressionListBuilder).parseAndConnectExpressions(Mockito.eq(node.toString()), Mockito.anyMap());
		}

	}

}
