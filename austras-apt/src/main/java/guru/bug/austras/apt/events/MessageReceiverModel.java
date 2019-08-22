package guru.bug.austras.apt.events;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.stream.Collectors;

class MessageReceiverModel {
    List<Dependency> dependencies;
    List<AnnotationSpec> annotations;
    TypeMirror messageType;

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
