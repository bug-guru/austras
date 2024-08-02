/*
 * Copyright (c) 2024 Dimitrijs Fedotovs
 * This software is licensed under the terms of the MIT license
 * See LICENSE for the license details.
 *
 */

package guru.bug.austras.test.convert;

import guru.bug.austras.convert.ContentConverter;
import guru.bug.austras.convert.json.ApplicationJson;

@SuppressWarnings("ALL") // this class is for testing only
public class WithJsonConverter {
    private final ContentConverter<FakeDto> fakeDtoJsonConverter; //NOSONAR this is for testing purposes only

    public WithJsonConverter(@ApplicationJson ContentConverter<FakeDto> fakeDtoJsonConverter) {
        this.fakeDtoJsonConverter = fakeDtoJsonConverter;
    }
}
