/*
 * Copyright (c) 2019-2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 */

package guru.bug.austras.test.mapper;

import guru.bug.austras.mapper.Mapper;

public class MappingService {
    private final Mapper<SourceBean, TargetBean> mapper;

    public MappingService(Mapper<SourceBean, TargetBean> mapper) {
        this.mapper = mapper;
    }

}
