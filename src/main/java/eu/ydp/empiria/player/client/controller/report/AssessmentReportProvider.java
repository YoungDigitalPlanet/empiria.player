package eu.ydp.empiria.player.client.controller.report;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

import java.util.List;

public class AssessmentReportProvider extends AbstractReportProvider {

    @Inject
    public AssessmentReportProvider(DataSourceDataSupplier dataSupplier, SessionDataSupplier sessionSupplier,
                                    AssessmentReportFactory factory) {
        super(dataSupplier, sessionSupplier, factory);
    }

    @Override
    public String getTitle() {
        return dataSupplier.getAssessmentTitle();
    }

    public List<ItemReportProvider> getItems() {
        List<ItemReportProvider> items = Lists.newArrayList();

        for (int i = 0; i < getItemsCount(); i++) {
            ItemReportProvider item = factory.getItemReportProvider(i);
            items.add(item);
        }

        return items;
    }

    public int getItemsCount() {
        return dataSupplier.getItemsCount();
    }

    @Override
    protected VariableProviderSocket getVariableProvider() {
        return sessionSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket();
    }

}
