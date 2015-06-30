package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.structure.SimpleOrderChoiceBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class OrderingViewBuilderJUnitTest {

    private OrderingViewBuilder orderingViewBuilder;

    private OrderInteractionBean bean;

    @Mock
    private InlineBodyGeneratorSocket bodyGeneratorSocket;
    @Mock
    private OrderInteractionView interactionView;
    @Mock
    private OrderingItemsDao orderingItemsDao;

    @Before
    public void setUp() throws Exception {
        bean = new OrderInteractionBean();
        orderingViewBuilder = new OrderingViewBuilder(interactionView, orderingItemsDao);
    }

    @Test
    public void shouldBuildView() throws Exception {
        // given
        SimpleOrderChoiceBean choiceBean = getChoiceBean("a");
        SimpleOrderChoiceBean secondChoiceBean = getChoiceBean("b");
        List<SimpleOrderChoiceBean> choiceBeans = Lists.newArrayList(choiceBean, secondChoiceBean);
        bean.setChoiceBeans(choiceBeans);

        // when
        orderingViewBuilder.buildView(bean, bodyGeneratorSocket);

        // then
        verifyThatItemWasAddedToDaoWithCorrectValues(choiceBean, "0");
        verifyThatItemWasAddedToDaoWithCorrectValues(secondChoiceBean, "1");
        verify(orderingItemsDao).createInitialItemsOrder();
    }

    private void verifyThatItemWasAddedToDaoWithCorrectValues(SimpleOrderChoiceBean choiceBean, String id) {
        ArgumentCaptor<OrderingItem> capturedFirstItem = ArgumentCaptor.forClass(OrderingItem.class);
        verify(interactionView).createItem(capturedFirstItem.capture(), eq(choiceBean.getContent()), eq(bodyGeneratorSocket));
        OrderingItem firstItem = capturedFirstItem.getValue();
        verify(orderingItemsDao).addItem(firstItem);
        assertEquals(id, firstItem.getId());
        assertEquals(choiceBean.getIdentifier(), firstItem.getAnswerValue());
    }

    private SimpleOrderChoiceBean getChoiceBean(String identifier) {
        SimpleOrderChoiceBean choiceBean = new SimpleOrderChoiceBean();
        XMLContent content = Mockito.mock(XMLContent.class);
        choiceBean.setContent(content);
        choiceBean.setIdentifier(identifier);
        return choiceBean;
    }
}
