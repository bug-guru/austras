package guru.bug.austras.apt.model;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.Writer;

public class ModuleModelSerializer {
    private ModuleModelSerializer() {
    }

    public static void store(ModuleModel model, Writer out) throws IOException {
        var baseConstructor = new Constructor(ModuleModel.class);
        var dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Representer representer = new Representer();
        representer.setPropertyUtils(new PropertyUtils());
        representer.addClassTag(ModuleModel.class, Tag.MAP);
        var yaml = new Yaml(baseConstructor, representer, dumperOptions);
        yaml.dump(model, out);
    }
}
