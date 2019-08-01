package guru.bug.fuzzbagel.apt.model;

import java.util.ArrayList;
import java.util.List;

public class ComponentModel implements Comparable<ComponentModel> {
    private String name;
    private String instantiable;
    private List<ComponentKeyModel> keys;
    private ProviderModel provider;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstantiable() {
        return instantiable;
    }

    public void setInstantiable(String instantiable) {
        this.instantiable = instantiable;
    }

    public List<ComponentKeyModel> keys() {
        if (keys == null) {
            keys = new ArrayList<>();
        }
        return keys;
    }

    public List<ComponentKeyModel> getKeys() {
        return keys;
    }

    public void setKeys(List<ComponentKeyModel> keys) {
        this.keys = keys;
    }

    public ProviderModel getProvider() {
        return provider;
    }

    public void setProvider(ProviderModel provider) {
        this.provider = provider;
    }

    @Override
    public int compareTo(ComponentModel o) {
        return instantiable.compareTo(o.instantiable);
    }
}
