package eu.ydp.empiria.player.client.controller.report;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public class ItemReportProvider extends AbstractReportProvider {

    private int index;

    @Inject
    public ItemReportProvider(@Assisted DataSourceDataSupplier dataSupplier, @Assisted SessionDataSupplier sessionSupplier, @Assisted int index,
                              AssessmentReportFactory factory) {
        super(dataSupplier, sessionSupplier, factory);
        this.index = index;
    }

    @Override
    public String getTitle() {
        return dataSupplier.getItemTitle(index);
    }

    public int getIndex() {
        return index;
    }

    @Override
    protected VariableProviderSocket getVariableProvider() {
        return sessionSupplier.getItemSessionDataSocket(index).getVariableProviderSocket();
    }

}
