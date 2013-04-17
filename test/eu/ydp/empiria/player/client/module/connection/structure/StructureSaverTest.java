package eu.ydp.empiria.player.client.module.connection.structure;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.json.YNativeJsonProvider;

public class StructureSaverTest {

	private StructureSaver structureSaver;

	@Before
	public void before() {
		structureSaver = new StructureSaver(new YNativeJsonProvider());
	}

	@Test
	public void saveTest() {
		// given
		List<SimpleMatchSetBean> simpleMatchSetBeans = new ArrayList<SimpleMatchSetBean>();
		simpleMatchSetBeans.add(createSimpleMatchSetBean("a0", "a1", "a2", "a3", "a4", "a5"));
		simpleMatchSetBeans.add(createSimpleMatchSetBean("b0", "b1", "b2", "b3"));

		// when
		YJsonArray result = structureSaver.save(simpleMatchSetBeans);

		// then
		assertEquals(2, result.size());

		YJsonArray subArray0 = result.get(0).isArray();
		assertEquals(6, subArray0.size());
		assertSubArray(subArray0, "a");

		YJsonArray subArray1 = result.get(1).isArray();
		assertEquals(4, subArray1.size());
		assertSubArray(subArray1, "b");

	}

	private void assertSubArray(YJsonArray subArray, String prefix) {
		for (int i = 0; i < subArray.size(); i++) {
			assertEquals(prefix + i, subArray.get(i).isString().stringValue());
		}
	}

	private SimpleMatchSetBean createSimpleMatchSetBean(String... ids) {
		SimpleMatchSetBean simpleMatchSetBean = new SimpleMatchSetBean();

		List<SimpleAssociableChoiceBean> list = new ArrayList<SimpleAssociableChoiceBean>();

		for (String id : ids) {
			SimpleAssociableChoiceBean associableChoiceBean = new SimpleAssociableChoiceBean();
			associableChoiceBean.setIdentifier(id);
			list.add(associableChoiceBean);
		}

		simpleMatchSetBean.setSimpleAssociableChoices(list);

		return simpleMatchSetBean;
	}
}
