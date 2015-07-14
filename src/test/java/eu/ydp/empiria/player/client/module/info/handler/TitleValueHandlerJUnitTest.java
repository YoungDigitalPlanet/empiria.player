package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo.FieldType;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TitleValueHandlerJUnitTest extends AbstractTestBase {

    private static final String LESSON_TITLE = "Lesson 1";

    private static final String ITEM2_TITLE = "Page 2";

    private static final String ITEM1_TITLE = "Page 1";

    private FieldValueHandlerFactory handlerFactory;

    @Before
    public void initialize() {
        handlerFactory = injector.getInstance(FieldValueHandlerFactory.class);
    }

    @Test
    public void shouldReturnItemTitle() {
        DataSourceDataSupplier dataSupplier = mock(DataSourceDataSupplier.class);
        ContentFieldInfo info = mock(ContentFieldInfo.class);

        when(dataSupplier.getItemTitle(0)).thenReturn(ITEM1_TITLE);
        when(dataSupplier.getItemTitle(1)).thenReturn(ITEM2_TITLE);
        when(info.getType()).thenReturn(FieldType.ITEM);

        FieldValueHandler handler = handlerFactory.getTitleValueHandler(dataSupplier);

        assertThat(handler.getValue(info, 0), is(equalTo(ITEM1_TITLE)));
        assertThat(handler.getValue(info, 1), is(equalTo(ITEM2_TITLE)));
    }

    @Test
    public void shouldReturnAssessmentTitle() {
        DataSourceDataSupplier dataSupplier = mock(DataSourceDataSupplier.class);
        ContentFieldInfo info = mock(ContentFieldInfo.class);

        when(dataSupplier.getAssessmentTitle()).thenReturn(LESSON_TITLE);
        when(info.getType()).thenReturn(FieldType.TEST);

        FieldValueHandler handler = handlerFactory.getTitleValueHandler(dataSupplier);

        assertThat(handler.getValue(info, 0), is(equalTo(LESSON_TITLE)));
        assertThat(handler.getValue(info, 1), is(equalTo(LESSON_TITLE)));
    }

}
