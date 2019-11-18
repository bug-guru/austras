package guru.bug.austras.test.convert;

import guru.bug.austras.convert.converters.JsonConverter;

public class WithJsonConverter {
    private final JsonConverter<FakeDto> fakeDtoJsonConverter; //NOSONAR this is for testing purposes only

    public WithJsonConverter(JsonConverter<FakeDto> fakeDtoJsonConverter) {
        this.fakeDtoJsonConverter = fakeDtoJsonConverter;
    }
}
