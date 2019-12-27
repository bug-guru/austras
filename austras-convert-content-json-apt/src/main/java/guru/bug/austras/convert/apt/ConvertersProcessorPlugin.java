package guru.bug.austras.convert.apt;

import guru.bug.austras.apt.core.engine.AustrasProcessorPlugin;
import guru.bug.austras.apt.core.engine.ProcessingContext;

public class ConvertersProcessorPlugin implements AustrasProcessorPlugin {

    @Override
    public void process(ProcessingContext ctx) {
        var p = new ConvertersProcessor(ctx);
        p.process();
    }


}
