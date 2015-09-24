package eu.ydp.empiria.player.client.module.info.handler;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PageCountValueHandlerTest {

    @InjectMocks
    private PageCountValueHandler testObj;
    @Mock
    private DataSourceDataSupplier dataSourceDataSupplier;

    @Test
    public void shouldReturnItemsCount() {
        // given
        int itemsCount = 5;
        String itemsCountString = "5";
        when(dataSourceDataSupplier.getItemsCount()).thenReturn(itemsCount);

        // when
        String result = testObj.getValue(mock(ContentFieldInfo.class), 0);

        // then
        assertThat(result).isEqualTo(itemsCountString);
    }
}