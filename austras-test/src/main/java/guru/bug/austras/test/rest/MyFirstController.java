package guru.bug.austras.test.rest;

import guru.bug.austras.web.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFirstController {
    private static final Logger log = LoggerFactory.getLogger(MyFirstController.class);

    @Endpoint(method = "POST", path = "/my/1")
    public void doSimplePost() {
        log.info("POST Simple endpoint");
    }

    @Endpoint(method = "POST", path = "/my/2/{param1}/x/{param2}")
    public void doSimplePostWithPathParam() {
        log.info("POST Simple endpoint");
    }

}
