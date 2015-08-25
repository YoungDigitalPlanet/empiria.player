package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.controller.data.ItemDataSourceCollectionManager;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentItemStyleProviderTest {

    @InjectMocks
    private CurrentItemStyleProvider testObj;
    @Mock
    private FlowDataSupplier flowData;
    @Mock
    private ItemDataSourceCollectionManager itemDataSourceCollectionManager;
    @Mock
    private ItemStylesContainer itemStylesContainer;

    private String identifier = "id";

    @Before
    public void init() {
        when(flowData.getCurrentPageIndex()).thenReturn(0);
        when(itemDataSourceCollectionManager.getItemIdentifier(0)).thenReturn(identifier);
    }

    @Test
    public void shouldReturnStyleWrappedWithOptional() {
        // given
        String style = "style";
        Optional<String> optionalStyle = Optional.of(style);
        when(itemStylesContainer.getStyle(identifier)).thenReturn(optionalStyle);

        // when
        Optional<String> result = testObj.getCurrentItemStyle();

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(style);
    }

    @Test
    public void shouldReturnEmptyOptional_whenStyleForPageDoesNotExist() {
        // given
        Optional<String> optionalStyle = Optional.absent();
        when(itemStylesContainer.getStyle(identifier)).thenReturn(optionalStyle);

        // when
        Optional<String> result = testObj.getCurrentItemStyle();

        // then
        assertThat(result.isPresent()).isFalse();
    }
}