/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.convert.json.apt;

import guru.bug.austras.apt.core.common.model.ComponentRef;
import guru.bug.austras.apt.core.engine.ProcessingContext;
import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.json.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class ConvertersProcessor {
    private final Logger logger = LoggerFactory.getLogger(ConvertersProcessor.class);
    private final ProcessingContext ctx;
    private final TypeElement contentConverter;
    private final TypeElement jsonConverter;

    ConvertersProcessor(ProcessingContext ctx) {
        this.ctx = ctx;
        var elementUtils = ctx.processingEnv().getElementUtils();
        contentConverter = elementUtils.getTypeElement(ContentConverter.class.getName());
        jsonConverter = elementUtils.getTypeElement(JsonConverter.class.getName());
    }

    Set<DeclaredType> process() {
        return ctx.componentManager().roundDependencies().stream()
                .map(this::converterTypeOrEmpty)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableSet());
    }


    private Optional<DeclaredType> converterTypeOrEmpty(ComponentRef ref) {
        return castToDeclaredOrEmpty(ref.getType())
                .flatMap(type -> {
                    var args = type.getTypeArguments();
                    if (args.size() != 1) {
                        return Optional.empty();
                    }
                    var element = type.asElement();
                    if (!contentConverter.equals(element) && !jsonConverter.equals(element)) {
                        return Optional.empty();
                    }
                    var qualifier = ref.getQualifiers();
                    if (ctx.componentManager().tryUseComponents(type, qualifier)) {
                        return Optional.empty();
                    }

                    return castToDeclaredOrEmpty(args.get(0));
                });
    }

    private Optional<DeclaredType> castToDeclaredOrEmpty(TypeMirror type) {
        if (type.getKind() != TypeKind.DECLARED) {
            return Optional.empty();
        }
        return Optional.of((DeclaredType) type);
    }

}
