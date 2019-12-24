package guru.bug.austras.apt.core.process;

import guru.bug.austras.apt.core.common.model.ModuleModel;
import guru.bug.austras.convert.json.reader.JsonValueReader;
import guru.bug.austras.convert.json.writer.JsonValueWriter;

import java.io.Reader;
import java.io.Writer;

public class ModuleModelSerializer {
    private ModuleModelSerializer() {
    }

    public static ModuleModel load(Reader reader) {
        var jsonReader = JsonValueReader.newInstance(reader);
        return jsonReader.read(ModuleModel.serializer()).orElseThrow();
    }

    public static void store(ModuleModel model, Writer out) {
        var jsonWriter = JsonValueWriter.newInstance(out);
        jsonWriter.writeValue(model, ModuleModel.serializer());
    }
}
