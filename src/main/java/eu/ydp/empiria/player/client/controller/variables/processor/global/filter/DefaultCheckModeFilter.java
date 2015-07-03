package eu.ydp.empiria.player.client.controller.variables.processor.global.filter;

import com.google.common.base.Predicate;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;

import javax.annotation.Nullable;
import java.util.Map.Entry;

public class DefaultCheckModeFilter implements Predicate<Entry<Response, DtoModuleProcessingResult>> {

    @Override
    public boolean apply(@Nullable Entry<Response, DtoModuleProcessingResult> entry) {
        return entry.getKey().getCheckMode().equals(CheckMode.DEFAULT);
    }
}
