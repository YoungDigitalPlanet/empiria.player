package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;

import java.util.List;

public class SessionDataSupplierMock implements SessionDataSupplier {

    private AssessmentSessionDataSocket assessmentSessionDataSocket;

    private List<ItemSessionDataSocket> itemSessionDataSocketList = Lists.newArrayList();

    public void setAssessmentSessionDataSocket(AssessmentSessionDataSocket assessmentSessionDataSocket) {
        this.assessmentSessionDataSocket = assessmentSessionDataSocket;
    }

    public void setItemSessionDataSocketList(List<ItemSessionDataSocket> itemSessionDataSocketList) {
        this.itemSessionDataSocketList = itemSessionDataSocketList;
    }

    @Override
    public AssessmentSessionDataSocket getAssessmentSessionDataSocket() {
        return assessmentSessionDataSocket;
    }

    @Override
    public ItemSessionDataSocket getItemSessionDataSocket(int index) {
        return itemSessionDataSocketList.get(index);
    }

}
