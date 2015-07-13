package eu.ydp.empiria.player.client.module.info;

import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.empiria.player.client.style.ModuleStyleImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class InfoModuleCssProgressMappingConfigurationParserTest {
    private static final String REPORT_PROGRESS_PREFIX = "-empiria-info-item-result-";
    private final String styleName = "xxx-x";

    @Spy
    private final ModuleStyle moduleStyle = new ModuleStyleImpl(new HashMap<String, String>());
    @InjectMocks
    private InfoModuleCssProgressMappingConfigurationParser instance;

    @Test
    public void getCssProgressToStyleMappingNoConfiguration() throws Exception {
        Map<Integer, String> toStyleMapping = instance.getCssProgressToStyleMapping();
        assertThat(toStyleMapping).isEmpty();
    }

    @Test
    public void getCssProgressToStyleMapping() throws Exception {
        for (int x = 0; x <= 100; x += 9) {
            moduleStyle.put(REPORT_PROGRESS_PREFIX + x, styleName + x);
        }

        Map<Integer, String> styleMapping = instance.getCssProgressToStyleMapping();
        assertThat(styleMapping).isNotEmpty();
        assertThat(styleMapping).hasSize(12);

        for (int x = 0; x <= 100; x += 9) {
            assertThat(styleMapping.get(x)).isEqualTo(styleName + x);
        }

    }

}
