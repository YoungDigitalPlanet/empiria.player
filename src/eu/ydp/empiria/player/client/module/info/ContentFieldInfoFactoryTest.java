package eu.ydp.empiria.player.client.module.info;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.module.info.handler.FieldValueHandler;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;

@RunWith(MockitoJUnitRunner.class)
public class ContentFieldInfoFactoryTest {

	@InjectMocks
	private ContentFieldInfoFactory testObj;
	@Mock
	private FieldValueHandler fieldValueHandler;

	@Test
	public void testCreate() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		// when
		ContentFieldInfo result = testObj.create("tagName", fieldValueHandler);
		// then
		assertSame(fieldValueHandler, getHandler(result));
		assertNotNull(result.getTag());
	}

	private FieldValueHandler getHandler(ContentFieldInfo contentFieldInfo) throws SecurityException, IllegalArgumentException, NoSuchFieldException,
			IllegalAccessException {
		return (FieldValueHandler) new ReflectionsUtils().getValueFromFiledInObject("handler", contentFieldInfo);
	}

}
