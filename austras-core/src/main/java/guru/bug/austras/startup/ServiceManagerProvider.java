package guru.bug.austras.startup;

import guru.bug.austras.core.Provider;
import guru.bug.austras.core.Selector;
import guru.bug.austras.meta.QualifierSetMetaInfo;

public class ServiceManagerProvider implements Provider<ServiceManager> {
    private final ServiceManager serviceManager;

    public ServiceManagerProvider(Selector<? extends StartupService> servicesSelector) {
        var serviceProviders = servicesSelector.get();
        this.serviceManager = new ServiceManager(serviceProviders);
    }

    @Override
    public ServiceManager get() {
        return serviceManager;
    }

    @Override
    public QualifierSetMetaInfo qualifier() {
        return QualifierSetMetaInfo.empty();
    }
}
