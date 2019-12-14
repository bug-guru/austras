package guru.bug.austras.startup;

import guru.bug.austras.core.Provider;

import java.util.Collection;

public class ServiceManagerProvider implements Provider<ServiceManager> {
    private final ServiceManager serviceManager;

    public ServiceManagerProvider(Provider<? extends Collection<? extends StartupService>> servicesProvider) {
        var serviceProviders = servicesProvider.get();
        this.serviceManager = new ServiceManager(serviceProviders);
    }

    @Override
    public ServiceManager get() {
        return serviceManager;
    }
}
