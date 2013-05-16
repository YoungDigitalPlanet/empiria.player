package eu.ydp.empiria.player.client.module.ordering.structure;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.AbstractModuleStructureTestBase;
import eu.ydp.gwtutil.client.json.YJsonArray;


public class OrderInteractionStructureJUnitTest extends
AbstractModuleStructureTestBase<OrderInteractionStructure, OrderInteractionBean, OrderInteractionModuleJAXBParserFactory>{


	@Override
	protected OrderInteractionStructure createModuleStructure() {
		return new OrderInteractionStructureMock();
	}

	@Test
	public void structureTest(){
		OrderInteractionBean bean = createFromXML(OrderInteractionStructureMock.XML, Mockito.mock(YJsonArray.class));
		assertThat(bean.getId()).isEqualTo("dummy1");
	}

}
