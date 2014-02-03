package eu.ydp.empiria.player.client.module.conversion;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.json.YJsonValue;
import eu.ydp.gwtutil.client.service.json.NativeJSONService;
import eu.ydp.gwtutil.json.YNativeJsonFactory;

public class StateToStateAndStructureConverterTest {

	private StateToStateAndStructureConverter testObj;

	@Before
	public void before() {
		testObj = new StateToStateAndStructureConverter(new NativeJSONService());
	}

	@Test
	public void convertTest_canConvertIsTrue() {
		YJsonArray yJsonArray = YNativeJsonFactory.createArray();
		YJsonValue result = testObj.convert(yJsonArray);

		assertNotSame(result, yJsonArray);

		yJsonArray.set(0, YNativeJsonFactory.createString("test"));

		result = testObj.convert(yJsonArray);

		assertNotSame(result, yJsonArray);

	}

	@Test
	public void convertTest_canConvertIsFalse() {
		YJsonArray yJsonArray = YNativeJsonFactory.createArray();
		yJsonArray.set(0, YNativeJsonFactory.createObject());
		YJsonValue result = testObj.convert(yJsonArray);

		assertSame(result, yJsonArray);
	}
}
