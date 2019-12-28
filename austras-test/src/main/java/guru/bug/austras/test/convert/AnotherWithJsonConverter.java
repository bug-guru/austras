package guru.bug.austras.test.convert;

import guru.bug.austras.convert.content.ContentConverter;
import guru.bug.austras.convert.content.json.ApplicationJson;

@SuppressWarnings("ALL") // this class is for testing only
public class AnotherWithJsonConverter {
    private final ContentConverter<FakeDto> fakeDtoJsonConverter; //NOSONAR this is for testing purposes only

    public AnotherWithJsonConverter(@ApplicationJson ContentConverter<FakeDto> fakeDtoJsonConverter) {
        this.fakeDtoJsonConverter = fakeDtoJsonConverter;
    }
}
