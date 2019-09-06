package guru.bug.austras.service;

import guru.bug.austras.provider.Provider;

import java.util.Collection;

public class ServicesStarterProvider implements Provider<ServicesStarter> {
    private final ServicesStarter servicesStarter;

    public ServicesStarterProvider(Provider<? extends Collection<? extends Service>> servicesProvider) {
        var serviceProviders = servicesProvider.get();
        this.servicesStarter = new ServicesStarter(serviceProviders);
    }

    @Override
    public ServicesStarter get() {
        return servicesStarter;
    }
}
