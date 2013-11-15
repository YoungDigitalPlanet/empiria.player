package eu.ydp.empiria.player.client.module.info;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.MocksCollector;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;

@RunWith(MockitoJUnitRunner.class)
public class ContentFieldRegistryTest {

	@InjectMocks
	private ContentFieldRegistry testObj;

	@Mock
	private ContentFieldInfoListInitializer contentFieldInfoListInitializer;
	@Mock
	private ContentFieldInfoSearcher contentFieldInfoSearcher;
	@Mock
	Optional<ContentFieldInfo> expectedResult;
	List<ContentFieldInfo> fieldInfos;

	private final MocksCollector mocksCollector = new MocksCollector();

	@Before
	public void setUp() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		fieldInfos = getContentFieldInfos();
	}

	@After
	public void tearDown() {
		verifyNoMoreInteractions(mocksCollector.getMocks());
	}

	@Test
	public void getFieldInfoTest_isNotRegistered() {
		// given
		String fieldName = "fieldName";
		List<ContentFieldInfo> contentFieldInfosFromInitializer = Lists.<ContentFieldInfo> newArrayList(new ContentFieldInfo());

		when(contentFieldInfoListInitializer.initilize()).thenReturn(contentFieldInfosFromInitializer);
		when(contentFieldInfoSearcher.findByTagName(fieldName, fieldInfos)).thenReturn(expectedResult);
		// when

		Optional<ContentFieldInfo> result = testObj.getFieldInfo(fieldName);

		// then
		InOrder inOrder = inOrder(mocksCollector.getMocks());
		inOrder.verify(contentFieldInfoListInitializer).initilize();
		inOrder.verify(contentFieldInfoSearcher).findByTagName(fieldName, fieldInfos);

		assertSame(expectedResult, result);
	}

	@Test
	public void getFieldInfoTest_isRegistered() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		// given
		String fieldName = "fieldName";
		fieldInfos.add(new ContentFieldInfo());
		when(contentFieldInfoSearcher.findByTagName(fieldName, fieldInfos)).thenReturn(expectedResult);
		// when

		Optional<ContentFieldInfo> result = testObj.getFieldInfo(fieldName);

		// then
		InOrder inOrder = inOrder(mocksCollector.getMocks());
		inOrder.verify(contentFieldInfoSearcher).findByTagName(fieldName, fieldInfos);

		assertSame(expectedResult, result);
	}

	@SuppressWarnings("unchecked")
	private List<ContentFieldInfo> getContentFieldInfos() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		return (List<ContentFieldInfo>) new ReflectionsUtils().getValueFromFiledInObject("fieldInfos", testObj);
	}
}