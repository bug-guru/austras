package guru.bug.austras.startup;

import guru.bug.austras.provider.Provider;

import java.util.Collection;

public class StartupServicesStarterProvider implements Provider<StartupServicesStarter> {
    private final StartupServicesStarter startupServicesStarter;

    public StartupServicesStarterProvider(Provider<? extends Collection<? extends StartupService>> servicesProvider) {
        var serviceProviders = servicesProvider.get();
        this.startupServicesStarter = new StartupServicesStarter(serviceProviders);
    }

    @Override
    public StartupServicesStarter get() {
        return startupServicesStarter;
    }
}
