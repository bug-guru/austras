/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.mapper;

/**
 * Defines the mapping from source type S to target type T. The mapper's implementation can be generated by the framework,
 * if there are no custom mappers defined yet for the same source and target. Generated mapper maps fields by their names.
 * But this can be customized using {@link Mapping} annotation.
 *
 * @param <S> source type
 * @param <T> target type
 */
public interface Mapper<S, T> {
    T map(S source);
}
