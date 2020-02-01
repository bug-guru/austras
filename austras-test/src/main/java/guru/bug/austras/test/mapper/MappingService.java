/*
 * Copyright (c) 2020 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test.mapper;

import guru.bug.austras.mapper.Mapper;

public class MappingService {
    private final Mapper<TestBean1, TestBean2> mapper;

    public MappingService(Mapper<TestBean1, TestBean2> mapper) {
        this.mapper = mapper;
    }

}
