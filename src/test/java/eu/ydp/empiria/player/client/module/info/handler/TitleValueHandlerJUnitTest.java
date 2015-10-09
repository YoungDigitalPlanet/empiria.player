package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TitleValueHandlerJUnitTest {

    private static final String LESSON_TITLE = "Lesson 1";
    private static final String ITEM2_TITLE = "Page 2";
    private static final String ITEM1_TITLE = "Page 1";

    @InjectMocks
    private TitleValueHandler testObj;
    @Mock
    private DataSourceDataSupplier dataSourceDataSupplier;

    @Before
    public void init() {
        when(dataSourceDataSupplier.getItemTitle(0)).thenReturn(ITEM1_TITLE);
        when(dataSourceDataSupplier.getItemTitle(1)).thenReturn(ITEM2_TITLE);
        when(dataSourceDataSupplier.getAssessmentTitle()).thenReturn(LESSON_TITLE);
    }

    @Test
    public void shouldReturnItemTitle() {
        // given
        ContentFieldInfo info = mock(ContentFieldInfo.class);
        when(info.getType()).thenReturn(FieldType.ITEM);

        // when
        String firstItemTitle = testObj.getValue(info, 0);
        String secondItemTitle = testObj.getValue(info, 1);

        // then
        assertThat(firstItemTitle, is(equalTo(ITEM1_TITLE)));
        assertThat(secondItemTitle, is(equalTo(ITEM2_TITLE)));
    }

    @Test
    public void shouldReturnAssessmentTitle() {
        // given
        ContentFieldInfo info = mock(ContentFieldInfo.class);
        when(info.getType()).thenReturn(FieldType.TEST);

        // when
        String result = testObj.getValue(info, 0);

        // then
        assertThat(result, equalTo(LESSON_TITLE));
    }
}
