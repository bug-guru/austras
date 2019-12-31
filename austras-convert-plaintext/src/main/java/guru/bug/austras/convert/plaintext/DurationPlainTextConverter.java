package guru.bug.austras.convert.plaintext;

import java.time.Duration;

@PlainText
public class DurationPlainTextConverter extends AbstractPlainTextConverter<Duration> {
    @Override
    public Duration fromString(String value) {
        if (value == null) {
            return null;
        }
        return Duration.parse(value);
    }

    @Override
    public String toString(Duration obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
}
