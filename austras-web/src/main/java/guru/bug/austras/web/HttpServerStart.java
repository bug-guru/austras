package guru.bug.austras.web;

import guru.bug.austras.core.Component;
import guru.bug.austras.startup.StartupService;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HttpServerStart implements StartupService {
    private Server server;

    @Override
    public void initialize() {
        this.server = new Server(8080);
        this.server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/plain");
                response.setContentLength(6);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().printf("HELLO!").close();
            }
        });
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
