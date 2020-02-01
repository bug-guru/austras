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
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.*;
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
        if (ctx.componentManager().tryUseComponents(type, qualifiers)) {
            return Optional.empty();
        }
        var args = type.getTypeArguments();
        var srcBeanModel = convertToBeanModel(args.get(0));
        var trgBeanModel = convertToBeanModel(args.get(1));
        var result = new MapperModel();
        result.setQualifiers(qualifiers);
        result.setSource(srcBeanModel);
        result.setTarget(trgBeanModel);
        var deps = new HashMap<String, FieldMapperDependency>();
        var usedNames = new HashSet<String>();
        var fieldMappings = srcBeanModel.getProperties().stream()
                .filter(BeanPropertyModel::isReadable)
                .map(srcProp -> findFieldMapping(srcProp, trgBeanModel, deps, usedNames))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableList());
        result.setMappings(fieldMappings);
        result.setDependencies(List.copyOf(deps.values()));
        return Optional.of(result);
    }

    private Optional<FieldMapping> findFieldMapping(BeanPropertyModel srcProp, BeanModel trgBeanModel, Map<String, FieldMapperDependency> dependencies, Set<String> usedNames) {
        return trgBeanModel.getProperties().stream()
                .filter(trgProp -> trgProp.isWritable() && trgProp.getName().equals(srcProp.getName()))
                .map(trgProp -> {
                    var types = ctx.processingEnv().getTypeUtils();
                    var mapperElement = ctx.processingEnv().getElementUtils().getTypeElement(Mapper.class.getName());
                    var srcType = getDeclaredTypeOf(srcProp.getType());
                    var trgType = getDeclaredTypeOf(trgProp.getType());
                    if (srcType.equals(trgType)) {
                        return FieldMapping.of(srcProp, trgProp, null);
                    } else {
                        var mapperType = types.getDeclaredType(mapperElement, srcType, trgType);
                        var dep = dependencies.computeIfAbsent(mapperType.toString(), k -> {
                            String name = generateUniqueVarName(srcType, trgType, usedNames);
                            return new FieldMapperDependency(name, mapperType);
                        });
                        return FieldMapping.of(srcProp, trgProp, dep);
                    }
                })
                .findFirst();
    }

    private String generateUniqueVarName(DeclaredType srcType, DeclaredType trgType, Set<String> usedNames) {
        int idx = 0;
        var src = srcType.asElement().getSimpleName().toString();
        var trg = trgType.asElement().getSimpleName().toString();
        var base = StringUtils.uncapitalize(src + "To" + trg);
        String name;
        do {
            name = base + (idx == 0 ? "" : String.valueOf(idx));
            idx++;
        } while (!usedNames.add(name));
        return name;
    }


    private DeclaredType getDeclaredTypeOf(TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            return (DeclaredType) ctx.processingEnv().getTypeUtils().boxedClass((PrimitiveType) type).asType();
        } else if (type.getKind() == TypeKind.DECLARED) {
            return (DeclaredType) type;
        } else {
            throw new IllegalArgumentException("Unsupported " + type);
        }
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
