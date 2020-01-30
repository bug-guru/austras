/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper.apt;

import guru.bug.austras.apt.core.common.model.ComponentRef;
import guru.bug.austras.apt.core.common.model.bean.BeanModel;
import guru.bug.austras.apt.core.common.model.bean.BeanPropertyModel;
import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.mapper.Mapper;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Optional;
import java.util.stream.Collectors;

public class MapperProcessorPlugin implements AustrasProcessorPlugin {
    private ProcessingContext ctx;
    @Override
    public void process(ProcessingContext ctx) {
        this.ctx = ctx;
        try {
            var generator = new MapperGenerator(ctx);
            ctx.componentManager().roundDependencies().stream()
                    .map(this::createMapperModel)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(generator::generate);
        } finally {
            this.ctx = null;
        }
    }

    private Optional<MapperModel> createMapperModel(ComponentRef ref) {
        return processDeclared(ref.getType())
                .flatMap(this::processIfMapper);
    }

    private Optional<MapperModel> processIfMapper(DeclaredType type) {
        if (!type.toString().startsWith(Mapper.class.getName() + "<")) {
            return Optional.empty();
        }
        var qualifiers = ctx.modelUtils().extractQualifiers(type);
        var args = type.getTypeArguments();
        var srcBeanModel = convertToBeanModel(args.get(0));
        var trgBeanModel = convertToBeanModel(args.get(1));
        var result = new MapperModel();
        result.setQualifiers(qualifiers);
        result.setSource(srcBeanModel);
        result.setTarget(trgBeanModel);

        var fieldMappings = srcBeanModel.getProperties().stream()
                .filter(BeanPropertyModel::isReadable)
                .map(srcProp -> findFieldMapping(srcProp, trgBeanModel))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableList());
        result.setMappings(fieldMappings);
        return Optional.of(result);
    }

    private Optional<FieldMapping> findFieldMapping(BeanPropertyModel srcProp, BeanModel trgBeanModel) {
        return trgBeanModel.getProperties().stream()
                .filter(trgProp -> trgProp.isWritable() && trgProp.getName().equals(srcProp.getName()))
                .map(trgProp -> FieldMapping.of(srcProp, trgProp))
                .findFirst();
    }

    private BeanModel convertToBeanModel(TypeMirror type) {
        return processDeclared(type)
                .map(BeanModel::of)
                .orElseThrow();
    }

    private Optional<DeclaredType> processDeclared(TypeMirror type) {
        if (type.getKind() == TypeKind.DECLARED) {
            return Optional.of((DeclaredType) type);
        }
        return Optional.empty();
    }

}
