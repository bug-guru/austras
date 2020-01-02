/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.common.model.ComponentRef;
import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;

import java.util.Optional;

public class MapperProcessorPlugin implements AustrasProcessorPlugin {
    @Override
    public void process(ProcessingContext ctx) {
        for (var ref : ctx.componentManager().roundDependencies()) {
            createMapperModel(ctx, ref)
        }
    }

    private Optional<MapperModel> createMapperModel(ProcessingContext ctx, ComponentRef ref) {
        ref.getType().
    }

}
