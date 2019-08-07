package guru.bug.austras.apt.model;

import java.util.List;
import java.util.Objects;

public class QualifiersModel {
    private List<QualifierModel> qualifiers;

    public List<QualifierModel> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<QualifierModel> qualifiers) {
        this.qualifiers = qualifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QualifiersModel that = (QualifiersModel) o;
        return Objects.equals(qualifiers, that.qualifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qualifiers);
    }
}
