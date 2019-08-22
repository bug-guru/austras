package guru.bug.austras.apt.events.model;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.stream.Collectors;

public class MessageReceiverModel {
    private final List<DependencyModel> dependencies;
    private final List<CallParamModel> parameters;
    private final List<AnnotationSpec> annotations;
    private final TypeMirror messageType;

    List<AnnotationSpec> annotations() {
        return annotations;
    }

    TypeMirror messageType() {
        return messageType;
    }

    List<FieldSpec> fields() {
        return dependencies.stream().map(Dependency::field).collect(Collectors.toList());
    }

    List<ParameterSpec> parameters() {
        return dependencies.stream().map(Dependency::param).collect(Collectors.toList());
    }


}
