package guru.bug.austras.apt.events;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.type.TypeMirror;
import java.util.List;

abstract class Dependency {
    String name;
    TypeMirror type;
    List<AnnotationSpec> qualifiers;

    abstract FieldSpec field();

    abstract ParameterSpec param();
}
