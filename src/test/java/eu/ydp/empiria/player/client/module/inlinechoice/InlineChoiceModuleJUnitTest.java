package eu.ydp.empiria.player.client.module.inlinechoice;

import com.google.gwt.junit.GWTMockUtilities;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class InlineChoiceModuleJUnitTest extends AbstractTestBase {
    InlineChoiceModule instance;

    @Before
    public void before() {
        instance = spy(injector.getInstance(InlineChoiceModule.class));
        doReturn(new HashMap<String, String>()).when(instance).getStyles();
    }

    @Test
    public void shouldSetParentInlineModuleCorrectlyForDefaultController() {
        instance.controller = injector.getInstance(InlineChoiceDefaultController.class);
        instance.initModule();
        IUniqueModule parentModule = instance.getController().getParentInlineModule();

        assertThat(parentModule, is((IUniqueModule) instance));
    }

    @Test
    public void shouldSetParentInlineModuleCorrectlyForPopupController() {
        instance.controller = injector.getInstance(InlineChoicePopupController.class);
        instance.initModule();
        IUniqueModule parentModule = instance.getController().getParentInlineModule();
        assertThat(parentModule, is((IUniqueModule) instance));
    }

    @BeforeClass
    public static void prepareTestEnviroment() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restoreEnviroment() {
        GWTMockUtilities.restore();
    }

}
