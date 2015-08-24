package eu.ydp.empiria.player.client.module.info;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.MocksCollector;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContentFieldRegistryTest {

    @InjectMocks
    private ContentFieldRegistry testObj;

    @Mock
    private ContentFieldInfoListProvider contentFieldInfoListProvider;
    @Mock
    private ContentFieldInfoSearcher contentFieldInfoSearcher;
    @Mock
    private Optional<ContentFieldInfo> expected;

    private List<ContentFieldInfo> fieldInfos = Lists.newArrayList();
    private String fieldName = "fieldName";

    @Before
    public void setUp() {
        when(contentFieldInfoListProvider.get()).thenReturn(fieldInfos);
        when(contentFieldInfoSearcher.findByTagName(fieldName, fieldInfos)).thenReturn(expected);
    }

    @Test
    public void shouldRegisterNewFieldInfo() {
        // when
        Optional<ContentFieldInfo> result = testObj.getFieldInfo(fieldName);

        // then
        verify(contentFieldInfoListProvider).get();
        assertEquals(expected, result);
    }

    @Test
    public void shouldGetFieldInfo_withoutRegister() {
        // given
        fieldInfos.add(new ContentFieldInfo());
        testObj.getFieldInfo(fieldName);

        // when
        Optional<ContentFieldInfo> result = testObj.getFieldInfo(fieldName);

        // then
        verify(contentFieldInfoListProvider, times(1)).get();
        assertEquals(expected, result);
    }
}
