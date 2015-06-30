package eu.ydp.empiria.player.client.module.identification.predicates;

import eu.ydp.empiria.player.client.module.identification.presenter.SelectableChoicePresenter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChoiceToIdentifierTransformerTest {
    @InjectMocks
    private ChoiceToIdentifierTransformer testObj;
    @Mock
    private SelectableChoicePresenter presenter;

    @Test
    public void shouldReturnTrue_whenChoiceIsSelected() {
        // given
        String identifier = "id";
        when(presenter.getIdentifier()).thenReturn(identifier);

        // when
        String result = testObj.apply(presenter);

        // then
        assertThat(result).isEqualTo(identifier);
    }
}
