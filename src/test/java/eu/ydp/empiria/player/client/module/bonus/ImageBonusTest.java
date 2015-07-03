package eu.ydp.empiria.player.client.module.bonus;

import eu.ydp.empiria.player.client.module.bonus.popup.BonusPopupPresenter;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageBonusTest {

    @InjectMocks
    private ImageBonus bonus;
    @Mock
    private BonusPopupPresenter presenter;
    @Mock
    private EmpiriaPaths empiriaPaths;

    @Test
    public void execute() {
        // given
        String url = "bonus.png";
        String fullUrl = "http://x.y.z/bonus1/bonus.png";
        Size size = new Size(100, 100);
        when(empiriaPaths.getCommonsFilePath(url)).thenReturn(fullUrl);
        bonus.setAsset(url, size);

        // when
        bonus.execute();

        // then
        verify(presenter).showImage(fullUrl, size);
    }
}
