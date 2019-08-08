package guru.bug.austras.apt.model;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ModuleModelSerializer {
    private ModuleModelSerializer() {
    }

    public static void store(ModuleModel model, Writer out) throws IOException {
        var baseConstructor = new Constructor(ModuleModel.class);
        var dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Representer representer = new Representer() {
            @Override
            protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
                if (propertyValue == null) {
                    return null;
                }
                if (propertyValue instanceof List && ((Collection) propertyValue).isEmpty()) {
                    return null;
                }
                if (propertyValue instanceof Map && ((Map) propertyValue).isEmpty()) {
                    return null;
                }
                return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
            }
        };
        representer.addClassTag(ModuleModel.class, Tag.MAP);
        var yaml = new Yaml(baseConstructor, representer, dumperOptions);
        yaml.dump(model, out);
    }
}
